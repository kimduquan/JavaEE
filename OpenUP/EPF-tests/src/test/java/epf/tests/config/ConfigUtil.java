<<<<<<< HEAD:OpenUP/EPF-tests/src/test/java/epf/tests/service/ConfigUtil.java
package epf.tests.service;
=======
package epf.tests.config;
>>>>>>> remotes/origin/micro:OpenUP/EPF-tests/src/test/java/epf/tests/config/ConfigUtil.java

import java.util.Map;

import epf.client.config.Config;
import epf.tests.client.ClientUtil;
<<<<<<< HEAD:OpenUP/EPF-tests/src/test/java/epf/tests/service/ConfigUtil.java
=======
import epf.tests.registry.RegistryUtil;
>>>>>>> remotes/origin/micro:OpenUP/EPF-tests/src/test/java/epf/tests/config/ConfigUtil.java
import epf.util.client.Client;
import epf.util.logging.Logging;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigUtil {

	private static final Logger logger = Logging.getLogger(ConfigUtil.class.getName());
    private static Map<String, String> configs;
    
    static Map<String, String> properties(){
    	if(configs == null) {
    		try(Client client = ClientUtil.newClient(RegistryUtil.lookup("config", null))){
    			configs = Config.getProperties(client, null);
    		}
    		catch(Exception ex) {
    			logger.log(Level.SEVERE, "properties", ex);
    		}
    	}
    	return configs;
    }
    
    public static String property(String name) {
    	return properties().get(name);
    }
}
