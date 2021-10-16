package epf.tests.config;

import java.util.Map;
import epf.client.config.Config;
import epf.client.gateway.GatewayUtil;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.tests.client.ClientUtil;
import epf.util.logging.LogManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigUtil {

	private static final Logger logger = LogManager.getLogger(ConfigUtil.class.getName());
    private static Map<String, String> configs;
    
    static Map<String, String> properties(){
    	if(configs == null) {
    		try(Client client = ClientUtil.newClient(GatewayUtil.get(Naming.CONFIG))){
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
