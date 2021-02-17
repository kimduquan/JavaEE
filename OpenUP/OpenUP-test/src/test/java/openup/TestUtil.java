/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import epf.client.config.ConfigNames;
import epf.util.client.Client;
import epf.util.client.ClientQueue;
import epf.util.client.RestClient;
import java.net.URI;
import java.net.URL;
import javax.json.JsonObject;

/**
 *
 * @author FOXCONN
 */
public class TestUtil {
    
    private static URL url;
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
    
    public static URL url() throws Exception{
        if(url == null) {
        	url = new URL(System.getProperty(ConfigNames.OPENUP_GATEWAY_URL, ""));
        }
        return url;
    }
    
    public static Client newClient(URI uri) {
    	if(clients == null) {
    		ClientQueue queue = new ClientQueue();
    		queue.initialize();
    		clients = queue;
    	}
    	return new Client(
    			clients, 
    			uri, 
    			builder -> builder.register(buildJsonProvider())
    			);
    }
    
    public static RestClient newRestClient(URI uri) {
    	return new RestClient(
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
