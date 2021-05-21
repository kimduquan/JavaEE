/**
 * 
 */
package epf.shell;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import picocli.CommandLine;
import picocli.CommandLine.IFactory;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Shell {
	
	/**
	 * 
	 */
	private transient CommandLine commandLine;
	
	/**
	 * 
	 */
	@Inject
	private transient IFactory factory;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		commandLine = new CommandLine(EPFCommand.class, factory);
	}

	/**
	 * @param args
	 * @return
	 */
	@ActivateRequestContext
	public int execute(final String... args) {
		return commandLine.execute(args);
	}
}
