package epf.transaction.api.security;

import java.util.List;
import java.util.Map;

/**
 * 
 */
public class SecurityRequirement {

	/**
	 *
	 */
	private Map<String, List<String>> schemes;

	public Map<String, List<String>> getSchemes() {
		return schemes;
	}

	public void setSchemes(final Map<String, List<String>> schemes) {
		this.schemes = schemes;
	}
}
