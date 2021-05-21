/**
 * 
 */
package epf.shell;

import java.io.PrintWriter;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;
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
	 * @return
	 */
	@Produces @Named(System.OUTPUT)
	public PrintWriter getOutput() {
		return commandLine.getOut();
	}
	
	/**
	 * @return
	 */
	@Produces @Named(System.ERROR)
	public PrintWriter getError() {
		return  commandLine.getErr();
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
