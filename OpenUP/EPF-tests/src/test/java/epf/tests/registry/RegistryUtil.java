<<<<<<< HEAD:OpenUP/EPF-tests/src/test/java/epf/tests/service/RegistryUtil.java
package epf.tests.service;
=======
package epf.tests.registry;
>>>>>>> remotes/origin/micro:OpenUP/EPF-tests/src/test/java/epf/tests/registry/RegistryUtil.java

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
<<<<<<< HEAD:OpenUP/EPF-tests/src/test/java/epf/tests/service/RegistryUtil.java

import epf.client.registry.Registry;
import epf.tests.client.ClientUtil;
=======
import epf.client.registry.Registry;
import epf.tests.client.ClientUtil;
import epf.tests.gateway.GatewayUtil;
>>>>>>> remotes/origin/micro:OpenUP/EPF-tests/src/test/java/epf/tests/registry/RegistryUtil.java
import epf.util.client.Client;
import epf.util.logging.Logging;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistryUtil {

	private static final Logger logger = Logging.getLogger(RegistryUtil.class.getName());
    private static Map<String, URI> remotes;
    
    static Map<String, URI> list(String version){
    	if(remotes == null) {
    		try {
    			URI gatewayUrl = GatewayUtil.getGatewayUrl();
				URI registryUrl = gatewayUrl.resolve("registry");
	    		try(Client client = ClientUtil.newClient(registryUrl)){
	    			remotes = new ConcurrentHashMap<>();
	    			Registry.list(client, version)
	    					.forEach(link -> {
	    						remotes.put(link.getRel(), link.getUri());
	    					});
	    		}
			}
    		catch(Exception ex) {
    			logger.log(Level.SEVERE, "list", ex);
    		}
    	}
    	return remotes;
    }
    
    public static URI lookup(String name, String version) {
    	return list(version).get(name);
    }
}
