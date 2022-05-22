package epf.query;

import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.health.Readiness;
import epf.client.schema.EntityId;
import epf.naming.Naming;
import epf.query.internal.EntityCache;
import epf.query.internal.QueryCache;
import epf.query.internal.persistence.PersistenceCache;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Path(Naming.QUERY)
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
		throw new NotFoundException();
	}

	@Override
	public Response executeQuery(
			final String schema, 
			final List<PathSegment> paths, 
			final Integer firstResult, 
			final Integer maxResults,
			final SecurityContext context,
			final List<String> sort) throws Exception {
		if(!paths.isEmpty()) {
			final List<?> resultList = persistence.executeQuery(schema, paths, firstResult, maxResults, context, sort);
			return Response.ok(resultList).header(Naming.Query.ENTITY_COUNT, resultList.size()).build();
		}
		throw new NotFoundException();
	}

	@Override
	public Response executeCountQuery(
			final String schema, 
			final List<PathSegment> paths, 
			final SecurityContext context)
			throws Exception {
		if(!paths.isEmpty()) {
			final Object count = persistence.executeCountQuery(schema, paths, context);
	    	return Response.ok().header(Naming.Query.ENTITY_COUNT, count).build();
		}
		throw new NotFoundException();
	}

	@Override
	public Response fetchEntities(final List<EntityId> entityIds) {
		final List<Object> entities = entityCache.getEntities(entityIds);
		return Response.ok(entities).header(Naming.Query.ENTITY_COUNT, entities.size()).build();
	}
}
