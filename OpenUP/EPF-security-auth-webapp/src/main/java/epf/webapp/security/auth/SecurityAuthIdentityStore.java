package epf.webapp.security.auth;

import java.net.URL;
import java.security.PublicKey;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.RememberMeCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.RememberMeIdentityStore;
import org.eclipse.microprofile.jwt.Claims;
import org.jose4j.jwt.JwtClaims;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.security.auth.util.JwtUtil;
import epf.security.client.Security;
import epf.util.logging.LogManager;
import epf.util.security.KeyUtil;
import epf.webapp.internal.ConfigUtil;
import epf.webapp.internal.GatewayUtil;
import epf.webapp.internal.TokenPrincipal;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class SecurityAuthIdentityStore implements RememberMeIdentityStore {
	
	/**
	 * 
	 */
	private transient final static Logger LOGGER = LogManager.getLogger(SecurityAuthIdentityStore.class.getName());
	
	/**
	 * 
	 */
	private transient final JwtUtil jwtUtil = new JwtUtil();
	
	/**
	 * 
	 */
	@Inject
	private transient GatewayUtil gatewayUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient ConfigUtil config;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			final String verifyKeyText = config.getProperty(Naming.Security.JWT.VERIFIER_PUBLIC_KEY);
			final URL webAppUrl = epf.util.config.ConfigUtil.getURL(Naming.WebApp.WEB_APP_URL);
			final PublicKey verifyKey = KeyUtil.decodePublicKey("RSA", verifyKeyText.getBytes("UTF-8"));
			jwtUtil.initialize(Naming.EPF, webAppUrl, verifyKey);
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "[SecurityAuthIdentityStore.jwtUtil]", ex);
		}
	}

	@Override
	public CredentialValidationResult validate(final RememberMeCredential credential) {
		CredentialValidationResult result = CredentialValidationResult.NOT_VALIDATED_RESULT;
		try {
			final char[] token = credential.getToken().toCharArray();
			final JwtClaims validClaims = jwtUtil.process(token);
			final Set<String> groups = new HashSet<>(validClaims.getStringListClaimValue(Claims.groups.name()));
			final TokenPrincipal principal = new TokenPrincipal(validClaims.getClaimValueAsString(Claims.upn.name()), token, validClaims.getClaimsMap());
			principal.setRememberToken(principal.getRawToken());
			result = new CredentialValidationResult(principal, groups);
		} 
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "[SecurityAuthIdentityStore.validate]", e);
			result = CredentialValidationResult.INVALID_RESULT;
		}
		return result;
	}

	@Override
	public String generateLoginToken(final CallerPrincipal callerPrincipal, final Set<String> groups) {
		char[] newToken = new char[0];
		if(callerPrincipal instanceof TokenPrincipal) {
			final TokenPrincipal principal = (TokenPrincipal) callerPrincipal;
			newToken = principal.getRawToken();
			try(Client client = gatewayUtil.newClient(Naming.SECURITY)){
				client.authorization(principal.getRawToken());
				newToken = Security.revoke(client, Duration.ofDays(1)).toCharArray();
				principal.setRememberToken(newToken);
			} 
			catch (Exception e) {
				LOGGER.log(Level.SEVERE, "[EPFRememberMeIdentityStore.generateLoginToken]", e);
			}
		}
		return new String(newToken);
	}

	@Override
	public void removeLoginToken(final String token) {
		try(Client client = gatewayUtil.newClient(Naming.SECURITY)){
			client.authorization(token.toCharArray());
			Security.logOut(client);
		}
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "[EPFRememberMeIdentityStore.removeLoginToken]", e);
		}
	}
}
