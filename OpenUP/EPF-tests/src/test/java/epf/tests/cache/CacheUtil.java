/**
 * 
 */
package epf.tests.cache;

import javax.ws.rs.core.Response;
import epf.client.cache.Cache;
import epf.client.gateway.GatewayUtil;
import epf.client.security.Token;
import epf.tests.client.ClientUtil;
import epf.util.client.Client;

/**
 * @author PC
 *
 */
public interface CacheUtil {

	static Token getToken(String tokenId) {
		try(Client client = ClientUtil.newClient(GatewayUtil.get("cache"))){
			return Cache.getToken(client, tokenId);
		} 
		catch (Exception e) {
			return null;
		}
	}
	
	static Response getEntity(String token, String entity, String id) throws Exception {
		try(Client client = ClientUtil.newClient(GatewayUtil.get("cache"))){
			client.authorization(token);
			return Cache.getEntity(client, entity, id);
		}
		catch (Exception e) {
			return null;
		}
	}
}
