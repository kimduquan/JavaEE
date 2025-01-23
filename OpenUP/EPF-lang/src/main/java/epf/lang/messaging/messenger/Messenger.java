package epf.lang.messaging.messenger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import epf.lang.Cache;
import epf.lang.messaging.messenger.client.schema.Message;
import epf.lang.messaging.messenger.client.schema.MessageRef;
import epf.lang.messaging.messenger.client.schema.ResponseMessage;
import epf.lang.messaging.messenger.shema.ObjectRef;
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
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;
import jakarta.persistence.metamodel.EntityType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
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
	
	@Inject
	transient Validator validator;
	
	private String systemMessage;
	private  Map<String, EntityType<?>> entitiesByJavaType = new ConcurrentHashMap<>();
	
	@PostConstruct
	void postConstruct() {
		final String packages = "erp.base.schema.res";
		final StringBuilder prompt = new StringBuilder();
		prompt.append("Given the following domain classes ( Java ) and their descrition that placed in comments:");
		prompt.append('\n');
		final List<EntityType<?>> entityTypes = new LinkedList<>();
		manager.getMetamodel().getEntities().forEach(entityType -> {
			entitiesByJavaType.put(entityType.getJavaType().getName(), entityType);
			if(entityType.getJavaType().getName().startsWith(packages)) {
				entityTypes.add(entityType);
			}
		});
		entityTypes.sort(new EntityTypeComparator(packages));
		final PromptPrinter printer = new PromptPrinter(packages);
		entityTypes.forEach(entityType -> {
			printer.printEntityType(entityType, prompt, entitiesByJavaType);
		});
		prompt.append('\n');
		prompt.append("""
When you receive a user's question, please perform the following tasks, thinking through each step carefully. Respond in JSON format with the specified fields:
1. "query": Provide a JPQL query using the given domain classes to address the user's question. Ensure the query is accurate and aligned with the provided domain model.
2. "parameters": Include a map of parameters where each key represents a parameter name, and the corresponding value represents the parameter's value. These parameters are used in the generated JPQL query to ensure it addresses the user's question effectively.
3. "template": Create a chat message template (using Handlebars syntax) that utilizes the actual returned data from the generated query to address the user's question. Assume that the returned object is named "result".
				""");
		systemMessage = prompt.toString();
		LOGGER.info("\nprompt:\"\"\"\n" + systemMessage + "\n\"\"\"");
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
	
	private GenerateRequest getGenerateRequest(final Pages pages) {
		GenerateRequest request = generates.get(pages.getEntry().getFirst().getMessaging().getFirst().getSender().getId());
		if(request == null) {
			LOGGER.info("generate is empty.");
			request = new GenerateRequest();
			request.setModel(model);
			request.setSystem(systemMessage);
			request.setStream(false);
			request.setKeep_alive("24h");
			request.setFormat("json");
			request.setOptions(new HashMap<>());
			request.getOptions().put("temperature", Float.valueOf("0.1"));
		}
		else {
			request.setSystem(null);
		}
		return request;
	}
	
	private void putRequest(final Pages pages, final GenerateRequest request) {
		generates.put(pages.getEntry().getFirst().getMessaging().getFirst().getSender().getId(), request);
	}
	
	private GeneratedQuery getQuery(final GenerateResponse response) throws Exception {
		GeneratedQuery query = null;
		try(Jsonb jsonb = JsonbBuilder.create()){
			query = jsonb.fromJson(response.getResponse(), GeneratedQuery.class);
		}
		return query;
	}
	
	private GeneratedQuery generate(final Pages pages, final GenerateRequest request) throws Exception {
		final GenerateResponse response = ollama.generate(request);
		LOGGER.info(response.getResponse());
		request.setContext(response.getContext());
		final GeneratedQuery query = getQuery(response);
		if(query == null) {
			response(pages.getEntry().getFirst().getId(), pages.getEntry().getFirst().getMessaging().getFirst().getSender(), response.getResponse());
			putRequest(pages, request);
		}
		return query;
	}
	
	private GeneratedQuery getQuery(final ChatResponse response) throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create()){
			return jsonb.fromJson(response.getMessage().getContent(), GeneratedQuery.class);
		}
		catch(JsonbException ex) {
			return null;
		}
	}
	
	private void putRequest(final Pages pages, final ChatRequest request) {
		chats.put(pages.getEntry().getFirst().getMessaging().getFirst().getSender().getId(), request);
	}
	
	private GeneratedQuery chat(final Pages pages, final ChatRequest request) throws Exception {
		final ChatResponse response = ollama.chat(request);
		LOGGER.info(response.getMessage().getContent());
		request.getMessages().add(response.getMessage());
		final GeneratedQuery query = getQuery(response);
		if(query == null) {
			response(pages.getEntry().getFirst().getId(), pages.getEntry().getFirst().getMessaging().getFirst().getSender(), response.getMessage().getContent());
			putRequest(pages, request);
		}
		return query;
	}
	
	private Object getResult(final GeneratedQuery generated) throws Exception {
		final Query query = manager.createQuery(generated.getQuery());
		if(generated.getParameters() != null) {
			generated.getParameters().forEach((name, value) -> {
				try {
					query.setParameter(name, value);
				}
				catch(Exception ex) {
					LOGGER.warning(ex.getMessage());
				}
			});
		}
		Object result = null;
		try {
			result = query.getSingleResult();
		}
		catch(NonUniqueResultException | NoResultException ex) {
			result = query.getResultList();
		}
		return result;
	}
	
	private Template compileTemplate(final GeneratedQuery query, final Map<String, List<String>> missingAttributes) throws Exception {
		final EntityAttributeGetter getter = new EntityAttributeGetter();
		final Handlebars handlebars = new Handlebars().prettyPrint(true).registerHelperMissing(new Helper<Object>() {
			@Override
			public Object apply(final Object context, final Options options) throws IOException {
				return getter.get(context, options.helperName, entitiesByJavaType, missingAttributes);
			}});
		final Template template = handlebars.compileInline(query.getTemplate());
		return template;
	}
	
	private String getText(final GeneratedQuery query, final Template template, final Object result) throws Exception {
		final Map<String, Object> context = new HashMap<>();
		if(query.getParameters() != null) {
			context.putAll(query.getParameters());
		}
		context.put("result", result);
		final String text = template.apply(context);
		return text;
	}
	
	private Tool getTool() {
		final Tool tool = new Tool();
		tool.setType("function");
		final Function function = new Function();
		function.setName("get_erp_data");
		function.setDescription("This function is designed to handle user queries related to Enterprise Resource Planning (ERP) systems.");
		function.setParameters(new Parameters());
		function.getParameters().setType("object");
		function.getParameters().setProperties(new LinkedHashMap<>());
		final Parameter queryParam = new Parameter();
		queryParam.setType("string");
		queryParam.setDescription("Provide a JPQL query using the given domain classes to address the user's question. Ensure the query is accurate and aligned with the provided domain model.");
		function.getParameters().getProperties().put("query", queryParam);
		final Parameter paramsParam = new Parameter();
		paramsParam.setType("object");
		paramsParam.setDescription("Include a map of parameters where each key represents a parameter name, and the corresponding value represents the parameter's value. These parameters are used in the generated JPQL query to ensure it addresses the user's question effectively.");
		function.getParameters().getProperties().put("parameters", paramsParam);
		final Parameter templateParam = new Parameter();
		templateParam.setType("string");
		templateParam.setDescription("Create a chat message template (using Handlebars syntax) that utilizes the actual returned data from the generated query to address the user's question. Assume that the returned object is named \"result\".");
		function.getParameters().getProperties().put("template", templateParam);
		function.getParameters().setRequired(Arrays.asList("query", "parameters", "template"));
		return tool;
	}
	
	private void response(final String id, final ObjectRef recipient, final String text) throws Exception {
		final ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setRecipient(recipient);
		final Message message = new Message();
		message.setText(text);
		responseMessage.setMessage(message);
		final MessageRef msgRef = client.send(id, token, responseMessage);
		LOGGER.info(String.format("response[%s]:%s", msgRef.getRecipient_id(), msgRef.getMessage_id()));
	}
	
	public ChatResponse handleResponse(final Pages data, final ChatRequest request) {
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
	
	private ChatRequest getChatRequest(final Pages pages) throws Exception {
		ChatRequest request = chats.get(pages.getEntry().getFirst().getMessaging().getFirst().getSender().getId());
		if(request == null) {
			LOGGER.info("chat is empty.");
			request = new ChatRequest();
			request.setModel(model);
			request.setStream(false);
			request.setMessages(new ArrayList<>());
			request.setTools(new ArrayList<>());
			
			final epf.lang.schema.ollama.Message systemMessage = new epf.lang.schema.ollama.Message();
			systemMessage.setRole(Role.system);
			systemMessage.setContent(this.systemMessage);
			request.getMessages().add(systemMessage);
			
			final Tool tool = getTool();
			
			request.getTools().add(tool);
			
			ollama.chat(request);
		}
		final epf.lang.schema.ollama.Message message = new epf.lang.schema.ollama.Message();
		message.setRole(Role.user);
		message.setContent(pages.getEntry().getFirst().getMessaging().getFirst().getMessage().getText());
		request.getMessages().add(message);
		return request;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response subscribeV2(final Pages pages) throws Exception {
		final ChatRequest request = getChatRequest(pages);
		int retry = 5;
		int failed = 0;
		final ErrorMessageBuilder errorBuilder = new ErrorMessageBuilder();
		do {
			final GeneratedQuery query = chat(pages, request);
			if(query != null) {
				final Set<ConstraintViolation<GeneratedQuery>> violations = validator.validate(query);
				if(violations.isEmpty()) {
					final Map<String, List<String>> missingAttributes = new LinkedHashMap<>();
					final Template template = compileTemplate(query, missingAttributes);
					if(missingAttributes.isEmpty()) {
						try {
							final Object result = getResult(query);
							String text = null;
							try {
								text = getText(query, template, result);
							}
							catch(Exception ex) {
								LOGGER.warning(ex.getMessage());
								failed++;
								final StringBuilder systemErrorMessage = errorBuilder.buildSystemErrorMessage("template", ex, entitiesByJavaType);
								final epf.lang.schema.ollama.Message message = new epf.lang.schema.ollama.Message();
								message.setRole(Role.user);
								message.setContent(systemErrorMessage.toString());
								request.getMessages().add(message);
								continue;
							}
							if(text != null) {
								putRequest(pages, request);
								text = text.replace("<br>", "\n");
								response(pages.getEntry().getFirst().getId(), pages.getEntry().getFirst().getMessaging().getFirst().getSender(), text);
								break;
							}
						}
						catch(Exception ex) {
							Throwable cause = ex;
							if(ex.getCause() != null) {
								cause = ex.getCause();
							}
							LOGGER.warning(cause.getMessage());
							failed++;
							final StringBuilder systemErrorMessage = errorBuilder.buildSystemErrorMessage("query", cause, entitiesByJavaType);
							final epf.lang.schema.ollama.Message message = new epf.lang.schema.ollama.Message();
							message.setRole(Role.user);
							message.setContent(systemErrorMessage.toString());
							request.getMessages().add(message);
							continue;
						}
					}
					else {
						missingAttributes.forEach((entityType, attributes) -> {
							LOGGER.warning(String.format("Could not resolve %s attribute(s) of entity type '%s'", String.join(",", attributes), entityType));
						});
						failed++;
						final StringBuilder missingAttributesSystemMessage = errorBuilder.buildMissingAttributesSystemMessage(missingAttributes);
						final epf.lang.schema.ollama.Message message = new epf.lang.schema.ollama.Message();
						message.setRole(Role.user);
						message.setContent(missingAttributesSystemMessage.toString());
						request.getMessages().add(message);
						continue;
					}
				}
				else {
					violations.forEach(violation -> LOGGER.warning(violation.getMessage()));
					failed++;
					final StringBuilder systemErrorMessage = errorBuilder.buildSystemErrorMessage(violations);
					final epf.lang.schema.ollama.Message message = new epf.lang.schema.ollama.Message();
					message.setRole(Role.user);
					message.setContent(systemErrorMessage.toString());
					request.getMessages().add(message);
					continue;
				}
			}
			else {
				break;
			}
		}
		while(failed < retry);
		return Response.ok().build();
	}
	
	public Response subscribe(final Pages pages) throws Exception {
		final GenerateRequest request = getGenerateRequest(pages);
		request.setPrompt(pages.getEntry().getFirst().getMessaging().getFirst().getMessage().getText());
		int retry = 5;
		int failed = 0;
		final ErrorMessageBuilder errorBuilder = new ErrorMessageBuilder();
		do {
			final GeneratedQuery query = generate(pages, request);
			if(query != null) {
				final Set<ConstraintViolation<GeneratedQuery>> violations = validator.validate(query);
				if(violations.isEmpty()) {
					final Map<String, List<String>> missingAttributes = new LinkedHashMap<>();
					final Template template = compileTemplate(query, missingAttributes);
					if(missingAttributes.isEmpty()) {
						try {
							final Object result = getResult(query);
							String text = null;
							try {
								text = getText(query, template, result);
							}
							catch(Exception ex) {
								LOGGER.warning(ex.getMessage());
								failed++;
								final StringBuilder systemErrorMessage = errorBuilder.buildSystemErrorMessage("template", ex, entitiesByJavaType);
								request.setPrompt(systemErrorMessage.toString());
								continue;
							}
							if(text != null) {
								putRequest(pages, request);
								text = text.replace("<br>", "\n");
								response(pages.getEntry().getFirst().getId(), pages.getEntry().getFirst().getMessaging().getFirst().getSender(), text);
								break;
							}
						}
						catch(Exception ex) {
							Throwable cause = ex;
							if(ex.getCause() != null) {
								cause = ex.getCause();
							}
							LOGGER.warning(cause.getMessage());
							failed++;
							final StringBuilder systemErrorMessage = errorBuilder.buildSystemErrorMessage("query", cause, entitiesByJavaType);
							request.setPrompt(systemErrorMessage.toString());
							continue;
						}
					}
					else {
						missingAttributes.forEach((entityType, attributes) -> {
							LOGGER.warning(String.format("Could not resolve %s attribute(s) of entity type '%s'", String.join(",", attributes), entityType));
						});
						failed++;
						final StringBuilder missingAttributesSystemMessage = errorBuilder.buildMissingAttributesSystemMessage(missingAttributes);
						request.setPrompt(missingAttributesSystemMessage.toString());
						continue;
					}
				}
				else {
					violations.forEach(violation -> LOGGER.warning(violation.getMessage()));
					failed++;
					final StringBuilder systemErrorMessage = errorBuilder.buildSystemErrorMessage(violations);
					request.setPrompt(systemErrorMessage.toString());
					continue;
				}
			}
			else {
				break;
			}
		}
		while(failed < retry);
		return Response.ok().build();
	}

	@Path("schema")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getSchema() {
		return systemMessage;
	}
	
	@Path("query")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String executeQuery(@Valid final GeneratedQuery query) throws Exception {
		final Object result = getResult(query);
		final Map<String, List<String>> missingAttributes = new LinkedHashMap<>();
		final Template template = compileTemplate(query, missingAttributes);
		final String text = getText(query, template, result);
		return text;
	}
}
