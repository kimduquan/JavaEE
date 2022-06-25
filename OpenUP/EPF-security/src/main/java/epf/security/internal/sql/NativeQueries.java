package epf.security.internal.sql;

/**
 * @author PC
 *
 */
public interface NativeQueries {

	/**
	 * 
	 */
	String GET_CURRENT_ROLES = "SELECT GRANTEDROLE FROM INFORMATION_SCHEMA.RIGHTS WHERE GRANTEE = UPPER(?) AND GRANTEETYPE = 'USER' AND GRANTEDROLE IS NOT NULL;";
	
	/**
	 * 
	 */
	String SET_PASSWORD = "SET PASSWORD ?;";
	
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
