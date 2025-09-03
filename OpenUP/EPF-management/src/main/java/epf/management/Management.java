package epf.management;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.management.schema.Principal;
import epf.naming.Naming;
import io.smallrye.common.annotation.RunOnVirtualThread;

@Path(Naming.MANAGEMENT)
@ApplicationScoped
public class Management {
	
	@Path("")
	@GET
	@RunOnVirtualThread
	public Principal authenticate(@Context final SecurityContext security) throws Exception {
		final Principal principal = new Principal();
		final JsonWebToken jwt = (JsonWebToken) security.getUserPrincipal();
		principal.setAddress((String) jwt.claim(Claims.address).orElse(null));
		principal.setBirthdate((String) jwt.claim(Claims.birthdate).orElse(null));
		principal.setEmail((String) jwt.claim(Claims.email).orElse(null));
		principal.setEmailVerified((String) jwt.claim(Claims.email_verified).orElse(null));
		principal.setFamilyName((String) jwt.claim(Claims.family_name).orElse(null));
		principal.setGender((String) jwt.claim(Claims.gender).orElse(null));
		principal.setGivenName((String) jwt.claim(Claims.given_name).orElse(null));
		principal.setLocale((String) jwt.claim(Claims.locale).orElse(null));
		principal.setMiddleName((String) jwt.claim(Claims.middle_name).orElse(null));
		principal.setName(jwt.getName());
		principal.setNickname((String) jwt.claim(Claims.nickname).orElse(null));
		principal.setPhoneNumber((String) jwt.claim(Claims.phone_number).orElse(null));
		principal.setPhoneNumberVerified((String) jwt.claim(Claims.phone_number_verified).orElse(null));
		principal.setPicture(null);
		principal.setPreferredUsername((String) jwt.claim(Claims.preferred_username).orElse(null));
		principal.setProfile(null);
		principal.setSubject(jwt.getSubject());
		principal.setUpdatedAt((String) jwt.claim(Claims.updated_at).orElse(null));
		principal.setWebsite(null);
		principal.setZoneinfo((String) jwt.claim(Claims.zoneinfo).orElse(null));
		return principal;
	}
}
