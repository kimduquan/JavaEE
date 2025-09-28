package epf.file.validation;

import java.util.List;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.PathSegment;
import org.eclipse.microprofile.jwt.JsonWebToken;

public interface PathValidator {
	
	int USER_INDEX = 1;

	static void validate(final List<PathSegment> paths, final JsonWebToken jwt, final String httpMethod) {
		final String principalName = jwt.getName();
		final String firstPath = paths.get(0).getPath();
		if(!principalName.equals(firstPath)) {
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
	}
}
