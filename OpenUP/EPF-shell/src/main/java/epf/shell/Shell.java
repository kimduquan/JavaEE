package epf.shell;

import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Path;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import epf.shell.util.file.PathTypeConverter;
import epf.shell.util.jdbc.URLTypeConverter;
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
	private transient final URLTypeConverter urlConverter = new URLTypeConverter();
	
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
		commandLine = new CommandLine(EPFFunction.class, factory);
		commandLine.registerConverter(Path.class, pathConverter);
		commandLine.registerConverter(URL.class, urlConverter);
		commandLine.setTrimQuotes(true);
		return commandLine.execute(args);
	}
}
