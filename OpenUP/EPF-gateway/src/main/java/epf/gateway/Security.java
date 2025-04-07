package epf.gateway;

import java.util.Objects;
import java.util.Optional;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.gateway.util.RequestUtil;
import epf.naming.Naming;

@ApplicationScoped
public class Security {
	
	public boolean authenticate(final JsonWebToken jwt, final UriInfo uriInfo) {
		Objects.requireNonNull(jwt, "JsonWebToken");
		Objects.requireNonNull(uriInfo, "UriInfo");
		final Optional<String> tenant = RequestUtil.getTenant(uriInfo);
		final Optional<String> tenantClaim = jwt.claim(Naming.Management.TENANT);
		if(tenant.isPresent() && tenantClaim.isPresent()) {
			return tenant.get().equals(tenantClaim.get());
		}
		if(!tenant.isPresent() && !tenantClaim.isPresent()) {
			return true;
		}
		return false;
	}
}
