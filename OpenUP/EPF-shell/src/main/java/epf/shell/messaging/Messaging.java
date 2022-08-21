package epf.shell.messaging;

import javax.enterprise.context.RequestScoped;
import epf.naming.Naming;
import epf.shell.Function;
import epf.shell.security.CallerPrincipal;
import epf.shell.security.Credential;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;

/**
 * @author PC
 *
 */
@Command(name = Naming.MESSAGING)
@RequestScoped
@Function
public class Messaging {
	
	/**
	 * @param credential
	 * @throws Exception
	 */
	@Command(name = "connect")
	public void connectToServer(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential) throws Exception {
		
	}
}
