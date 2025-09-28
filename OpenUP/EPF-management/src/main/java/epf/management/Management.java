package epf.management;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import java.util.Optional;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.management.schema.Organization;
import epf.management.internal.OrganizationManagement;
import epf.management.schema.Principal;
import epf.management.schema.Session;
import epf.naming.Naming;
import io.smallrye.common.annotation.RunOnVirtualThread;

@Path(Naming.MANAGEMENT)
@ApplicationScoped
public class Management {
	
	@Inject
	transient OrganizationManagement organizationManagement;
	
	@POST
	@Path(Naming.Management.SESSION)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(Naming.Security.DEFAULT_ROLE)
	@RunOnVirtualThread
	public Session newSession(@Context final JsonWebToken jwt) throws Exception {
		final Principal principal = new Principal();
		principal.setAddress((String) jwt.claim(Claims.address).orElse(null));
		principal.setBirthdate((String) jwt.claim(Claims.birthdate).orElse(null));
		principal.setEmail((String) jwt.claim(Claims.email).orElse(null));
		principal.setEmailVerified((Boolean) jwt.claim(Claims.email_verified).orElse(null));
		principal.setFamilyName((String) jwt.claim(Claims.family_name).orElse(null));
		principal.setGender((String) jwt.claim(Claims.gender).orElse(null));
		principal.setGivenName((String) jwt.claim(Claims.given_name).orElse(null));
		principal.setLocale((String) jwt.claim(Claims.locale).orElse(null));
		principal.setMiddleName((String) jwt.claim(Claims.middle_name).orElse(null));
		principal.setName(jwt.getName());
		principal.setNickname((String) jwt.claim(Claims.nickname).orElse(null));
		principal.setPhoneNumber((String) jwt.claim(Claims.phone_number).orElse(null));
		principal.setPhoneNumberVerified((String) jwt.claim(Claims.phone_number_verified).orElse(null));
		principal.setPreferredUsername((String) jwt.claim(Claims.preferred_username).orElse(null));
		principal.setSubject(jwt.getSubject());
		principal.setUpdatedAt((Long) jwt.claim(Claims.updated_at).orElse(null));
		principal.setZoneinfo((String) jwt.claim(Claims.zoneinfo).orElse(null));
		
		Organization organization = null;
		final Optional<Organization> organizationClaim = organizationManagement.getOrganization(jwt);
		if(organizationClaim.isPresent()) {
			organization = organizationClaim.get();
		}
		else {
			organization = organizationManagement.createOrganization(jwt.getTokenID(), principal);
		}
		
		final Session session = new Session();
		session.setPrincipal(principal);
		session.setOrganization(organization);
		return session;
	}
}
