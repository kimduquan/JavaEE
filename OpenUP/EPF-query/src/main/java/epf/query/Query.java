package epf.query;

import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;
import epf.naming.Naming.Query.Client;
import epf.query.client.EntityId;
import epf.query.internal.EntityCache;
import epf.query.internal.QueryCache;
import epf.query.persistence.QueryPersistence;
import epf.query.util.LinkUtil;
import epf.schema.utility.Request;
import epf.util.concurrent.Executor;

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
	transient QueryPersistence persistence;
	
	/**
	 * 
	 */
	@Inject
	transient Search search;
	
	/**
	 *
	 */
	@Inject
	transient Executor executor;
	
	/**
	 * 
	 */
	@Inject
	Request request;
	
	@Override
    public Response getEntity(
    		final String tenant,
    		final String schema,
            final String name,
            final String entityId
            ) {
		request.setSchema(schema);
		request.setTenant(tenant);
		final Optional<Object> entity = entityCache.getEntity(name, entityId);
		return Response.ok(entity.orElseThrow(NotFoundException::new)).build();
	}

	@Override
	public Response countEntity(
    		final String tenant,
    		final String schema, 
    		final String entity) {
		request.setSchema(schema);
		request.setTenant(tenant);
		final Optional<Integer> count = queryCache.countEntity(entity);
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
		request.setSchema(schema);
		request.setTenant(tenant);
		if(!paths.isEmpty()) {
			final List<?> resultList = persistence.executeQuery(paths, firstResult, maxResults, context, sort);
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
		request.setSchema(schema);
		request.setTenant(tenant);
		if(!paths.isEmpty()) {
			final Object count = persistence.executeCountQuery(paths, context);
	    	return Response.ok().header(Client.ENTITY_COUNT, count).build();
		}
		throw new NotFoundException();
	}

	@Override
	public Response fetchEntities(
    		final String tenant,
    		final List<EntityId> entityIds) {
		request.setTenant(tenant);
		final List<Object> entities = entityCache.getEntities(entityIds);
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
