package epf.query;

import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;
import epf.query.cache.EntityCache;
import epf.query.cache.QueryCache;
import epf.query.persistence.PersistenceCache;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Path(Naming.CACHE)
public class Query implements epf.client.query.Query {

	/**
	 * 
	 */
	@Inject @Readiness
	private transient EntityCache entityCache;
	
	/**
	 * 
	 */
	@Inject @Readiness
	private transient QueryCache queryCache;
	
	/**
	 * 
	 */
	@Inject @Readiness
	private transient PersistenceCache persistence;
	
	@Override
    public Response getEntity(
    		final String schema,
            final String name,
            final String entityId
            ) {
		final Optional<Object> entity = entityCache.getEntity(schema, name, entityId);
		return Response.ok(entity.orElseThrow(NotFoundException::new)).build();
	}

	@Override
	public Response countEntity(final String schema, final String entity) {
		final Optional<Integer> count = queryCache.countEntity(schema, entity);
		if(count.isPresent()) {
			return Response.ok().header(Naming.Query.ENTITY_COUNT, count.get()).build();
		}
		else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@Override
	public Response executeQuery(
			final String schema, 
			final List<PathSegment> paths, 
			final Integer firstResult, 
			final Integer maxResults,
			final SecurityContext context,
			final List<String> sort) throws Exception {
		return persistence.executeQuery(schema, paths, firstResult, maxResults, context, sort);
	}

	@Override
	public Response executeCountQuery(
			final String schema, 
			final List<PathSegment> paths, 
			final SecurityContext context)
			throws Exception {
		return persistence.executeCountQuery(schema, paths, context);
	}
}
