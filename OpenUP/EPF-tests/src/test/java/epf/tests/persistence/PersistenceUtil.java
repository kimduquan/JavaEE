/**
 * 
 */
package epf.tests.persistence;

import epf.client.gateway.GatewayUtil;
import epf.client.persistence.Entities;
import epf.tests.client.ClientUtil;
import epf.util.client.Client;

/**
 * @author PC
 *
 */
public class PersistenceUtil {

	public static <T> T persist(
			final String token,
    		final Class<T> cls,
    		final String schema,
    		final String name,
    		final T body
            ) throws Exception{
		try(Client client = ClientUtil.newClient(GatewayUtil.get("persistence"))){
			client.authorization(token);
	    	return Entities.persist(client, cls, schema, name, body);
		}
    }
	
	public static void merge(
			final String token,
			final String schema,
    		final String name,
    		final String entityId,
    		final Object body
            ) throws Exception {
		try(Client client = ClientUtil.newClient(GatewayUtil.get("persistence"))){
			client.authorization(token);
	    	Entities.merge(client, schema, name, entityId, body);
		}
    }
	
	public static void remove(
			final String token,
			final String schema,
    		final String name,
    		final String entityId
            ) throws Exception {
		try(Client client = ClientUtil.newClient(GatewayUtil.get("persistence"))){
			client.authorization(token);
	    	Entities.remove(client, schema, name, entityId);
		}
    }
}
