package epf.security.auth.util;

import java.net.URL;
import java.security.PublicKey;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;

/**
 * @author PC
 *
 */
public class JwtUtil {
	
	/**
	 * 
	 */
	private transient JwtConsumer jwtConsumer;

	/**
	 * 
	 */
	public void initialize(final String issuer, final URL url, final PublicKey verifyKey) {
		final String audience = String.format("%s://%s/", url.getProtocol(), url.getAuthority());
		jwtConsumer = new JwtConsumerBuilder()
				.setEnableRequireIntegrity()
				.setExpectedAudience(audience)
				.setExpectedIssuer(issuer)
				.setRequireExpirationTime()
				.setRequireIssuedAt()
				.setRequireJwtId()
				.setRequireNotBefore()
				.setRequireSubject()
				.setVerificationKey(verifyKey)
				.setAllowedClockSkewInSeconds(300)
	            .build();
	}
	
	/**
	 * @param token
	 * @return
	 */
	public JwtClaims process(final char[] token) throws Exception {
		if(jwtConsumer != null) {
			return jwtConsumer.processToClaims(new String(token));
		}
		return null;
	}
	
	/**
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public static JwtClaims decode(final char[] jwt) throws Exception {
		return new JwtConsumerBuilder().setSkipSignatureVerification().setSkipDefaultAudienceValidation().build().process(new String(jwt)).getJwtClaims();
	}
}
