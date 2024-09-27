package epf.lang;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import dev.langchain4j.data.segment.TextSegment;
import epf.lang.ollama.Ollama;
import epf.lang.schema.ollama.ChatRequest;
import epf.lang.schema.ollama.ChatResponse;
import epf.lang.schema.ollama.Message;
import epf.lang.schema.ollama.Role;
import epf.naming.Naming;
import epf.util.logging.LogManager;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.OnMessage;
import jakarta.websocket.RemoteEndpoint.Basic;
import jakarta.websocket.Session;

/**
 * 
 */
@ClientEndpoint
@ApplicationScoped
@Readiness
public class Messaging implements HealthCheck {
	
	/**
	 * 
	 */
	private static final transient Logger LOGGER = LogManager.getLogger(Messaging.class.getName());
	
	/**
	 * 
	 */
	private static final String DEFAULT_PROMPT_TEMPLATE = """
            Based on the GraphQL schema below, write a GraphQL query that would answer the user's question:
            %s

            Question: %s
            GraphQL query:
            """;
	
	private final Map<String, String> promptTemplates = Map.of(
			//"llama3:instruct", "<|start_header_id|>user<|end_header_id|>\n\n%s<|eot_id|><|start_header_id|>assistant<|end_header_id|>\n\n%s<|eot_id|>",
			//"nous-hermes2:10.7b", "<|im_start|>user\n%s<|im_end|>\n<|im_start|>assistant\n%s<|im_end|>\n"
			);
	
	private Session session;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Lang.Internal.LANGUAGE_MODEL)
	String modelName;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Gateway.MESSAGING_URL)
	URI messagingUrl;
	
	/**
	 * 
	 */
	@RestClient
	Ollama ollama;
	
	/**
	 * 
	 */
	@Inject
	@Readiness
	Cache cache;
	
	@PostConstruct
	void postConstruct() {
        try {
        	final URI url = messagingUrl.resolve(Naming.LANG);
        	LOGGER.info("connect to server: " + url);
            session = ContainerProvider.getWebSocketContainer().connectToServer(this, url);
        }
        catch(Exception ex) {
        	LOGGER.log(Level.SEVERE, "connectToServer", ex);
        }
	}
	
	@PreDestroy
	void preDestroy() {
		try {
			session.close();
		}
		catch(Exception ex) {
        	LOGGER.log(Level.WARNING, "cose", ex);
        }
	}
    
    private void send(final Session session, final String promt) {
    	final Message chatMessage = new Message();
    	chatMessage.setContent(promt);
    	chatMessage.setRole(Role.user);
    	final ChatRequest chat = cache.get(session.getId());
    	chat.getMessages().add(chatMessage);
    	final Basic remote = session.getBasicRemote();
    	Ollama.stream(
    			ollama.chat(chat), 
    			ChatResponse.class, 
    			(response) -> {
    				if(session.isOpen()) {
		    			try {
		        			remote.sendText(response.getMessage().getContent());
		    			}
		            	catch(Exception ex) {
		            		LOGGER.log(Level.WARNING, "sendText", ex);
		            	}
		    		}
					if(response.isDone()) {
						final ChatRequest newChat = cache.get(session.getId());
						newChat.getMessages().add(response.getMessage());
		            	cache.put(session.getId(), newChat);
		            	return false;
					}
					return session.isOpen();
					}, 
    			(err) -> {
    				LOGGER.log(Level.WARNING, "send", err);
    				}
    			);
    }
    
    @OnMessage
    public void onMessage(
    		final String message, 
    		final Session session) {
	}
	
	/**
	 * @param message
	 * @param session
	 */
	@OnMessage
    public void onMessage(
    		final InputStream message, 
    		final Session session) {
	}
	
	/**
	 * @param id
	 * @param text
	 * @return
	 */
	public ChatRequest put(final String id, final String text) {
		ChatRequest chat = cache.get(id);
		if(chat == null) {
			chat = new ChatRequest();
			chat.setMessages(new ArrayList<>());
			chat.setModel(modelName);
		}
		final Message message = new Message();
		message.setRole(Role.user);
		message.setContent(text);
		chat.getMessages().add(message);
		cache.put(id, chat);
		return chat;
	}
	
	/**
	 * @param query
	 * @param segments
	 * @return
	 */
	public String injectPrompt(final String query, final List<TextSegment> segments) {
    	final StringBuilder contents = new StringBuilder();
    	segments.forEach(segment -> {
    		contents.append(segment.text());
    		contents.append("\n\n");
    	});
    	return String.format(promptTemplates.getOrDefault(modelName, DEFAULT_PROMPT_TEMPLATE), query, contents.toString());
	}
    
    /**
     * @param sid
     * @param promt
     */
    public void send(final String sid, final String promt) {
    	final Optional<Session> session = this.session.getOpenSessions().stream().filter(ss -> ss.getId().equals(sid)).findFirst();
    	if(session.isPresent()) {
    		send(session.get(), promt);
    	}
    }

	@Override
	public HealthCheckResponse call() {
		if(session != null && session.isOpen()) {
			return HealthCheckResponse.up("EPF-lang-messaging");
		}
		return HealthCheckResponse.down("EPF-lang-messaging");
	}
}
