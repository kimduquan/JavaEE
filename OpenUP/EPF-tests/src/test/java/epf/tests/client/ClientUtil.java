package epf.tests.client;

import java.net.URI;
import java.nio.file.Path;
import java.security.KeyStore;
import javax.json.JsonObject;
import javax.net.ssl.SSLContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import epf.client.internal.ClientQueue;
import epf.client.util.Client;
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

        	/*ClientContainer container = (ClientContainer) ContainerProvider.getWebSocketContainer();
        	container.getClient().getSslContextFactory().setSslContext(ClientUtil.getSSLContext());
        	container.getClient().getSslContextFactory().setTrustAll(true);
        	container.getClient().getSslContextFactory().setTrustStore(trustStore);
        	container.getClient().getSslContextFactory().setTrustStoreType(trustStoreType);
        	container.getClient().getSslContextFactory().setTrustStorePassword(new String(trustStorePassword));
        	container.getClient().getSslContextFactory().setKeyStore(keyStore);
        	container.getClient().getSslContextFactory().setKeyStorePassword(new String(keyStorePassword));
        	container.getClient().getSslContextFactory().setKeyStoreType(keyStoreType);
        	container.getClient().getSslContextFactory().setCertAlias("localhost");
        	container.getClient().getSslContextFactory().setTrustStorePath(trustStoreFile.toString());
        	container.getClient().getSslContextFactory().setKeyStorePath(keyStoreFile.toString());*/
    	}
    	return clients;
    }
    
    public static Client newClient(URI uri) {
    	return new Client(
    			clients().poll(uri, builder -> builder.register(buildJsonProvider()).trustStore(trustStore).keyStore(keyStore, keyPassword)), 
    			uri, 
    			clients()::add
    			);
    }
    
    public static void afterClass() {
    	if(clients != null) {
    		clients.close();
    	}
    }
    
    public static SSLContext getSSLContext() {
    	clients();
    	return sslContext;
    }
}
