/**
 * 
 */
package epf.portlet.persistence.util;

import java.io.Serializable;
import java.util.Map;

/**
 * @author PC
 *
 */
public class AttributeFilter implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private final AttributeComparator comparator;
	/**
	 * 
	 */
	private boolean include;
	
	/**
	 * @param comparator
	 * @param include
	 */
	protected AttributeFilter(final AttributeComparator comparator, final boolean include) {
		this.comparator = comparator;
		this.include = include;
	}

	/**
	 * @param data
	 * @return
	 */
	public boolean filter(final Map<String, Object> filter, final Map<String, Object> data) {
		boolean result = comparator.compare(filter, data) == 0;
		return result && include;
	}

	public boolean isInclude() {
		return include;
	}

	public void setInclude(final boolean include) {
		this.include = include;
	}

	public AttributeComparator getComparator() {
		return comparator;
	}
}
