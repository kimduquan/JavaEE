package epf.shell.security;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import epf.file.util.PathUtil;
import epf.util.logging.LogManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@ApplicationScoped
public class IdentityStore implements ConstraintValidator<CallerPrincipal, Credential> {
	
	private static final String TOKEN_FOLDER = "security";
	
	private static final Logger LOGGER = LogManager.getLogger(IdentityStore.class.getName());
	
	protected String getTokenId(final String token) throws Exception {
		final JwtConsumer consumer = new JwtConsumerBuilder().setSkipSignatureVerification().build();
		final JwtClaims claims = consumer.processToClaims(token);
		return claims.getClaimValueAsString("jti");
	}
	
	protected void put(final Credential credential) throws Exception {
		final Path tokenFolder = PathUtil.of("", TOKEN_FOLDER);
    	tokenFolder.toFile().mkdirs();
    	final Path tokenFile = PathUtil.of("", TOKEN_FOLDER, credential.getTokenID().replace(':', '_'));
		Files.write(tokenFile, Arrays.asList(credential.getRawToken()));
	}
	
	protected void remove(final Credential credential) throws Exception {
		if(credential.getTokenID() != null && !credential.getTokenID().isEmpty()) {
			final Path tokenFile = PathUtil.of("", TOKEN_FOLDER, credential.getTokenID().replace(':', '_'));
			Files.delete(tokenFile);
		}
	}
	
	protected boolean validate(final Credential credential) throws Exception {
		boolean result = true;
		if(credential.getTokenID() != null && !credential.getTokenID().isEmpty()) {
    		final Path tokenFile = PathUtil.of("", TOKEN_FOLDER, credential.getTokenID().replace(':', '_'));
    		result = Files.exists(tokenFile);
    		if(result) {
        		final List<String> lines = Files.readAllLines(tokenFile);
        		result = !lines.isEmpty();
        		if(result) {
        			credential.setRawToken(lines.get(0));
        		}
    		}
    	}
		return result;
	}

	@Override
	public boolean isValid(final Credential credential, final ConstraintValidatorContext context) {
		boolean result = false;
		try {
			result = validate(credential);
		} 
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "isValid", e);
		}
		return result;
	}
}
