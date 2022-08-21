package epf.shell;

import java.io.PrintWriter;
import java.nio.file.Path;
import epf.shell.file.PathTypeConverter;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import picocli.CommandLine;
import picocli.CommandLine.IFactory;

/**
 * @author PC
 *
 */
@QuarkusMain
@ApplicationScoped
public class Shell implements QuarkusApplication {
	
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
	transient IFactory factory;
	
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

	@ActivateRequestContext
	@Override
	public int run(String... args) throws Exception {
		commandLine = new CommandLine(EPFCommand.class, factory);
		commandLine.registerConverter(Path.class, pathConverter);
		commandLine.setTrimQuotes(true);
		return commandLine.execute(args);
	}
}
