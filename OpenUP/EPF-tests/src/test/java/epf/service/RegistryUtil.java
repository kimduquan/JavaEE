package epf.service;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import epf.client.config.ConfigNames;
import epf.client.registry.Registry;
import epf.util.client.Client;
import epf.util.logging.Logging;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistryUtil {

	private static final Logger logger = Logging.getLogger(RegistryUtil.class.getName());
    private static Map<String, URI> remotes;
    
    static Map<String, URI> list(){
    	if(remotes == null) {
    		String registryUrl = System.getProperty(ConfigNames.REGISTRY_URL, "");
    		try(Client client = ClientUtil.newClient(new URI(registryUrl))){
    			remotes = new ConcurrentHashMap<>();
    			Registry.list(client)
    					.forEach(link -> {
    						remotes.put(link.getRel(), link.getUri());
    					});
    		}
    		catch(Exception ex) {
    			logger.log(Level.SEVERE, "list", ex);
    		}
    	}
    	return remotes;
    }
    
    public static URI lookup(String name) {
    	return list().get(name);
    }
}
