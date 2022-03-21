package epf.webapp.security;

import java.security.PublicKey;
import java.time.Duration;
import java.util.Base64;
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
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.security.client.Security;
import epf.util.config.ConfigUtil;
import epf.util.logging.LogManager;
import epf.util.security.KeyUtil;
import epf.webapp.GatewayUtil;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class EPFRememberMeIdentityStore implements RememberMeIdentityStore {
	
	/**
	 * 
	 */
	private transient final static Logger LOGGER = LogManager.getLogger(EPFRememberMeIdentityStore.class.getName());
	
	/**
	 * 
	 */
	private transient JwtConsumer jwtConsumer;
	
	/**
	 * 
	 */
	@Inject
	private transient GatewayUtil gatewayUtil;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			final String verifyKeyText = ConfigUtil.getString(Naming.Security.JWT.VERIFY_KEY);
			final String webAppUrl = ConfigUtil.getString(Naming.WebApp.WEB_APP_URL);
			final PublicKey verifyKey = KeyUtil.generatePublic("RSA", verifyKeyText, Base64.getDecoder(), "UTF-8");
			jwtConsumer = new JwtConsumerBuilder()
					.setExpectedAudience(webAppUrl)
					.setExpectedIssuer(Naming.EPF)
					.setRequireExpirationTime()
					.setRequireIssuedAt()
					.setRequireJwtId()
					.setRequireNotBefore()
					.setRequireSubject()
					.setVerificationKey(verifyKey)
		            .build();
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "[EPFRememberMeIdentityStore.jwtConsumer]", ex);
		}
	}

	@Override
	public CredentialValidationResult validate(final RememberMeCredential credential) {
		CredentialValidationResult result = CredentialValidationResult.NOT_VALIDATED_RESULT;
		if(jwtConsumer != null) {
			try {
				final JwtClaims claims = jwtConsumer.processToClaims(credential.getToken());
				final Set<String> groups = Set.copyOf(claims.getStringListClaimValue(Claims.groups.name()));
				if(groups.contains(Naming.Security.DEFAULT_ROLE)) {
					final TokenPrincipal principal = new TokenPrincipal(claims.getSubject(), credential.getToken());
					result = new CredentialValidationResult(principal, groups);
				}
			} 
			catch (InvalidJwtException | MalformedClaimException e) {
				LOGGER.log(Level.WARNING, "[EPFRememberMeIdentityStore.validate]", e);
				result = CredentialValidationResult.INVALID_RESULT;
			}
		}
		return result;
	}

	@Override
	public String generateLoginToken(final CallerPrincipal callerPrincipal, final Set<String> groups) {
		String newToken = "";
		if(callerPrincipal instanceof TokenPrincipal) {
			final TokenPrincipal principal = (TokenPrincipal) callerPrincipal;
			newToken = principal.getRawToken();
			try(Client client = gatewayUtil.newClient(Naming.SECURITY)){
				client.authorization(principal.getRawToken());
				newToken = Security.revoke(client, Duration.ofDays(1));
				principal.setRememberToken(newToken);
			} 
			catch (Exception e) {
				LOGGER.log(Level.SEVERE, "[EPFRememberMeIdentityStore.generateLoginToken]", e);
			}
		}
		return newToken;
	}

	@Override
	public void removeLoginToken(final String token) {
		try(Client client = gatewayUtil.newClient(Naming.SECURITY)){
			client.authorization(token);
			Security.logOut(client);
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[EPFRememberMeIdentityStore.removeLoginToken]", e);
		}
	}

}
