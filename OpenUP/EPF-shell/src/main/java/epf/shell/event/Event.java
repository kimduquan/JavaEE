package epf.shell.event;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.naming.Naming;
import epf.shell.Function;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;

/**
 * @author PC
 *
 */
@Command(name = Naming.EVENT)
@RequestScoped
@Function
public class Event {
	
	/**
	 * 
	 */
	@Inject
	@RestClient
	transient EventClient event;
}
