/**
 * 
 */
package epf.tests.cache;

import javax.ws.rs.core.Response;
import epf.client.cache.Cache;
import epf.client.gateway.GatewayUtil;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.security.schema.Token;
import epf.tests.client.ClientUtil;

/**
 * @author PC
 *
 */
public interface CacheUtil {

	static Token getToken(String tokenId) {
		try(Client client = ClientUtil.newClient(GatewayUtil.get(Naming.CACHE))){
			return Cache.getToken(client, tokenId);
		} 
		catch (Exception e) {
			return null;
		}
	}
	
	static Response getEntity(String token, String schema, String entity, String id) throws Exception {
		try(Client client = ClientUtil.newClient(GatewayUtil.get(Naming.CACHE))){
			client.authorization(token.toCharArray());
			return Cache.getEntity(client, schema, entity, id);
		}
		catch (Exception e) {
			return null;
		}
	}
}
