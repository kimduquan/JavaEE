package epf.shell.security;

import epf.naming.Naming;
import epf.security.schema.Token;
import epf.shell.Function;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.ws.rs.core.MultivaluedHashMap;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * @author PC
 *
 */
@Command(name = Naming.SECURITY)
@RequestScoped
@Function
public class Security {
	
	/**
	 * 
	 */
	public static final String TOKEN_ARG = "--token";
	/**
	 * 
	 */
	public static final String TOKEN_DESC = "Token";
	
	/**
	 * 
	 */
	@Inject
	transient IdentityStore identityStore;
	
	/**
	 * 
	 */
	@RestClient
	transient SecurityClient security;
	
	/**
	 * 
	 */
	@ConfigProperty(name = Naming.Shell.SHELL_URL)
	@Inject
	String shellUrl;

	/**
	 * @param user
	 * @param password
	 * @return
	 * @throws Exception
	 */
	@Command(name = "login")
	public String login(
			@Option(names = {"-u", "--user"}, required = true, description = "User name")
			@NotBlank
			final String user,
			@Option(names = {"-p", "--password"}, required = true, description = "Password", interactive = true)
		    @NotEmpty
			final char... password
			) throws Exception {
		
		return security.login(user, new String(password), shellUrl);
	}
	
	/**
	 * @param credential
	 * @return
	 * @throws Exception
	 */
	@Command(name = "logout")
	public String logout(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential
			) throws Exception {
		identityStore.remove(credential);
		return security.logOut(credential.getAuthHeader());
	}
	
	/**
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@Command(name = "auth")
	public Token authenticate(
			@Option(names = {"-t", TOKEN_ARG}, description = TOKEN_DESC) 
			final String token) throws Exception {
		final Credential credential = new Credential();
		credential.setRawToken(token);
		final Token authToken = security.authenticate(credential.getAuthHeader());
		credential.setTokenID(authToken.getTokenID());
		identityStore.put(credential);
		return authToken;
	}
	
	/**
	 * @param credential
	 * @param password
	 * @throws Exception
	 */
	@Command(name = "update")
	public void update(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-p", "--password"}, description = "Password", interactive = true)
		    final char... password
		    ) throws Exception {
		security.update(credential.getAuthHeader(), new String(password));
	}
	
	/**
	 * @param credential
	 * @return
	 * @throws Exception
	 */
	@Command(name = "revoke")
	public String revoke(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential
			) throws Exception {
		return security.revoke(credential.getAuthHeader(), new MultivaluedHashMap<>());
	}
}
