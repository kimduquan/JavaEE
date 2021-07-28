/**
 * 
 */
package epf.shell;

import java.io.PrintWriter;
import java.nio.file.Path;
import epf.shell.file.PathTypeConverter;
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
	private transient final PathTypeConverter pathConverter = new PathTypeConverter();
	
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
		commandLine.registerConverter(Path.class, pathConverter);
		commandLine.setTrimQuotes(true);
	}
	
	/**
	 * @return
	 */
	@Produces @Named(SYSTEM.OUT)
	public PrintWriter getOutput() {
		return commandLine.getOut();
	}
	
	/**
	 * @return
	 */
	@Produces @Named(SYSTEM.ERR)
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
