package epf.persistence.security.auth.sql;

/**
 * @author PC
 *
 */
public interface NativeQueries {

	/**
	 * 
	 */
	String GET_CURRENT_ROLES = "SELECT CURRENT_ROLE();";/**
	 * 
	 */
	String SET_PASSWORD = "ALTER USER USER() IDENTIFIED BY ?;";
}