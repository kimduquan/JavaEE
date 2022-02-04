package epf.security.internal.sql;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import epf.security.internal.Role;

/**
 * @author PC
 *
 */
public interface RoleUtil {
	
	/**
	 * 
	 */
	String ROLE_FORMAT = "'%s'@'%s'";
	/**
	 * 
	 */
	String ROLES_SEPARATOR = "`,`";
	/**
	 * 
	 */
	String ROLE_DELIMITER = "`@`";
	
	/**
	 * @param string
	 * @return
	 */
	static Stream<Role> parse(final String string) {
		Objects.requireNonNull(string, "String");
		final String[] roles = string.substring(1, string.length() - 1).split(ROLES_SEPARATOR);
		return Arrays.asList(roles).stream().map(RoleUtil::valueOf);
	}
	
	/**
	 * @param string
	 * @return
	 */
	static Role valueOf(final String string) {
		Objects.requireNonNull(string, "String");
		final String[] fragments = string.split(ROLE_DELIMITER);
		return new Role(fragments[0], fragments[1]);
	}
}
