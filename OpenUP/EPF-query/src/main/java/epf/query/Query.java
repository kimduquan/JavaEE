package epf.query;

import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;
import epf.naming.Naming.Query.Client;
import epf.query.client.EntityId;
import epf.query.internal.EntityCache;
import epf.query.internal.QueryCache;
import epf.query.internal.persistence.PersistenceQuery;
import epf.query.util.LinkUtil;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Path(Naming.QUERY)
public class Query implements epf.query.client.Query {

	/**
	 * 
	 */
	@Inject @Readiness
	transient EntityCache entityCache;
	
	/**
	 * 
	 */
	@Inject @Readiness
	transient QueryCache queryCache;
	
	/**
	 * 
	 */
	@Inject @Readiness
	transient PersistenceQuery persistence;
	
	/**
	 * 
	 */
	@Inject
	transient Search search;
	
	/**
	 *
	 */
	@Inject
	transient ManagedExecutor executor;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		entityCache.submit(executor);
		queryCache.submit(executor);
	}
	
	@Override
    public Response getEntity(
    		final String tenant,
    		final String schema,
            final String name,
            final String entityId
            ) {
		final Optional<Object> entity = entityCache.getEntity(tenant, schema, name, entityId);
		return Response.ok(entity.orElseThrow(NotFoundException::new)).build();
	}

	@Override
	public Response countEntity(
    		final String tenant,
    		final String schema, 
    		final String entity) {
		final Optional<Integer> count = queryCache.countEntity(tenant, schema, entity);
		if(count.isPresent()) {
			return Response.ok().header(Client.ENTITY_COUNT, count.get()).build();
		}
		throw new NotFoundException();
	}

	@Override
	public Response executeQuery(
    		final String tenant,
			final String schema, 
			final List<PathSegment> paths, 
			final Integer firstResult, 
			final Integer maxResults,
			final SecurityContext context,
			final List<String> sort) throws Exception {
		if(!paths.isEmpty()) {
			final List<?> resultList = persistence.executeQuery(tenant, schema, paths, firstResult, maxResults, context, sort);
			return Response.ok(resultList).header(Client.ENTITY_COUNT, resultList.size()).build();
		}
		throw new NotFoundException();
	}

	@Override
	public Response executeCountQuery(
    		final String tenant,
			final String schema, 
			final List<PathSegment> paths, 
			final SecurityContext context)
			throws Exception {
		if(!paths.isEmpty()) {
			final Object count = persistence.executeCountQuery(tenant, schema, paths, context);
	    	return Response.ok().header(Client.ENTITY_COUNT, count).build();
		}
		throw new NotFoundException();
	}

	@Override
	public Response fetchEntities(
    		final String tenant,
    		final List<EntityId> entityIds) {
		final List<Object> entities = entityCache.getEntities(tenant, entityIds);
		ResponseBuilder response = Response.ok(entities).header(Client.ENTITY_COUNT, entities.size());
		response = LinkUtil.links(response, "", entityIds);
		return response.build();
	}
	
	/**
	 * @return
	 */
	@Path(Naming.Query.SEARCH)
	public Search getSearch() {
		return search;
	}
}
