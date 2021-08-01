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
	String SET_PASSWORD = "ALTER USER %s SET PASSWORD ?;";
}
