/**
 * 
 */
package epf.persistence.security;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.persistence.context.Application;
import epf.persistence.context.Credential;
import epf.persistence.context.Session;

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
	static Session getSession(final Principal principal, final epf.persistence.context.Context context){
		final Credential credential = context.getCredential(principal.getName());
		final JsonWebToken jwt = (JsonWebToken)principal;
		return credential.getSession(jwt.getTokenID());
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
			final epf.persistence.context.Context ctx = persistence.getDefaultContext();
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
	static Session removeSession(final Principal principal, final epf.persistence.context.Context context){
		final Credential credential = context.getCredential(principal.getName());
	    if(credential != null ){
	    	final JsonWebToken jwt = (JsonWebToken)principal;
	    	final Session session = credential.removeSession(jwt.getTokenID());
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
	    	final epf.persistence.context.Context ctx = persistence.getDefaultContext();
    		final Session session = removeSession(principal, ctx);
        	sessions.add(session);
	    	return sessions;
	    }
	    throw new ForbiddenException();
	}

}
