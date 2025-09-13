package epf.management.util;

import java.util.Optional;
import java.util.Map.Entry;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.naming.Naming;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

public interface OrganizationUtil {
	
	static Optional<String> getOrganizationId(final JsonWebToken jwt) throws Exception {
		String organizationId = null;
		final Optional<?> organizationClaim = jwt.claim(Naming.Management.ORGANIZATION);
		if(organizationClaim.isPresent()) {
			final JsonObject organizationValue = (JsonObject) organizationClaim.get();
			for(Entry<String, JsonValue> organizationEntry : organizationValue.entrySet()) {
				organizationId = organizationEntry.getValue().asJsonObject().getString("id");
			}
		}
		return Optional.ofNullable(organizationId);
	}
}
