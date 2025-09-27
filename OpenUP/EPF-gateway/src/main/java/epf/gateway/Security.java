package epf.gateway;

import java.util.Objects;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.management.util.OrganizationUtil;

@ApplicationScoped
public class Security {
	
	public boolean authenticate(final JsonWebToken jwt) {
		Objects.requireNonNull(jwt, "JsonWebToken");
		return OrganizationUtil.getOrganizationId(jwt).isPresent();
	}
}
