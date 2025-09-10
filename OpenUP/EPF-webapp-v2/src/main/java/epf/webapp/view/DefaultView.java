package epf.webapp.view;

import java.io.Serializable;
import epf.management.schema.Principal;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.identitystore.openid.OpenIdClaims;
import jakarta.security.enterprise.identitystore.openid.OpenIdContext;

@ViewScoped
@Named("default_view")
public class DefaultView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
    private OpenIdContext context;
	
	public Principal getPrincipal() {
		final Principal principal = new Principal();
		final OpenIdClaims claims = context.getClaims();
		principal.setSubject(claims.getSubject());
		principal.setAddress(claims.getAddress().orElse(null));
		principal.setBirthdate(claims.getBirthdate().orElse(null));
		principal.setEmail(claims.getEmail().orElse(null));
		//principal.setEmailVerified(claims.getEmailVerified().orElse(null));
		principal.setFamilyName(claims.getFamilyName().orElse(null));
		principal.setGender(claims.getGender().orElse(null));
		principal.setGivenName(claims.getGivenName().orElse(null));
		principal.setLocale(claims.getLocale().orElse(null));
		principal.setMiddleName(claims.getMiddleName().orElse(null));
		principal.setName(claims.getName().orElse(null));
		principal.setNickname(claims.getNickname().orElse(null));
		principal.setPhoneNumber(claims.getPhoneNumber().orElse(null));
		principal.setPhoneNumberVerified(claims.getPhoneNumberVerified().orElse(null));
		principal.setPicture(claims.getPicture().orElse(null));
		principal.setPreferredUsername(claims.getPreferredUsername().orElse(null));
		principal.setProfile(claims.getProfile().orElse(null));
		//principal.setUpdatedAt(claims.getUpdatedAt().orElse(null));
		principal.setWebsite(claims.getWebsite().orElse(null));
		principal.setZoneinfo(claims.getZoneinfo().orElse(null));
		return principal;
	}
}
