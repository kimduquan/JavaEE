/**
 * 
 */
package epf.shell.security;

import java.net.URL;
import epf.client.gateway.GatewayUtil;
import epf.client.security.Token;
import epf.shell.Function;
import epf.shell.client.ClientUtil;
import epf.util.client.Client;
import epf.util.security.PasswordUtil;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * @author PC
 *
 */
@Command(name = "security")
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
	private transient ClientUtil clientUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient IdentityStore identityStore;

	/**
	 * @param user
	 * @param password
	 * @return
	 * @throws Exception 
	 * @throws ShellException
	 */
	@Command(name = "login")
	public String login(
			@Option(names = {"-u", "--user"}, description = "User name")
			final String user,
			@Option(names = {"-p", "--password"}, description = "Password", interactive = true)
		    final char... password
			) throws Exception {
		try(Client client = clientUtil.newClient(GatewayUtil.get("security"))){
			return epf.client.security.Security.login(
					client,
					user, 
					PasswordUtil.hash(user, password), 
					new URL(GatewayUtil.get("shell").toString())
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
		try(Client client = clientUtil.newClient(GatewayUtil.get("security"))){
			client.authorization(credential.getToken());
			return epf.client.security.Security.logOut(client);
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
		try(Client client = clientUtil.newClient(GatewayUtil.get("security"))){
			client.authorization(token);
			authToken = epf.client.security.Security.authenticate(client);
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
		try(Client client = clientUtil.newClient(GatewayUtil.get("security"))){
			client.authorization(credential.getToken());
			epf.client.security.Security.update(client, new String(password));
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
		try(Client client = clientUtil.newClient(GatewayUtil.get("security"))){
			client.authorization(credential.getToken());
			return epf.client.security.Security.revoke(client);
		}
	}
}
