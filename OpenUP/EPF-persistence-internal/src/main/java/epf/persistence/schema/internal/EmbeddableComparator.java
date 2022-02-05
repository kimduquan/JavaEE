package epf.persistence.schema.internal;

import java.util.Comparator;
import epf.persistence.schema.client.Embeddable;

/**
 * @author PC
 *
 */
public class EmbeddableComparator implements Comparator<Embeddable> {

	@Override
	public int compare(final Embeddable o1, final Embeddable o2) {
		return o1.getType().compareTo(o2.getType());
	}

}
