/**
 * 
 */
package epf.shell.security;

import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import epf.client.security.Token;
import epf.shell.client.ClientUtil;
import epf.shell.registry.Registry;
import epf.util.Var;
import epf.util.client.Client;
import epf.util.security.PasswordHelper;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * @author PC
 *
 */
@Command(name = "security")
@RequestScoped
public class Security {
	
	/**
	 * 
	 */
	@Inject @Named(Registry.EPF_SECURITY_URL)
	private transient Var<URI> securityUrl;
	
	/**
	 * 
	 */
	@Inject
	private transient ClientUtil clientUtil;
	
	/**
	 * 
	 */
	@Inject @Named(epf.shell.System.OUT)
	private transient PrintWriter out;

	/**
	 * @param user
	 * @param password
	 * @return
	 * @throws ShellException
	 */
	@Command(name = "login")
	public String login(
			@Option(names = {"-u", "--user"}, description = "User name")
			final String user,
			@Option(names = {"-p", "--password"}, description = "Password", interactive = true)
		    final char... password
			) throws Exception {
		try(Client client = clientUtil.newClient(securityUrl.get())){
			final String token = epf.client.security.Security.login(
					client, 
					null, 
					user, 
					PasswordHelper.hash(user, password), 
					new URL(securityUrl.get().toString())
					);
			out.println(token);
			return token;
		}
	}
	
	/**
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@Command(name = "logout")
	public String logout(
			@Option(names = {"-t", "--token"}, description = "Token") 
			final String token
			) throws Exception {
		try(Client client = clientUtil.newClient(securityUrl.get())){
			client.authorization(token);
			final String result = epf.client.security.Security.logOut(client, null);
			out.println(result);
			return result;
		}
	}
	
	/**
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@Command(name = "auth")
	public Token authenticate(
			@Option(names = {"-t", "--token"}, description = "Token") 
			final String token) throws Exception {
		try(Client client = clientUtil.newClient(securityUrl.get())){
			client.authorization(token);
			return epf.client.security.Security.authenticate(client, null);
		}
	}
	
	@Command(name = "set")
	public void update(
			@Option(names = {"-t", "--token"}, description = "Token") 
			final String token,
			@Option(names = {"-p", "--password"}, description = "Password", interactive = true)
		    final char... password
		    ) throws Exception {
		try(Client client = clientUtil.newClient(securityUrl.get())){
			client.authorization(token);
			final Map<String, String> infos = new HashMap<>();
			infos.put("password", new String(password));
			epf.client.security.Security.update(client, null, infos);
		}
	}
}
