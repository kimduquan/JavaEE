package epf.webapp.security.auth;

import java.security.PublicKey;
import java.time.Duration;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.RememberMeCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.RememberMeIdentityStore;
import org.eclipse.microprofile.jwt.Claims;
import org.jose4j.jwt.JwtClaims;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.security.client.Security;
import epf.util.config.ConfigUtil;
import epf.util.logging.LogManager;
import epf.util.security.KeyUtil;
import epf.webapp.ConfigSource;
import epf.webapp.GatewayUtil;
import epf.webapp.security.TokenPrincipal;
import epf.webapp.security.util.JwtUtil;

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
	private transient ConfigSource config;
	
	/**
	 * 
	 */
	@Inject
	private transient SecurityAuth securityAuth;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			final String verifyKeyText = config.getProperty(Naming.Security.JWT.VERIFY_KEY);
			final String webAppUrl = ConfigUtil.getString(Naming.WebApp.WEB_APP_URL);
			final PublicKey verifyKey = KeyUtil.generatePublic("RSA", verifyKeyText, Base64.getDecoder(), "UTF-8");
			jwtUtil.initialize(Naming.EPF, webAppUrl, verifyKey);
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "[EPFRememberMeIdentityStore.jwtUtil]", ex);
		}
	}

	@Override
	public CredentialValidationResult validate(final RememberMeCredential credential) {
		CredentialValidationResult result = CredentialValidationResult.NOT_VALIDATED_RESULT;
		try {
			final char[] token = credential.getToken().toCharArray();
			final JwtClaims claims = JwtUtil.decode(token);
			if(Naming.EPF.equals(claims.getIssuer())) {
				final JwtClaims validClaims = jwtUtil.process(token);
				final Set<String> groups = new HashSet<>(validClaims.getStringListClaimValue(Claims.groups.name()));
				final TokenPrincipal principal = new TokenPrincipal(validClaims.getSubject(), token);
				result = new CredentialValidationResult(principal, groups);
			}
			else if(securityAuth.getProvider(claims.getIssuer()).validateIDToken(credential.getToken(), FacesContext.getCurrentInstance().getExternalContext().getSessionId(false))) {
				final IDTokenPrincipal principal = new IDTokenPrincipal(claims.getSubject(), token, claims.getClaimsMap());
				final Set<String> groups = new HashSet<>();
				groups.add(Naming.Security.DEFAULT_ROLE);
				result = new CredentialValidationResult(principal, groups);
			}
			else {
				result = CredentialValidationResult.INVALID_RESULT;
			}
		} 
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "[EPFRememberMeIdentityStore.validate]", e);
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
		else if(callerPrincipal instanceof IDTokenPrincipal) {
			final IDTokenPrincipal principal = (IDTokenPrincipal) callerPrincipal;
			newToken = principal.getId_token();
		}
		return new String(newToken);
	}

	@Override
	public void removeLoginToken(final String token) {
		try {
			final char[] raw = token.toCharArray();
			final JwtClaims claims = JwtUtil.decode(raw);
			if(Naming.EPF.equals(claims.getIssuer())) {
				try(Client client = gatewayUtil.newClient(Naming.SECURITY)){
					client.authorization(raw);
					Security.logOut(client);
				}
			}
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[EPFRememberMeIdentityStore.removeLoginToken]", e);
		}
	}
}
