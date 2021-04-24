/**
 * 
 */
package epf.schema.h2;

/**
 * @author PC
 *
 */
public interface Queries {
	/**
	 * 
	 */
	String FT_SEARCH_DATA = "SELECT * FROM FT_SEARCH_DATA(?, ?, ?);";
	
	/**
	 * 
	 */
	String ROLES = "SELECT GRANTEDROLE FROM INFORMATION_SCHEMA.RIGHTS WHERE GRANTEDROLE <> '' AND GRANTEETYPE = 'ROLE' AND GRANTEE IN"
			+ " ( "
			+ "    SELECT GRANTEDROLE FROM INFORMATION_SCHEMA.RIGHTS WHERE GRANTEETYPE = 'USER' AND GRANTEE = ?"
			+ " ) "
			+ "UNION"
			+ "    SELECT GRANTEDROLE FROM INFORMATION_SCHEMA.RIGHTS WHERE GRANTEDROLE <> '' AND GRANTEETYPE = 'USER' AND GRANTEE = ?;";
	
	/**
	 * 
	 */
	String SET_PASSWORD = "ALTER USER %s SET PASSWORD ?;";
}
