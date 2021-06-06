/**
 * 
 */
package epf.tests.rules;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.ws.rs.core.Response;
import epf.tests.client.ClientUtil;
import epf.tests.registry.RegistryUtil;
import epf.util.client.Client;

/**
 * @author PC
 *
 */
public class RulesUtil {

	public static void registerRuleExecutionSet(String token, Path ruleFile, String ruleSet) throws Exception {
		try(Client client = ClientUtil.newClient(RegistryUtil.lookup("rules", null))){
			client.authorization(token);
			try(InputStream input = Files.newInputStream(ruleFile)){
				try(Response response = epf.client.rules.admin.Admin.registerRuleExecutionSet(client, ruleSet, input)){
					response.getStatus();
				}
			}
		}
	}
	
	public static void registerRuleExecutionSet(String token, String ruleSet) throws Exception {
		try(Client client = ClientUtil.newClient(RegistryUtil.lookup("rules", null))){
			client.authorization(token);
			try(Response response = epf.client.rules.admin.Admin.deregisterRuleExecutionSet(client, ruleSet)){
				response.getStatus();
			}
		}
	}
}
