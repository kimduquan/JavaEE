package epf.lang.messaging.messenger;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import epf.json.schema.JsonObject;
import epf.json.schema.JsonString;
import epf.lang.Cache;
import epf.lang.messaging.messenger.client.schema.Message;
import epf.lang.messaging.messenger.client.schema.MessageRef;
import epf.lang.messaging.messenger.client.schema.ResponseMessage;
import epf.lang.messaging.messenger.shema.Pages;
import epf.lang.ollama.Ollama;
import epf.lang.schema.ollama.ChatRequest;
import epf.lang.schema.ollama.ChatResponse;
import epf.lang.schema.ollama.Function;
import epf.lang.schema.ollama.GenerateRequest;
import epf.lang.schema.ollama.GenerateResponse;
import epf.lang.schema.ollama.Parameter;
import epf.lang.schema.ollama.Parameters;
import epf.lang.schema.ollama.Role;
import epf.lang.schema.ollama.Tool;
import epf.lang.schema.ollama.ToolCall;
import epf.naming.Naming;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import jakarta.persistence.Query;
import jakarta.persistence.Transient;
import jakarta.persistence.metamodel.Bindable;
import jakarta.persistence.metamodel.CollectionAttribute;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.MapAttribute;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("lang/messaging/messenger")
public class Messenger {
	
	private static final Logger LOGGER = Logger.getLogger(Messenger.class.getName());
	
	private final Map<String, ChatRequest> chats = new ConcurrentHashMap<>();
	private final Map<String, GenerateRequest> generates = new ConcurrentHashMap<>();
	
	@Inject
	@ConfigProperty(name = "epf.lang.messaging.messenger.token")
	String token;
	
	@Inject
	@ConfigProperty(name = Naming.Lang.Internal.LANGUAGE_MODEL)
	String model;
	
	@RestClient
	transient Client client;
	
	@Inject
	transient ManagedExecutor executor;
	
	@RestClient
	transient Ollama ollama;
	
	@Inject
	@Readiness
	transient Cache cache;
	
	@Inject
	transient EntityManager manager;
	
	private String systemMessage;
	
	@PostConstruct
	void postConstruct() {
		final String packages = "erp.base.schema.res";
		final StringBuilder prompt = new StringBuilder();
		prompt.append("Given the following domain classes and their descrition that placed in comments:");
		prompt.append('\n');
		manager.getMetamodel().getEntities().forEach(entityType -> {
			if(entityType.getJavaType().getName().startsWith(packages)) {
				final Description description = entityType.getJavaType().getAnnotation(Description.class);
				if(description != null && !description.value().isEmpty()) {
					prompt.append(" // ");
					prompt.append(description.value());
					prompt.append('\n');
				}
				prompt.append("class ");
				prompt.append(entityType.getName());
				prompt.append(" {\n");
				for(Class<?> clazz : entityType.getJavaType().getClasses()) {
					if(clazz.isEnum()) {
						prompt.append("    enum ");
						prompt.append(clazz.getSimpleName());
						prompt.append(" {\n");
						for(Object enumConstant : clazz.getEnumConstants()) {
							prompt.append("        ");
							prompt.append(enumConstant.toString());
							prompt.append(",");
							try {
								final Field enumField = clazz.getField(enumConstant.toString());
								final Description enumDesc = enumField.getAnnotation(Description.class);
								if(enumDesc != null) {
									prompt.append(" // ");
									prompt.append(enumDesc.value());
								}
							}
							catch(Exception ex) {
								LOGGER.log(Level.SEVERE, ex.toString());
							}
							prompt.append("\n");
						}
						prompt.append("    };\n");
					}
				}
				entityType.getAttributes().forEach(attr -> {
					if(attr.getJavaMember() instanceof Field) {
						final Field field = (Field) attr.getJavaMember();
						if(field.getAnnotation(Id.class) == null && field.getAnnotation(Transient.class) == null) {
							if(attr instanceof Bindable) {
								final Bindable<?> bindable = (Bindable<?>) attr;
								if(attr.isAssociation() || attr.isCollection()) {
									if(bindable.getBindableJavaType().getName().startsWith(packages)) {
										prompt.append("    ");
										if(attr instanceof CollectionAttribute) {
											prompt.append(String.format("Collection<%s>", bindable.getBindableJavaType().getSimpleName()));
										}
										else if(attr instanceof ListAttribute) {
											prompt.append(String.format("List<%s>", bindable.getBindableJavaType().getSimpleName()));
										}
										else if(attr instanceof MapAttribute) {
											prompt.append(String.format("Map<%s>", bindable.getBindableJavaType().getSimpleName()));
										}
										else if(attr instanceof SetAttribute) {
											prompt.append(String.format("Set<%s>", bindable.getBindableJavaType().getSimpleName()));
										}
										else if(attr instanceof SingularAttribute) {
											prompt.append(bindable.getBindableJavaType().getSimpleName());
										}
									}
									else {
										return;
									}
								}
								else {
									prompt.append("    ");
									prompt.append(attr.getJavaType().getSimpleName());
								}
							}
							else {
								prompt.append("    ");
								prompt.append(attr.getJavaType().getSimpleName());
							}
							prompt.append(' ');
							prompt.append(attr.getName());
							final DefaultValue defaultValue = field.getAnnotation(DefaultValue.class);
							if(defaultValue != null) {
								prompt.append(" = ");
								if(attr.getJavaType() == String.class) {
									prompt.append('"');
									prompt.append(defaultValue.value());
									prompt.append('"');
								}
								else if(attr.getJavaType().isEnum()) {
									prompt.append(attr.getJavaType().getSimpleName());
									prompt.append('.');
									prompt.append(defaultValue.value().replace(' ', '_').replace('-', '_'));
								}
								else {
									prompt.append(defaultValue.value());
								}
							}
							prompt.append(";");
							final Description fieldDesc = field.getAnnotation(Description.class);
							if(fieldDesc != null && !fieldDesc.value().isEmpty()) {
								prompt.append(" // ");
								prompt.append(fieldDesc.value());
							}
							prompt.append('\n');
						}
					}
				});
				prompt.append("};\n");
			}
		});
		prompt.append('\n');
		prompt.append("""
When you receive a user's question, please perform the following tasks, think through each step carefully and repsonse in JSON format with the specified fields:
1. "query": Provide a JPQL query using the given domain classes to address the user's question.
2. "template": Create a Markdown template (Handlebars) that utilizes the actual returned data from the generated query to address the user's question. Assume that the returned object will be named 'result'.
				""");
		systemMessage = prompt.toString();
		LOGGER.info("prompt:" + systemMessage);
	}
	
	@GET
	public Response verify(
			@QueryParam("hub.mode")
			final String hub_mode,
			@QueryParam("hub.verify_token")
			final String hub_verify_token,
			@QueryParam("hub.challenge")
			final String hub_challenge) throws Exception {
		if("subscribe".equals(hub_mode) && token.equals(hub_verify_token)) {
			return Response.ok(hub_challenge).build();
		}
		throw new ForbiddenException();
	}
	
	private GeneratedResponse generate(final Pages pages) throws Exception {
		GenerateRequest request = generates.get(pages.getEntry().getFirst().getMessaging().getFirst().getSender().getId());
		if(request == null) {
			LOGGER.info("generate is empty.");
			request = new GenerateRequest();
			request.setModel(model);
			request.setSystem(systemMessage);
			request.setStream(false);
			request.setKeep_alive("24h");
			final JsonObject jsonSchema = new JsonObject();
			jsonSchema.setProperties(new HashMap<>());
			jsonSchema.getProperties().put("query", new JsonString());
			jsonSchema.getProperties().put("template", new JsonString());
			jsonSchema.setRequired(new String[] {"query", "template"});
			request.setFormat("json");
			//request.setFormat(jsonSchema);
			final Map<String, Object> schema = new HashMap<>();
			schema.put("type", "object");
			final Map<String, Object> properties = new HashMap<>();
			final Map<String, Object> stringType = new HashMap<>();
			stringType.put("type", "string");
			properties.put("query", stringType);
			properties.put("template", stringType);
			schema.put("properties", properties);
			schema.put("required", Arrays.asList("query", "template"));
			//request.setFormat(schema);
		}
		request.setPrompt(pages.getEntry().getFirst().getMessaging().getFirst().getMessage().getText());
		final GenerateResponse response = ollama.generate(request);
		request.setContext(response.getContext());
		generates.put(pages.getEntry().getFirst().getMessaging().getFirst().getSender().getId(), request);
		GeneratedResponse generated = null;
		try(Jsonb jsonb = JsonbBuilder.create()){
			generated = jsonb.fromJson(response.getResponse(), GeneratedResponse.class);
		}
		if(generated != null && generated.getQuery() != null && generated.getTemplate() != null) {
			return generated;
		}
		else {
			response(pages, response.getResponse());
			return null;
		}
	}
	
	private Object query(final GeneratedResponse generated) throws Exception {
		final Query query = manager.createQuery(generated.getQuery());
		Object result = null;
		try {
			result = query.getSingleResult();
		}
		catch(Exception ex) {
			result = query.getResultList();
		}
		return result;
	}
	
	private void response(final Pages pages, final String text) throws Exception {
		final ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setRecipient(pages.getEntry().getFirst().getMessaging().getFirst().getSender());
		final Message message = new Message();
		message.setText(text);
		responseMessage.setMessage(message);
		final MessageRef msgRef = client.send(pages.getEntry().getFirst().getId(), token, responseMessage);
		LOGGER.info(String.format("response[%s]:%s", msgRef.getRecipient_id(), msgRef.getMessage_id()));
	}
	
	public ChatResponse chat(final Pages data) throws Exception {
		ChatRequest request = chats.get(data.getEntry().getFirst().getMessaging().getFirst().getSender().getId());
		if(request == null) {
			LOGGER.info("chat is empty.");
			request = new ChatRequest();
			request.setModel(model);
			request.setStream(false);
			request.setMessages(new LinkedList<>());
			request.setTools(new LinkedList<>());
			
			final Tool tool = new Tool();
			tool.setType("function");
			final Function function = new Function();
			function.setName("get_erp_data");
			function.setDescription("""
This function is designed to handle user queries related to Enterprise Resource Planning (ERP) systems. 
It exclusively accepts questions in natural language through its \"user_question\" parameter, interprets the intent, retrieves relevant data from the ERP system, and generates clear, conversational answers in natural language.
			""");
			function.setParameters(new Parameters());
			function.getParameters().setType("object");
			function.getParameters().setProperties(new LinkedHashMap<>());
			final Parameter queryParam = new Parameter();
			queryParam.setType("string");
			queryParam.setDescription("""
The user's query in natural language.
					""");
			function.getParameters().getProperties().put("user_question ", queryParam);
			function.getParameters().setRequired(Arrays.asList("user_question "));
			request.getTools().add(tool);
		}
		final epf.lang.schema.ollama.Message systemMsg = new epf.lang.schema.ollama.Message();
		systemMsg.setRole(Role.system);
		systemMsg.setContent(systemMessage);
		request.getMessages().add(systemMsg);
		
		final epf.lang.schema.ollama.Message userMsg = new epf.lang.schema.ollama.Message();
		userMsg.setRole(Role.user);
		userMsg.setContent(data.getEntry().getFirst().getMessaging().getFirst().getMessage().getText());
		request.getMessages().add(userMsg);
		ChatResponse response = ollama.chat(request);
		if(response.getMessage().getTool_calls() != null && !response.getMessage().getTool_calls().isEmpty()) {
			for(ToolCall toolCall : response.getMessage().getTool_calls()) {
				if(toolCall.getFunction().getName().equals("get_erp_data")) {
					final epf.lang.schema.ollama.Message toolMsg = new epf.lang.schema.ollama.Message();
					toolMsg.setRole(Role.tool);
					request.getMessages().add(toolMsg);
				}
			}
			response = ollama.chat(request);
		}
		request.getMessages().add(response.getMessage());
		chats.put(data.getEntry().getFirst().getMessaging().getFirst().getSender().getId(), request);
		return response;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response subscribe(final Pages data) throws Exception {
		final GeneratedResponse generated = generate(data);
		if(generated != null) {
			final Object result = query(generated);
			final Handlebars handlebars = new Handlebars();
			final Template template = handlebars.compileInline(generated.getTemplate());
			final String text = template.apply(Map.of("result", result));
			response(data, text);
		}
		return Response.ok().build();
	}

	@Path("schema")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getSchema() {
		return systemMessage;
	}
}
