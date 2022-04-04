package epf.webapp.naming;

/**
 * @author PC
 *
 */
public interface Naming {
	
	/**
	 * 
	 */
	String VIEW = "epf_view";
	
	/**
	 * 
	 */
	String CONTEXT_ROOT = "/webapp";
	
	/**
	 * 
	 */
	String MESSAGES = "epf_messages";
	
	/**
	 * @author PC
	 *
	 */
	interface Security {
		
		/**
		 * 
		 */
		String LOGIN = "security_login";
		
		/**
		 * 
		 */
		String SESSION = "security_session";
		
		/**
		 * 
		 */
		String CONVERSATION = "security_conversation";
		
		/**
		 * 
		 */
		String PRINCIPAL = "security_principal";
		
		/**
		 * 
		 */
		String AUTH = "security_auth";
	}
}
