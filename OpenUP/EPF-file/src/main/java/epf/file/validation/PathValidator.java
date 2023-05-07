package epf.file.validation;

import java.security.Principal;
import java.util.List;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 * 
 */
public interface PathValidator {
	
	/**
	 * 
	 */
	int USER_INDEX = 1;

	/**
	 * @param paths
	 * @param security
	 * @param httpMethod
	 */
	static void validate(final List<PathSegment> paths, final SecurityContext security, final String httpMethod) {
		final Principal principal = security.getUserPrincipal();
		final String principalName = principal.getName();
		final String firstPath = paths.get(0).getPath();
		if(!principalName.equals(firstPath)) {
			if(principal instanceof JsonWebToken) {
				final JsonWebToken jwt = (JsonWebToken) principal;
				if(jwt.getGroups().contains(firstPath)) {
					if(paths.size() > USER_INDEX) {
						final String secondPath = paths.get(1).toString();
						if(!secondPath.equals(principalName) && !httpMethod.equals(HttpMethod.GET)) {
							throw new ForbiddenException();
						}
					}
					else if(!httpMethod.equals(HttpMethod.GET)) {
						throw new ForbiddenException();
					}
				}
				else {
					throw new ForbiddenException();
				}
			}
			else {
				throw new ForbiddenException();
			}
		}
	}
}