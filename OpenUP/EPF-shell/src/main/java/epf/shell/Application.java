/**
 * 
 */
package epf.shell;

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
		try(SeContainer container = SeContainerInitializer.newInstance().initialize()){
			final Shell shell = container.select(Shell.class).get();
			shell.execute(args);
		}
	}

}
