package epf.persistence.security.internal.mysql;

import java.util.Objects;

/**
 * @author PC
 *
 */
public class Role {
	
	/**
	 * 
	 */
	private static final String NAME_FORMAT = "'%s'@'%s'";

	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String host;
	
	/**
	 * @param fullName
	 */
	public Role(final String fullName) {
		Objects.requireNonNull(fullName, "String");
		final String[] data = fullName.split("`@`");
		name = data[0].substring(1);
		host = data[1].substring(0, data[1].length() - 1);
	}
	
	@Override
	public String toString() {
		return String.format(NAME_FORMAT, name, host);
	}
	
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public String getHost() {
		return host;
	}
	public void setHost(final String host) {
		this.host = host;
	}
}
