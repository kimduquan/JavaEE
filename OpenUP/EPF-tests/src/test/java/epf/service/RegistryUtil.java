package epf.service;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import epf.client.config.ConfigNames;
import epf.client.registry.Registry;
import epf.util.client.Client;

public class RegistryUtil {

    private static Map<String, URI> remotes;
    
    static Map<String, URI> list() throws Exception{
    	if(remotes == null) {
    		String registryUrl = System.getProperty(ConfigNames.REGISTRY_URL, "");
    		System.out.println("ConfigNames.REGISTRY_URL=" + registryUrl);
    		try(Client client = ClientUtil.newClient(new URI(registryUrl))){
    			remotes = new ConcurrentHashMap<>();
    			Registry.list(client)
    					.getLinks()
    					.forEach(link -> {
    						remotes.put(link.getRel(), link.getUri());
    					});
    		}
    	}
    	return remotes;
    }
    
    public static URI lookup(String name) throws Exception {
    	return list().get(name);
    }
}
