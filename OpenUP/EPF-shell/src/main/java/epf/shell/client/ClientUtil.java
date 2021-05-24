/**
 * 
 */
package epf.shell.client;

import java.net.URI;
import javax.json.JsonObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import epf.util.client.Client;
import epf.util.client.ClientQueue;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class ClientUtil {
	
	/**
	 * 
	 */
	private transient JacksonJsonProvider provider;
	
	/**
	 * 
	 */
	private transient ClientQueue clients;

	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		clients = new ClientQueue();
		clients.initialize();
		SimpleModule module = new SimpleModule();
        module.addDeserializer(JsonObject.class, new JsonObjectDeserializer());
        module.addSerializer(JsonObject.class, new JsonObjectSerializer());
		ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(module);
        provider = new JacksonJsonProvider(mapper);
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		clients.close();
	}
	
	/**
	 * @param uri
	 * @return
	 */
	public Client newClient(final URI uri) {
		return new Client(clients, uri, builder -> builder.register(provider));
	}
}
