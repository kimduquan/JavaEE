package epf.management.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import java.util.Map.Entry;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.naming.Naming;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

public interface OrganizationUtil {
	
	static Optional<String> getOrganizationId(final JsonWebToken jwt) {
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
	
	static String getDefaultPersistenceDatabase(final String organizationId) {
		return organizationId;
	}
	
	static String getDefaultPersistenceUserName(final String organizationId) {
		return Naming.PERSISTENCE + "-" + organizationId;
	}
	
	static String getDefaultQueryUserName(final String organizationId) {
		return Naming.QUERY + "-" + organizationId;
	}
	
	static String getDefaultPersistencePassword(final String organizationId) {
		return Base64.getEncoder().withoutPadding().encodeToString(getDefaultPersistenceUserName(organizationId).getBytes(StandardCharsets.UTF_8));
	}
	
	static String getDefaultQueryPassword(final String organizationId) {
		return Base64.getEncoder().withoutPadding().encodeToString(getDefaultQueryUserName(organizationId).getBytes(StandardCharsets.UTF_8));
	}
}
