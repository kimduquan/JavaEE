package epf.persistence.schema.internal;

import java.util.Comparator;
import epf.persistence.schema.Entity;

public class EntityComparator implements Comparator<Entity> {

	@Override
	public int compare(final Entity o1, final Entity o2) {
		return o1.getType().compareTo(o2.getType());
	}

}
