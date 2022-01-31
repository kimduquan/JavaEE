package epf.client.schema.util;

import java.util.Comparator;
import epf.client.schema.Embeddable;

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
