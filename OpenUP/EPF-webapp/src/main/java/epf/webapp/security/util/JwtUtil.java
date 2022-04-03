package epf.webapp.security.util;

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
	public void initialize(final String issuer, final String audience, final PublicKey verifyKey) {
		jwtConsumer = new JwtConsumerBuilder()
				.setExpectedAudience(audience)
				.setExpectedIssuer(issuer)
				.setRequireExpirationTime()
				.setRequireIssuedAt()
				.setRequireJwtId()
				.setRequireNotBefore()
				.setRequireSubject()
				.setVerificationKey(verifyKey)
	            .build();
	}
	
	/**
	 * @param token
	 * @return
	 */
	public JwtClaims process(final String token) throws Exception {
		if(jwtConsumer != null) {
			return jwtConsumer.processToClaims(token);
		}
		return null;
	}
	
	/**
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public static JwtClaims decode(final String jwt) throws Exception {
		return new JwtConsumerBuilder().setSkipSignatureVerification().setSkipDefaultAudienceValidation().build().process(jwt).getJwtClaims();
	}
}
