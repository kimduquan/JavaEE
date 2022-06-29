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
		String REGISTER = "security_register";
		
		/**
		 * 
		 */
		String FORGOT_PASSWORD = "security_forgot_password";
		
		/**
		 * 
		 */
		String REGISTRATION = "security_registration";
		
		/**
		 * 
		 */
		String RESET_PASSWORD = "security_reset_password";
		
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
		
		/**
		 * 
		 */
		interface Auth {
			
			/**
			 *
			 */
			String CONTEXT_ROOT = "/security-auth";
			
			/**
			 *
			 */
			String AUTH_PAGE = "/webapp/auth.html";
		}
		
		/**
		 * 
		 */
		interface View {
			
			/**
			 *
			 */
			String REGISTRATION_PAGE = "management/registration.html";
			
			/**
			 *
			 */
			String RESET_PASSWORD_PAGE = "management/reset-password.html";
		}
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
	
	/**
	 * 
	 */
	interface View {
		
		/**
		 *
		 */
		String LOGIN_PAGE = "/security/login.html";
		
		/**
		 *
		 */
		String SECURITY_AUTH_PAGE = "/security/auth/auth.html";
	}
}
