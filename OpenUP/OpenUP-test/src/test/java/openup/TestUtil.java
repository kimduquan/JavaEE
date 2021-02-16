/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import epf.client.RestClient;
import epf.client.config.ConfigNames;
import epf.client.security.Header;
import epf.client.security.Security;
import epf.util.client.Client;
import epf.util.client.ClientQueue;
import epf.util.ssl.DefaultHostnameVerifier;
import epf.util.ssl.DefaultSSLContext;
import java.net.URI;
import java.net.URL;
import javax.json.JsonObject;
import javax.net.ssl.SSLContext;
import openup.client.security.PasswordHash;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

/**
 *
 * @author FOXCONN
 */
public class TestUtil {
    
    private static SSLContext sslContext;
    private static URL url;
    private static ClientQueue clients;
    
    public static Header buildClient(RestClientBuilder rest) throws Exception{
        Header header = new Header();
        JacksonJsonProvider provider = buildJsonProvider();
        if(sslContext == null) {
        	sslContext = DefaultSSLContext.build();
        }
        if(rest != null) {
            rest = rest.hostnameVerifier(new DefaultHostnameVerifier())
                    .sslContext(sslContext)
                    .register(provider)
                    .baseUrl(url)
                    .register(header);
        }
        return header;
    }

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
    
    public static String login(Security security, Header header, String username, String password) throws Exception{
        String token = security.login("OpenUP", username, PasswordHash.hash(username, password.toCharArray()), url);
        header.setToken(token);
        return token;
    }
    
    public static void logout(Security security, Header header) throws Exception{
        security.logOut("OpenUP");
        header.setToken(null);
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
