package epf.tests.persistence;

import epf.client.gateway.GatewayUtil;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.persistence.client.Entities;
import epf.tests.client.ClientUtil;

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
		try(Client client = ClientUtil.newClient(GatewayUtil.get(Naming.PERSISTENCE))){
			client.authorization(token.toCharArray());
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
		try(Client client = ClientUtil.newClient(GatewayUtil.get(Naming.PERSISTENCE))){
			client.authorization(token.toCharArray());
	    	Entities.merge(client, schema, name, entityId, body);
		}
    }
	
	public static void remove(
			final String token,
			final String schema,
    		final String name,
    		final String entityId
            ) throws Exception {
		try(Client client = ClientUtil.newClient(GatewayUtil.get(Naming.PERSISTENCE))){
			client.authorization(token.toCharArray());
	    	Entities.remove(client, schema, name, entityId);
		}
    }
}
