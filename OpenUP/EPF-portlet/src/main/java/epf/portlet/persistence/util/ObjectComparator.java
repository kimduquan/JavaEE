/**
 * 
 */
package epf.portlet.persistence.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import epf.client.schema.Attribute;

/**
 * @author PC
 *
 */
public class ObjectComparator implements Comparator<Map<String, Object>>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private final Attribute attribute;
	/**
	 * 
	 */
	private boolean ascending;

	/**
	 * @param attribute
	 * @param ascending
	 */
	protected ObjectComparator(final Attribute attribute, final boolean ascending) {
		this.attribute = attribute;
		this.ascending = ascending;
	}

	@Override
	public int compare(final Map<String, Object> o1, final Map<String, Object> o2) {
		final Object value1 = o1.get(attribute.getName());
		final Object value2 = o2.get(attribute.getName());
		int result = 0;
		final int sort = ascending ? 1 : -1;
		if(value1 instanceof Comparable) {
			@SuppressWarnings("unchecked")
			final Comparable<Object> comparable = (Comparable<Object>) value1;
			result = comparable.compareTo(value2);
		}
		return result * sort;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public boolean isAscending() {
		return ascending;
	}

	public void setAscending(final boolean ascending) {
		this.ascending = ascending;
	}

}
