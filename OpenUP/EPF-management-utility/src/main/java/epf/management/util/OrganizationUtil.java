package epf.management.util;

import java.util.Optional;
import java.util.Map.Entry;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.management.schema.Organization;
import epf.naming.Naming;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

public interface OrganizationUtil {

	static Optional<Organization> getOrganization(final JsonWebToken jwt) throws Exception {
		final Optional<?> organizationClaim = jwt.claim(Naming.Management.ORGANIZATION);
		if(organizationClaim.isPresent()) {
			final Organization organization = new Organization();
			final JsonObject organizationValue = (JsonObject) organizationClaim.get();
			for(Entry<String, JsonValue> organizationEntry : organizationValue.entrySet()) {
				organization.setName(organizationEntry.getKey());
				organization.setId(organizationEntry.getValue().asJsonObject().getString("id"));
			}
			return Optional.of(organization);
		}
		return Optional.empty();
	}
}
