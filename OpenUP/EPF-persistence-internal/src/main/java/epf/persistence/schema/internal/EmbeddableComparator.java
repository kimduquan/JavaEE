package epf.persistence.schema.internal;

import java.util.Comparator;
import epf.persistence.schema.EmbeddableType;

public class EmbeddableComparator implements Comparator<EmbeddableType> {

	@Override
	public int compare(final EmbeddableType o1, final EmbeddableType o2) {
		return o1.getJavaType().compareTo(o2.getJavaType());
	}

}
