package epf.function;

import org.eclipse.jgit.api.Git;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * 
 */
public class Activator implements BundleActivator {

	@Override
	public void start(final BundleContext context) throws Exception {
		
	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		Git.shutdown();
	}
}