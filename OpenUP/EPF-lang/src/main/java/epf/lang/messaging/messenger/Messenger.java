package epf.lang.messaging.messenger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.lang.Cache;
import epf.lang.messaging.messenger.client.schema.Message;
import epf.lang.messaging.messenger.client.schema.MessageRef;
import epf.lang.messaging.messenger.client.schema.ResponseMessage;
import epf.lang.messaging.messenger.shema.Pages;
import epf.lang.ollama.Ollama;
import epf.lang.schema.ollama.ChatRequest;
import epf.lang.schema.ollama.ChatResponse;
import epf.lang.schema.ollama.Role;
import epf.naming.Naming;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("lang/messaging/messenger")
public class Messenger {
	
	private static final Logger LOGGER = Logger.getLogger(Messenger.class.getName());
	
	private final Map<String, ChatRequest> map = new ConcurrentHashMap<>();
	
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
	
	@PostConstruct
	void postConstruct() {
		final String packages = "erp.base.schema.res";
		final StringBuilder prompt = new StringBuilder();
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
						prompt.append("\tenum ");
						prompt.append(clazz.getSimpleName());
						prompt.append(" {\n");
						for(Object enumConstant : clazz.getEnumConstants()) {
							prompt.append("\t\t");
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
						prompt.append("\t}\n");
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
										prompt.append('\t');
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
									prompt.append('\t');
									prompt.append(attr.getJavaType().getSimpleName());
								}
							}
							else {
								prompt.append('\t');
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
				prompt.append("}\n");
			}
		});
		LOGGER.info("prompt:" + prompt.toString());
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
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response subscribe(final Pages data) throws Exception {
		ChatRequest request = map.get(data.getEntry().getFirst().getMessaging().getFirst().getSender().getId());
		if(request == null) {
			LOGGER.info("cache is empty.");
			request = new ChatRequest();
			request.setModel(model);
			request.setStream(false);
			request.setMessages(new ArrayList<>());
		}
		final epf.lang.schema.ollama.Message msg = new epf.lang.schema.ollama.Message();
		msg.setRole(Role.user);
		msg.setContent(data.getEntry().getFirst().getMessaging().getFirst().getMessage().getText());
		request.getMessages().add(msg);
		final ChatResponse response = ollama.chat(request);
		final ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setRecipient(data.getEntry().getFirst().getMessaging().getFirst().getSender());
		final Message message = new Message();
		message.setText(response.getMessage().getContent());
		responseMessage.setMessage(message);
		final MessageRef msgRef = client.send(data.getEntry().getFirst().getId(), token, responseMessage);
		LOGGER.info(String.format("response[%s]:%s", msgRef.getRecipient_id(), msgRef.getMessage_id()));
		request.getMessages().add(response.getMessage());
		map.put(data.getEntry().getFirst().getMessaging().getFirst().getSender().getId(), request);
		return Response.ok().build();
	}

}
