/**
 * 
 */
package epf.persistence.security.internal;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;

import epf.persistence.internal.context.Application;
import epf.persistence.internal.context.Credential;
import epf.persistence.internal.context.Session;

/**
 * @author PC
 *
 */
public interface SessionUtil {

	/**
	 * @param principal
	 * @param context
	 * @return
	 */
	static Session getSession(final Principal principal, final epf.persistence.internal.context.Context context){
		final JsonWebToken jwt = (JsonWebToken)principal;
		return getSession(principal.getName(), context, jwt.getTokenID());
	}
	
	/**
	 * @param principalName
	 * @param context
	 * @param tokenId
	 * @return
	 */
	static Session getSession(final String principalName, final epf.persistence.internal.context.Context context, final String tokenId) {
		final Credential credential = context.getCredential(principalName);
		return credential.getSession(tokenId);
	}

	/**
	 * @param context
	 * @param persistence
	 * @return
	 */
	static List<Session> getSessions(final SecurityContext context, final Application persistence) {
		List<Session> sessions = new ArrayList<>();
		final Principal principal = context.getUserPrincipal();
		if(principal instanceof JsonWebToken) {
			final epf.persistence.internal.context.Context ctx = persistence.getDefaultContext();
    		final Session session = getSession(principal, ctx);
        	if(session != null) {
        		sessions.add(session);
        	}
		}
	    return sessions;
	}

	/**
	 * @param principal
	 * @param context
	 * @return
	 */
	static Session removeSession(final Principal principal, final epf.persistence.internal.context.Context context){
	    final JsonWebToken jwt = (JsonWebToken)principal;
	    return removeSession(principal.getName(), context, jwt.getTokenID());
	}
	
	/**
	 * @param principalName
	 * @param context
	 * @param tokenId
	 * @return
	 */
	static Session removeSession(final String principalName, final epf.persistence.internal.context.Context context, final String tokenId) {
		final Credential credential = context.getCredential(principalName);
	    if(credential != null ){
	    	final Session session = credential.removeSession(tokenId);
	    	if(session != null) {
	    		return session;
	    	}
	    }
	    throw new ForbiddenException();
	}

	/**
	 * @param context
	 * @param persistence
	 * @return
	 */
	static List<Session> removeSessions(final SecurityContext context, final Application persistence) {
		final Principal principal = context.getUserPrincipal();
	    if(principal != null && principal instanceof JsonWebToken){
	    	final List<Session> sessions = new ArrayList<>();
	    	final epf.persistence.internal.context.Context ctx = persistence.getDefaultContext();
    		final Session session = removeSession(principal, ctx);
        	sessions.add(session);
	    	return sessions;
	    }
	    throw new ForbiddenException();
	}

}
