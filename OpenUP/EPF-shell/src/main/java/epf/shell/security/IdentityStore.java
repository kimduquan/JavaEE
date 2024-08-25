/**
 * 
 */
package epf.shell.security;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import epf.file.util.PathUtil;
import epf.util.logging.LogManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class IdentityStore implements ConstraintValidator<CallerPrincipal, Credential> {
	
	/**
	 * 
	 */
	private static final String TOKEN_FOLDER = "security";
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(IdentityStore.class.getName());
	
	/**
	 * @param credential
	 * @throws Exception
	 */
	protected void put(final Credential credential) throws Exception {
		final Path tokenFolder = PathUtil.of("", TOKEN_FOLDER);
    	tokenFolder.toFile().mkdirs();
    	final Path tokenFile = PathUtil.of("", TOKEN_FOLDER, credential.getTokenID());
		Files.write(tokenFile, Arrays.asList(credential.getRawToken()));
	}
	
	/**
	 * @param credential
	 * @throws Exception
	 */
	protected void remove(final Credential credential) throws Exception {
		if(credential.getTokenID() != null && !credential.getTokenID().isEmpty()) {
			final Path tokenFile = PathUtil.of("", TOKEN_FOLDER, credential.getTokenID());
			Files.delete(tokenFile);
		}
	}
	
	/**
	 * @param credential
	 * @return
	 * @throws Exception
	 */
	protected boolean validate(final Credential credential) throws Exception {
		boolean result = true;
		if(credential.getTokenID() != null && !credential.getTokenID().isEmpty()) {
    		final Path tokenFile = PathUtil.of("", TOKEN_FOLDER, credential.getTokenID());
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
