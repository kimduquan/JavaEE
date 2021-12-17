/**
 * 
 */
package epf.persistence.security.internal;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.SecurityContext;

import epf.persistence.internal.context.Application;
import epf.persistence.internal.context.Credential;

/**
 * @author PC
 *
 */
public interface CredentialUtil {

	/**
	 * @param principal
	 * @param context
	 * @return
	 */
	static Credential getCredential(final Principal principal, final epf.persistence.internal.context.Context context){
		final Credential credential = context.getCredential(principal.getName());
		if(credential != null) {
			return credential;
		}
		throw new ForbiddenException();
	}

	/**
	 * @param context
	 * @param persistence
	 * @return
	 */
	static List<Credential> getCredentials(final SecurityContext context, final Application persistence) {
		final Principal principal = context.getUserPrincipal();
		if(principal != null) {
	    	final List<Credential> credentials = new ArrayList<>();
	    	final epf.persistence.internal.context.Context ctx = persistence.getDefaultContext();
    		final Credential credential = getCredential(principal, ctx);
    		credentials.add(credential);
	    	return credentials;
		}
		throw new ForbiddenException();
	}

}
