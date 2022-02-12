package epf.security.util;

import java.util.Comparator;

/**
 * @author PC
 *
 */
public class CredentialComparator implements Comparator<Credential> {

	@Override
	public int compare(final Credential o1, final Credential o2) {
		return o1.getCaller().compareTo(o2.getCaller()) * o2.getPasswordAsString().compareTo(o2.getPasswordAsString());
	}

}
