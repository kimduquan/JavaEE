/**
 * 
 */
package epf.portlet.schema;

import java.net.URI;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import epf.client.schema.Entity;
import epf.portlet.Name;
import epf.portlet.SessionUtil;
import epf.portlet.client.ClientUtil;
import epf.portlet.registry.RegistryUtil;
import epf.portlet.security.Principal;
import epf.util.client.Client;

/**
 * @author PC
 *
 */
@RequestScoped
public class SchemaUtil {
	
	/**
	 * 
	 */
	@Inject
	private transient SessionUtil sessionUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient RegistryUtil registryUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient ClientUtil clientUtil;
	
	/**
	 * @param unit
	 * @return
	 * @throws Exception
	 */
	public List<Entity> getEntities(final String unit) throws Exception{
		final Principal principal = sessionUtil.getAttribute(Name.SECURITY_PRINCIPAL);
		if(principal != null) {
			final URI schemaUrl = registryUtil.get("schema");
			try(Client client = clientUtil.newClient(schemaUrl)){
				client.authorization(principal.getToken().getRawToken());
				try(Response response = epf.client.schema.Schema.getEntities(client, unit)){
					return response.readEntity(new GenericType<List<Entity>>() {});
				}
			}
		}
		return null;
	}
}
