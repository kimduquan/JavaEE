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
import epf.client.schema.util.EmbeddableComparator;
import epf.client.schema.util.EntityComparator;
import epf.persistence.Request;
import epf.persistence.impl.Embeddable;
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
	 * @return
	 */
	protected <T> List<Entity<T>> findEntities(){
    	final List<Entity<T>> entities = cache.findEntities(context.getUserPrincipal());
    	if(entities.isEmpty()){
            throw new NotFoundException();
        }
        return entities;
    }
	
	/**
	 * @param <T>
	 * @return
	 */
	protected <T> List<Embeddable<T>> findEmbeddables(){
    	final List<Embeddable<T>> embeddables = cache.findEmbeddables(context.getUserPrincipal());
    	if(embeddables.isEmpty()){
            throw new NotFoundException();
        }
        return embeddables;
    }

	@Override
	public Response getEntities() {
		final EntityBuilder builder = new EntityBuilder();
		final EntityComparator comparator = new EntityComparator();
		final List<epf.client.schema.Entity> entities = findEntities()
				.stream()
				.map(builder::build)
				.sorted(comparator)
				.collect(Collectors.toList());
		return Response.ok(entities).build();
	}

	@Override
	public Response getEmbeddables() {
		final EmbeddableBuilder builder = new EmbeddableBuilder();
		final EmbeddableComparator comparator = new EmbeddableComparator();
		final List<epf.client.schema.Embeddable> embeddables = findEmbeddables()
				.stream()
				.map(builder::build)
				.sorted(comparator)
				.collect(Collectors.toList());
		return Response.ok(embeddables).build();
	}
}
