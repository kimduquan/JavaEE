package epf.security.management.internal;

/**
 * @author PC
 *
 */
public interface NativeQueries {
	
	/**
	 *
	 */
	String CREATE_USER = "CREATE USER \"%s\" PASSWORD ?;";
	
	/**
	 *
	 */
	String SET_ROLE = "GRANT %s TO \"%s\"";
	
	/**
	 *
	 */
	String CHECK_USER = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.USERS WHERE USER_NAME = UPPER(?);";
	
	/**
	 * 
	 */
	String SET_USER__PASSWORD = "ALTER USER \"%s\" SET PASSWORD ?;";
}
