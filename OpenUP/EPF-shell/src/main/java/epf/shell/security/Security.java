package epf.shell.security;

import epf.naming.Naming;
import epf.security.schema.Token;
import epf.shell.Function;
import epf.shell.client.RestClientUtil;
import epf.util.logging.Logging;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.core.MultivaluedHashMap;
import org.eclipse.microprofile.config.inject.ConfigProperty;
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
@Logging
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
	@Inject
	transient RestClientUtil restClient;
	
	/**
	 * 
	 */
	@ConfigProperty(name = Naming.Shell.SHELL_URL)
	String shellUrl;

	/**
	 * @param user
	 * @param password
	 * @return
	 * @throws Exception 
	 * @throws ShellException
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
		
		return restClient.newClient(SecurityClient.class).login(user, new String(password), shellUrl);
	}
	
	/**
	 * @param token
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
		return restClient.newClient(SecurityClient.class).logOut(credential.getAuthHeader());
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
		credential.token = token;
		final Token authToken = restClient.newClient(SecurityClient.class).authenticate(credential.getAuthHeader());
		credential.tokenID = authToken.getTokenID();
		identityStore.put(credential);
		return authToken;
	}
	
	/**
	 * @param token
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
		restClient.newClient(SecurityClient.class).update(credential.getAuthHeader(), new String(password));
	}
	
	/**
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@Command(name = "revoke")
	public String revoke(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential
			) throws Exception {
		return restClient.newClient(SecurityClient.class).revoke(credential.getAuthHeader(), new MultivaluedHashMap<>());
	}
}
