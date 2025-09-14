package epf.management.security.util;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;

public class SecurityUtil {

	public static String generateToken(final String secret) throws Exception {
		final JwtClaimsBuilder builder = Jwt.claims();
		return builder.signWithSecret(secret);
	}
}
