package epf.persistence.schema.internal;

import java.util.Comparator;
import epf.persistence.schema.Attribute;

public class AttributeComparator implements Comparator<Attribute> {

	@Override
	public int compare(final Attribute attr1, final Attribute attr2) {
		int result = Integer.compare(attr1.getName().length(), attr2.getName().length());
		if(result == 0) {
			result = attr1.getName().compareTo(attr2.getName());
		}
		return result;
	}

}
