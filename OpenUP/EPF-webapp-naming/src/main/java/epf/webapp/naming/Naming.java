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
	interface Internal {
		
		/**
		 *
		 */
		String VIEW_EXTENSION = ".html";
	}
	
	/**
	 * @author PC
	 *
	 */
	interface Messaging {
		
		/**
		 * 
		 */
		String MESSAGING = "epf_messaging";
		
		/**
		 * 
		 */
		String VIEW = "epf_messaging_view";
		
		/**
		 * 
		 */
		String SESSION = "epf_messaging_session";
	}
	
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
	
	/**
	 * 
	 */
	interface Persistence {
		
	}
	
	/**
	 * 
	 */
	interface Query {
		
		/**
		 * 
		 */
		String VIEW = "epf_query_view";
	}
	
	/**
	 * 
	 */
	interface Search {
		
		/**
		 * 
		 */
		String VIEW = "epf_search_view";
	}
}
