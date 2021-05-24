package epf.tests.client;

import java.net.URI;
import javax.json.JsonObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import epf.util.client.Client;
import epf.util.client.ClientQueue;

public class ClientUtil {

    private static ClientQueue clients;

	private static JacksonJsonProvider buildJsonProvider() {
		SimpleModule module = new SimpleModule();
        module.addDeserializer(JsonObject.class, new JsonObjectDeserializer());
        module.addSerializer(JsonObject.class, new JsonObjectSerializer());
		ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(module);
        JacksonJsonProvider provider = new JacksonJsonProvider(mapper);
		return provider;
	}
    
    static ClientQueue clients() {
    	if(clients == null) {
    		ClientQueue queue = new ClientQueue();
    		queue.initialize();
    		clients = queue;
    	}
    	return clients;
    }
    
    public static Client newClient(URI uri) {
    	return new Client(
    			clients(), 
    			uri, 
    			builder -> builder.register(buildJsonProvider())
    			);
    }
    
    public static void afterClass() {
    	if(clients != null) {
    		clients.close();
    	}
    }
}
