package epf.api;

import java.util.Map;

/**
 * 
 */
public class Paths extends Extensible {

	/**
	 *
	 */
	private Map<String, PathItem> pathItems;

	public Map<String, PathItem> getPathItems() {
		return pathItems;
	}

	public void setPathItems(final Map<String, PathItem> pathItems) {
		this.pathItems = pathItems;
	}
}
