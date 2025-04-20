package epf.security.internal;

public interface NativeQueries {

	String GET_CURRENT_ROLES = "SELECT ROLE_NAME FROM INFORMATION_SCHEMA.ROLES;";
	
	String SET_PASSWORD = "ALTER USER \"%s\" SET PASSWORD ?;";
}
