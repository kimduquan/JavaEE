package epf.security.util;

import java.util.Comparator;

/**
 * @author PC
 *
 */
public class CredentialComparator implements Comparator<Credential> {

	@Override
	public int compare(final Credential credential1, final Credential credential2) {
		final int compareCaller = credential1.getCaller().compareTo(credential2.getCaller());
		if(compareCaller == 0) {
			final String password1 = String.valueOf(credential1.getPassword().getValue());
			final String password2 = String.valueOf(credential2.getPassword().getValue());
			return password1.compareTo(password2);
		}
		return compareCaller;
	}

}
