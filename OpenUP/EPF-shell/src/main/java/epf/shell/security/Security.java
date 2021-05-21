/**
 * 
 */
package epf.shell.security;

import epf.util.security.PasswordHelper;
import jakarta.enterprise.context.RequestScoped;
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
		return PasswordHelper.hash(user, password);
	}
}
