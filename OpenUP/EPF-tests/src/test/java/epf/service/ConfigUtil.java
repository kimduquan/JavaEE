package epf.service;

import java.util.Map;
import epf.client.config.Config;
import epf.util.client.Client;

public class ConfigUtil {

    private static Map<String, String> configs;
    
    static Map<String, String> properties() throws Exception{
    	if(configs == null) {
    		try(Client client = ClientUtil.newClient(RegistryUtil.lookup("config"))){
    			configs = Config.getProperties(client, null);
    		}
    	}
    	return configs;
    }
    
    public static String property(String name) throws Exception {
    	return properties().get(name);
    }
}
