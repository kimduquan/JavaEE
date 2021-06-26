/**
 * 
 */
package epf.portlet.persistence;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import epf.client.schema.Attribute;
import epf.portlet.persistence.util.AttributeComparator;
import epf.portlet.persistence.util.AttributeFilter;

/**
 * @author PC
 *
 */
public interface ObjectCollector {

	/**
	 * @param data
	 * @return
	 */
	List<Map<String, Object>> collect(final Stream<Map<String, Object>> data);
	
	/**
	 * @param attribute
	 * @param ascending
	 * @return
	 */
	AttributeComparator sort(final Attribute attribute, final boolean ascending);
	/**
	 * @param comparator
	 * @param include
	 * @return
	 */
	AttributeFilter filter(final Attribute attribute, final boolean include);
}
