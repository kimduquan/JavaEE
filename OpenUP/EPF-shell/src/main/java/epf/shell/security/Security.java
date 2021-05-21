/**
 * 
 */
package epf.shell.security;

import java.net.URI;
import java.net.URL;
import epf.shell.ClientUtil;
import epf.shell.Registry;
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
	
	@Inject @Named(Registry.EPF_SECURITY_URL)
	private transient Var<URI> securityUrl;
	
	@Inject
	private transient ClientUtil clientUtil;

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
			@Option(names = {"-p", "--password"}, description = "Passphrase", interactive = true)
		    final char... password
			) throws Exception {
		try(Client client = clientUtil.newClient(securityUrl.get())){
			return epf.client.security.Security.login(
					client, 
					null, 
					user, 
					PasswordHelper.hash(user, password), 
					new URL(securityUrl.get().toString())
					);
		}
	}
}
