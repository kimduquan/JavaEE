/**
 * 
 */
package epf.shell.security;

import java.net.URL;
import epf.client.gateway.GatewayUtil;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.security.schema.Token;
import epf.shell.Function;
import epf.shell.client.ClientUtil;
import epf.util.logging.Logging;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
	transient ClientUtil clientUtil;
	
	/**
	 * 
	 */
	@Inject
	transient IdentityStore identityStore;
	
	/**
	 * 
	 */
	@ConfigProperty(name = Naming.Gateway.GATEWAY_URL)
	String gatewayUrl;

	/**
	 * @param user
	 * @param password
	 * @return
	 * @throws Exception 
	 * @throws ShellException
	 */
	@Command(name = "login")
	@Valid
	public String login(
			@Option(names = {"-u", "--user"}, required = true, description = "User name")
			@NotBlank
			final String user,
			@Option(names = {"-p", "--password"}, required = true, description = "Password", interactive = true)
		    @NotEmpty
			final char... password
			) throws Exception {
		try(Client client = clientUtil.newClient(gatewayUrl, Naming.SECURITY)){
			return epf.security.client.Security.login(
					client,
					user, 
					new String(password), 
					new URL(GatewayUtil.get(gatewayUrl, Naming.SHELL).toString())
					);
		}
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
		try(Client client = clientUtil.newClient(gatewayUrl, Naming.SECURITY)){
			client.authorization(credential.getToken());
			return epf.security.client.Security.logOut(client);
		}
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
		Token authToken = null;
		try(Client client = clientUtil.newClient(gatewayUrl, Naming.SECURITY)){
			client.authorization(token);
			authToken = epf.security.client.Security.authenticate(client);
		}
		final Credential credential = new Credential();
		credential.token = token;
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
		try(Client client = clientUtil.newClient(gatewayUrl, Naming.SECURITY)){
			client.authorization(credential.getToken());
			epf.security.client.Security.update(client, new String(password));
		}
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
		try(Client client = clientUtil.newClient(gatewayUrl, Naming.SECURITY)){
			client.authorization(credential.getToken());
			return epf.security.client.Security.revoke(client);
		}
	}
}
