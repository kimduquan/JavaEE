package epf.gateway.util;

import java.util.Comparator;
import jakarta.ws.rs.core.Link;

public class LinkComparator implements Comparator<Link> {

	@Override
	public int compare(final Link one, final Link other) {
		if(one == null && other == null) {
			return 0;
		}
		if(one == null) {
			return -1;
		}
		if(other == null) {
			return 1;
		}
		return one.getTitle().compareTo(other.getTitle());
	}

}
