package epf.security.internal.sql;

/**
 * @author PC
 *
 */
public interface NativeQueries {

	/**
	 * 
	 */
	String GET_CURRENT_ROLES = "SELECT GRANTEDROLE FROM INFORMATION_SCHEMA.RIGHTS WHERE GRANTEE = CURRENT_USER() AND GRANTEETYPE = 'USER';";
	/**
	 * 
	 */
	String SET_PASSWORD = "ALTER USER CURRENT_USER() SET PASSWORD ?;";
}
