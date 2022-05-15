package epf.transaction.api.callback;

import java.util.Map;
import epf.transaction.api.Extensible;
import epf.transaction.api.PathItem;

/**
 * 
 */
public class Callback extends Extensible {

	/**
	 *
	 */
	private String ref;
	
	/**
	 *
	 */
	private Map<String, PathItem> pathItems;

	public String getRef() {
		return ref;
	}

	public void setRef(final String ref) {
		this.ref = ref;
	}

	public Map<String, PathItem> getPathItems() {
		return pathItems;
	}

	public void setPathItems(final Map<String, PathItem> pathItems) {
		this.pathItems = pathItems;
	}
}