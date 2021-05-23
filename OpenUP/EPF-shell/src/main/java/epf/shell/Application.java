/**
 * 
 */
package epf.shell;

import java.io.PrintStream;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

/**
 * @author PC
 *
 */
public final class Application {
	
	/**
	 * 
	 */
	private Application() {
		super();
	}

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final PrintStream sysErr = SYSTEM.disableErr();
		try(SeContainer container = SeContainerInitializer.newInstance().initialize()){
			SYSTEM.enableErr(sysErr);
			final Shell shell = container.select(Shell.class).get();
			shell.execute(args);
		}
	}
}
