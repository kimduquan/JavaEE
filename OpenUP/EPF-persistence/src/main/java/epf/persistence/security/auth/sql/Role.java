package epf.persistence.security.auth.sql;

/**
 * @author PC
 *
 */
public class Role {

	/**
	 * 
	 */
	private final String name;
	/**
	 * 
	 */
	private final String host;
	
	/**
	 * @param name
	 * @param host
	 */
	public Role(final String name, final String host) {
		this.name = name;
		this.host = host;
	}
	
	public String getName() {
		return name;
	}
	
	public String getHost() {
		return host;
	}
}
