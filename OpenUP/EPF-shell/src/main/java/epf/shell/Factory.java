package epf.shell;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import picocli.CommandLine;
import picocli.CommandLine.IFactory;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Factory implements IFactory {
	
	/**
	 * 
	 */
	private final transient IFactory defaultFactory;
	
	/**
	 * 
	 */
	@Inject
	private transient Instance<Object> instance;
	
	/**
	 * 
	 */
	public Factory() {
		defaultFactory = CommandLine.defaultFactory();
	}

	@Override
	public <K> K create(final Class<K> cls) throws Exception {
		K result = null;
		final Instance<K> object = instance.select(cls);
		if(object.isResolvable()) {
			result = object.get();
		}
		if(result == null) {
			result = defaultFactory.create(cls);
		}
		return result;
	}

}
