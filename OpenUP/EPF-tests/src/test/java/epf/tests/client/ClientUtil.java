package epf.tests.client;

import java.net.URI;
import java.nio.file.Path;
import java.security.KeyStore;
import javax.json.JsonObject;
import javax.net.ssl.SSLContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import epf.client.util.Client;
import epf.client.util.ClientQueue;
import epf.naming.Naming;
import epf.util.config.ConfigUtil;
import epf.util.security.KeyStoreUtil;
import epf.util.ssl.SSLContextUtil;

public class ClientUtil {

    private static ClientQueue clients;

	private static KeyStore trustStore;
	
	private static KeyStore keyStore;
	
	private transient static char[] keyPassword;
	
	private static SSLContext sslContext;

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
    		final Path trustStoreFile = ConfigUtil.getPath(Naming.Client.SSL_TRUST_STORE);
        	final String trustStoreType = ConfigUtil.getString(Naming.Client.SSL_TRUST_STORE_TYPE);
        	final char[] trustStorePassword = ConfigUtil.getChars(Naming.Client.SSL_TRUST_STORE_PASSWORD);
        	try {
        		trustStore = KeyStoreUtil.loadKeyStore(trustStoreType, trustStoreFile, trustStorePassword);
			} 
        	catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	final Path keyStoreFile = ConfigUtil.getPath(Naming.Client.SSL_KEY_STORE);
        	final String keyStoreType = ConfigUtil.getString(Naming.Client.SSL_KEY_STORE_TYPE);
        	final char[] keyStorePassword = ConfigUtil.getChars(Naming.Client.SSL_KEY_STORE_PASSWORD);
        	try {
        		keyStore = KeyStoreUtil.loadKeyStore(keyStoreType, keyStoreFile, keyStorePassword);
			} 
        	catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		clients = queue;
    		keyPassword = ConfigUtil.getChars(Naming.Client.SSL_KEY_PASSWORD);
    		try {
				sslContext = SSLContextUtil.getDefault(SSLContextUtil.TLS, keyStore, keyStorePassword);
			} 
    		catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return clients;
    }
    
    public static Client newClient(URI uri) {
    	return new Client(
    			clients(), 
    			uri, 
    			builder -> builder.register(buildJsonProvider()).trustStore(trustStore).keyStore(keyStore, keyPassword)
    			);
    }
    
    public static void afterClass() {
    	if(clients != null) {
    		clients.close();
    	}
    }
    
    public static SSLContext getSSLContext() {
    	return sslContext;
    }
}
