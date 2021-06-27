/**
 * 
 */
package epf.client.schema.util;

import java.util.Comparator;
import epf.client.schema.Attribute;

/**
 * @author PC
 *
 */
public class AttributeComparator implements Comparator<Attribute> {

	@Override
	public int compare(final Attribute o1, final Attribute o2) {
		int result = Integer.compare(o1.getName().length(), o2.getName().length());
		if(result == 0) {
			result = o1.getName().compareTo(o2.getName());
		}
		return result;
	}

}
