package epf.cache;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import org.eclipse.microprofile.health.Readiness;
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

	@Override
	public Token getToken(final String tokenId) {
		return security.getToken(tokenId);
	}
}
