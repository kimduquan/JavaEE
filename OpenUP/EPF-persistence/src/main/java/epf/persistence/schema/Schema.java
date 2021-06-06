/**
 * 
 */
package epf.persistence.schema;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import epf.persistence.Request;
import epf.persistence.impl.Entity;
import epf.schema.roles.Role;

/**
 * @author PC
 *
 */
@Path("schema")
@RolesAllowed(Role.DEFAULT_ROLE)
@RequestScoped
public class Schema implements epf.client.schema.Schema {
	
	/**
	 * 
	 */
	@Inject
    private transient Request cache;
	
	/**
	 * 
	 */
	@Context
    private transient SecurityContext context;
	
	/**
	 * @param <T>
	 * @param unit
	 * @return
	 */
	protected <T> List<Entity<T>> findEntities(final String unit){
    	final List<Entity<T>> entities = cache.findEntities(unit, context.getUserPrincipal());
    	if(entities.isEmpty()){
            throw new NotFoundException();
        }
        return entities;
    }

	@Override
	public Response getEntities(final String unit) {
		final EntityBuilder builder = new EntityBuilder();
		final List<epf.client.schema.Entity> entityTypes = findEntities(unit)
				.stream()
				.map(builder::build)
				.collect(Collectors.toList());
		return Response.ok(entityTypes).build();
	}
}
