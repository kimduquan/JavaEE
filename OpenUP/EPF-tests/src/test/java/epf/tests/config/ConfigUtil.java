package epf.tests.config;

import java.util.Map;
import epf.client.gateway.GatewayUtil;
import epf.client.util.Client;
import epf.config.client.Config;
import epf.naming.Naming;
import epf.tests.client.ClientUtil;

public class ConfigUtil {
    private static Map<String, String> configs;
    
    static Map<String, String> properties(){
    	if(configs == null) {
    		try(Client client = ClientUtil.newClient(GatewayUtil.get(Naming.CONFIG))){
    			configs = Config.getProperties(client, null);
    		}
    		catch(Exception ex) {
    			ex.printStackTrace();
    		}
    	}
    	return configs;
    }
    
    public static String property(String name) {
    	return properties().get(name);
    }
}
