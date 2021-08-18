package epf.tests.config;

import java.util.Map;
import epf.client.config.Config;
import epf.client.gateway.GatewayUtil;
import epf.tests.client.ClientUtil;
import epf.util.client.Client;
import epf.util.logging.Logging;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigUtil {

	private static final Logger logger = Logging.getLogger(ConfigUtil.class.getName());
    private static Map<String, String> configs;
    
    static Map<String, String> properties(){
    	if(configs == null) {
    		try(Client client = ClientUtil.newClient(GatewayUtil.get("config"))){
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
