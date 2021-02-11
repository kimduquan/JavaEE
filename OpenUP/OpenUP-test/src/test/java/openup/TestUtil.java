/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import java.net.URL;
import javax.json.JsonObject;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import openup.client.config.ConfigNames;
import openup.client.security.Header;
import openup.client.security.PasswordHash;
import openup.client.security.Security;
import openup.client.ssl.DefaultHostnameVerifier;
import openup.client.ssl.DefaultSSLContext;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

/**
 *
 * @author FOXCONN
 */
public class TestUtil {
    
    private static RestClientBuilder restBuilder;
    private static SSLContext sslContext;
    private static ClientBuilder builder;
    private static URL url;
    private static Header header;
    private static Client client;
    
    private static Security security;
    
    public static void beforeClass() throws Exception{
        
        url = new URL(System.getProperty(ConfigNames.OPENUP_GATEWAY_URL, ""));
        sslContext = DefaultSSLContext.build();
        header = new Header();
        
        SimpleModule module = new SimpleModule();
        module.addDeserializer(JsonObject.class, new JsonObjectDeserializer());
        module.addSerializer(JsonObject.class, new JsonObjectSerializer());
		ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(module);
        JacksonJsonProvider provider = new JacksonJsonProvider(mapper);
        
        restBuilder = RestClientBuilder.newBuilder()
                .hostnameVerifier(new DefaultHostnameVerifier())
                .sslContext(sslContext)
                .register(provider)
                .baseUrl(url)
                .register(header);
        
        builder = ClientBuilder.newBuilder()
                .hostnameVerifier(new DefaultHostnameVerifier())
                .sslContext(sslContext)
                .register(provider)
                .register(header);
        
        client = builder.build();
    }
    
    public static void afterClass(){
        client.close();
    }
    
    public static Header header(){
        return header;
    }
    
    public static Client client(){
        return client;
    }
    
    public static RestClientBuilder rest(){
        return restBuilder;
    }
    
    public static URL url(){
        return url;
    }
    
    public static String login(String username, String password) throws Exception{
        if(security == null){
            security = restBuilder.build(Security.class);
        }
        String token = security.login("OpenUP", username, PasswordHash.hash(username, password.toCharArray()), url);
        header.setToken(token);
        return token;
    }
    
    public static void logout(String token) throws Exception{
        if(security == null){
            security = restBuilder.build(Security.class);
        }
        header.setToken(token);
        security.logOut("OpenUP");
    }
}
