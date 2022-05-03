package epf.cache;

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
import epf.cache.persistence.Persistence;
import epf.cache.security.Security;
import epf.security.schema.Token;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Path(Naming.CACHE)
public class Cache implements epf.client.cache.Cache {
	
	/**
	 * 
	 */
	@Inject @Readiness
	private transient Security security;

	/**
	 * 
	 */
	@Inject @Readiness
	private transient Persistence persistence;
	
	@Override
    public Response getEntity(
    		final String schema,
            final String name,
            final String entityId
            ) {
		final Optional<Object> entity = persistence.getEntity(schema, name, entityId);
		return Response.ok(entity.orElseThrow(NotFoundException::new)).build();
	}

	@Override
	public Token getToken(final String tokenId) {
		return security.getToken(tokenId);
	}

	@Override
	public Response executeQuery(
			final String schema, 
			final List<PathSegment> paths, 
			final Integer firstResult, 
			final Integer maxResults,
			final SecurityContext context,
			final List<String> sort) {
		return persistence.executeQuery(schema, paths, firstResult, maxResults, context, sort);
	}
}
