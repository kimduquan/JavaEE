/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import epf.client.config.ConfigNames;
import epf.client.security.Security;
import epf.util.client.Client;
import epf.util.client.ClientQueue;
import epf.util.security.PasswordHash;
import java.net.URI;
import java.net.URL;
import javax.json.JsonObject;

/**
 *
 * @author FOXCONN
 */
public class TestUtil {
    
    private static URL gateway_url;
    private static ClientQueue clients;
	private static URI securityUrl;

	private static JacksonJsonProvider buildJsonProvider() {
		SimpleModule module = new SimpleModule();
        module.addDeserializer(JsonObject.class, new JsonObjectDeserializer());
        module.addSerializer(JsonObject.class, new JsonObjectSerializer());
		ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(module);
        JacksonJsonProvider provider = new JacksonJsonProvider(mapper);
		return provider;
	}
    
    public static URL gateway_url() throws Exception{
        if(gateway_url == null) {
        	gateway_url = new URL(System.getProperty(ConfigNames.GATEWAY_URL, ""));
        }
        return gateway_url;
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
    
    public static void afterClass() {
    	if(clients != null) {
    		clients.close();
    	}
    }
    
    public static String login(String unit, String username, String password) throws Exception {
    	if(securityUrl == null) {
    		securityUrl = new URI(gateway_url().toString() + "security");
    	}
    	String token;
    	try(Client client = TestUtil.newClient(securityUrl)){
    		token = Security.login(client, unit, username, PasswordHash.hash(username, password.toCharArray()), TestUtil.gateway_url());
    	}
    	return token;
    }
    
    public static void logOut(String unit, String token) throws Exception {
    	if(securityUrl == null) {
    		securityUrl = new URI(gateway_url().toString() + "security");
    	}
    	try(Client client = TestUtil.newClient(securityUrl)){
    		client.authorization(token);
    		token = Security.logOut(client, unit);
    	}
    }
}
