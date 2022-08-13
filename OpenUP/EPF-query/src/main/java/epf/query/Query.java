package epf.query;

import java.util.List;
import java.util.Optional;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.health.Readiness;
import epf.client.schema.EntityId;
import epf.naming.Naming;
import epf.query.internal.EntityCache;
import epf.query.internal.QueryCache;
import epf.query.internal.TokenCache;
import epf.query.internal.persistence.PersistenceCache;
import epf.query.util.LinkUtil;
import epf.security.schema.Token;

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
	
	/**
	 *
	 */
	@Inject @Readiness
	private transient TokenCache tokenCache;
	
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
			return Response.ok().header(Naming.Query.ENTITY_COUNT, count.get()).build();
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
			return Response.ok(resultList).header(Naming.Query.ENTITY_COUNT, resultList.size()).build();
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
	    	return Response.ok().header(Naming.Query.ENTITY_COUNT, count).build();
		}
		throw new NotFoundException();
	}

	@Override
	public Response fetchEntities(
    		final String tenant,
    		final List<EntityId> entityIds) {
		final List<Object> entities = entityCache.getEntities(tenant, entityIds);
		ResponseBuilder response = Response.ok(entities).header(Naming.Query.ENTITY_COUNT, entities.size());
		response = LinkUtil.links(response, "", entityIds);
		return response.build();
	}

	@PermitAll
	@Override
	public Token getToken(final String tokenHash) {
		final Optional<Token> token = tokenCache.getToken(tokenHash);
		return token.orElseThrow(NotFoundException::new);
	}
}
