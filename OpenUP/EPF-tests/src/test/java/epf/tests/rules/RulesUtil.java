package epf.tests.rules;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.ws.rs.core.Response;
import epf.client.gateway.GatewayUtil;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.tests.client.ClientUtil;
import epf.util.json.Adapter;
import epf.util.json.Decoder;
import epf.util.json.Encoder;

/**
 * @author PC
 *
 */
public class RulesUtil {

	public static void registerRuleExecutionSet(String token, Path ruleFile, String ruleSet) throws Exception {
		try(Client client = ClientUtil.newClient(GatewayUtil.get(Naming.RULES))){
			client.authorization(token.toCharArray());
			try(InputStream input = Files.newInputStream(ruleFile)){
				try(Response response = epf.client.rules.admin.Admin.registerRuleExecutionSet(client, ruleSet, input)){
					response.getStatus();
				}
			}
		}
	}
	
	public static void deregisterRuleExecutionSet(String token, String ruleSet) throws Exception {
		try(Client client = ClientUtil.newClient(GatewayUtil.get("rules"))){
			client.authorization(token.toCharArray());
			try(Response response = epf.client.rules.admin.Admin.deregisterRuleExecutionSet(client, ruleSet)){
				response.getStatus();
			}
		}
	}
	
	public static String encode(List<Object> input) throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withAdapters(new Adapter()))){
			Encoder encoder = new Encoder();
			return encoder.encodeArray(jsonb, input);
		}
	}
	
	public static List<Object> decode(String input) throws Exception{
		try(Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withAdapters(new Adapter()))){
			Decoder decoder = new Decoder();
			return decoder.decodeArray(jsonb, input);
		}
	}
}
