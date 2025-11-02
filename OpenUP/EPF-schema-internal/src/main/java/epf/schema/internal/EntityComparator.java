package epf.schema.internal;

import java.util.Comparator;
import epf.persistence.schema.EntityType;

public class EntityComparator implements Comparator<EntityType> {

	@Override
	public int compare(final EntityType o1, final EntityType o2) {
		return o1.getJavaType().compareTo(o2.getJavaType());
	}

}
