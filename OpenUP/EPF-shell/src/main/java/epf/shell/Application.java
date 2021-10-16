/**
 * 
 */
package epf.shell;

import epf.util.logging.LogManager;
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
		LogManager.config(Application.class);
		try(SeContainer container = SeContainerInitializer.newInstance().initialize()){
			final Shell shell = container.select(Shell.class).get();
			shell.execute(args);
		}
	}
}
