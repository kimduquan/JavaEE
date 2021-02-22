package epf.service;

import java.net.URI;
import java.util.Map;
import epf.client.config.Config;
import epf.client.config.ConfigNames;
import epf.util.client.Client;

public class ConfigUtil {

    private static Map<String, String> configs;
    
    static Map<String, String> properties() throws Exception{
    	if(configs == null) {
    		String url = System.getProperty(ConfigNames.GATEWAY_URL, "");
    		System.out.println("ConfigNames.GATEWAY_URL=" + url);
    		try(Client client = ClientUtil.newClient(new URI(url + "config"))){
    			configs = Config.getProperties(client, null);
    		}
    	}
    	return configs;
    }
    
    public static String property(String name) throws Exception {
    	return properties().get(name);
    }
}
