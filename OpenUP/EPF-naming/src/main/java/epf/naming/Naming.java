package epf.naming;

/**
 * @author PC
 *
 */
public interface Naming {
	
	/**
	 * 
	 */
	String EPF = "EPF";
	
	/**
	 * 
	 */
	String BATCH = "batch";
	
	/**
	 * @author PC
	 *
	 */
	interface Batch {
		
		/**
		 * 
		 */
		String BATCH_API = "api/batch";
		
		/**
		 * 
		 */
		String BATCH_API_URL = "epf.batch.api.url";
	}
	
	/**
	 * 
	 */
	String CACHE = "cache";
	
	/**
	 * @author PC
	 *
	 */
	interface Cache {

		/**
		 * 
		 */
		String CACHE_URL = "epf.cache.url";
		
	}
	
	/**
	 * 
	 */
	String CLIENT = "client";
	
	/**
	 * @author PC
	 *
	 */
	interface Client {
		/**
		 * 
		 */
		String CLIENT_CONFIG = "epf.client.config";
		
		/**
		 * 
		 */
		String SSL_KEY_STORE = "epf.client.net.ssl.keyStore";
		
		/**
		 * 
		 */
		String SSL_KEY_STORE_TYPE = "epf.client.net.ssl.keyStoreType";
		
		/**
		 * 
		 */
		String SSL_KEY_STORE_PASSWORD = "epf.client.net.ssl.keyStorePassword";
		
		/**
		 * 
		 */
		String SSL_TRUST_STORE = "epf.client.net.ssl.trustStore";
		
		/**
		 * 
		 */
		String SSL_TRUST_STORE_TYPE = "epf.client.net.ssl.trustStoreType";
		
		/**
		 * 
		 */
		String SSL_TRUST_STORE_PASSWORD = "epf.client.net.ssl.trustStorePassword";
	}
	
	/**
	 * 
	 */
	String CONFIG = "config";
	
	/**
	 * @author PC
	 *
	 */
	interface Config {
		
		/**
		 * 
		 */
		String CONFIG_URL = "epf.config.url";
		
	}
	
	/**
	 * 
	 */
	String FILE = "file";
	
	/**
	 * @author PC
	 *
	 */
	interface File {
	    
	    /**
	     * 
	     */
	    String FILE_URL = "epf.file.url";
		
	    /**
	     * 
	     */
	    String ROOT = "epf.file.root";
	}
	
	/**
	 * 
	 */
	String GATEWAY = "gateway";
	
	/**
	 * @author PC
	 *
	 */
	interface Gateway {
		
	    /**
	     * 
	     */
	    String GATEWAY_URL = "epf.gateway.url";
	    
	    /**
	     * 
	     */
	    String MESSAGING_URL = "epf.gateway.messaging.url";
	    
	    /**
	     * @author PC
	     *
	     */
	    interface Headers {
	    	
	    	/**
	    	 * 
	    	 */
	    	String X_FORWARDED_PORT = "X-Forwarded-Port";
	    	
	    	/**
	    	 * 
	    	 */
	    	String X_FORWARDED_PROTO = "X-Forwarded-Proto";
	    	
	    	/**
	    	 * 
	    	 */
	    	String X_FORWARDED_HOST = "X-Forwarded-Host";
	    }
	}
	
	/**
	 * 
	 */
	String HEALTH = "health";
	
	/**
	 * @author PC
	 *
	 */
	interface Health {
		
		/**
		 * 
		 */
		String HEALTH_URL = "epf.health.url";
	}
	
	/**
	 * 
	 */
	String IMAGE = "image";
	
	/**
	 * @author PC
	 *
	 */
	interface Image {

		/**
		 * 
		 */
		String IMAGE_URL = "epf.image.url";
		
	}
	
	/**
	 * @author PC
	 *
	 */
	interface Lang {

		/**
		 * 
		 */
		String LANG_URL = "epf.lang.url";
	}
	
	/**
	 * 
	 */
	String MANAGEMENT = "management";
	
	/**
	 * @author PC
	 *
	 */
	interface Management {

		/**
		 * 
		 */
		String MANAGEMENT_URL = "epf.management.url";
		
		/**
		 * 
		 */
		String TENANT = "tenant";
	}
	
	/**
	 * 
	 */
	String MESSAGING = "messaging";
	
	/**
	 * @author PC
	 *
	 */
	interface Messaging {
		
	    /**
	     * 
	     */
	    String MESSAGING_URL = "epf.messaging.url";
		
	}
	
	/**
	 * 
	 */
	String NET = "net";
	
	/**
	 * @author PC
	 *
	 */
	interface Net {
		
		/**
		 * 
		 */
		String NET_URL = "epf.net.url";
		
		/**
		 * 
		 */
		String HTTP_PORT = "epf.net.http.port";
	}
	
	/**
	 * 
	 */
	String PERSISTENCE = "persistence";
	
	/**
	 * @author PC
	 *
	 */
	interface Persistence {
		
		/**
		 *
		 */
		String QUERY = "persistence-query";
		
		/**
		 * 
		 */
		String PERSISTENCE = "epf.persistence";

		/**
		 * 
		 */
		String PERSISTENCE_URL = "epf.persistence.url";
		
		/**
		 * 
		 */
		String PERSISTENCE_SECURITY = "epf.persistence.security";
		
		/**
		 * 
		 */
		String PERSISTENCE_SECURITY_URL = "epf.persistence.security.url";
		
		/**
		 * 
		 */
		String PERSISTENCE_QUERY_FIRST_RESULT_DEFAULT = "epf.persistence.query.firstResult.default";
		
		/**
		 * 
		 */
		String PERSISTENCE_QUERY_MAX_RESULTS_DEFAULT = "epf.persistence.query.maxResults.default";
		
		/**
		 * 
		 */
		String PERSISTENCE_ENTITY_LISTENERS = "persistence";
		
		/**
		 * @author PC
		 *
		 */
		interface JDBC {

			/**
			 * 
			 */
			String JDBC_USER = "javax.persistence.jdbc.user";
			/**
			 * 
			 */
			String JDBC_PASSWORD = "javax.persistence.jdbc.password";
			/**
			 * 
			 */
			String JDBC_URL = "javax.persistence.jdbc.url";
		}
		
		/**
		 * @author PC
		 *
		 */
		interface Internal {
			/**
			 * 
			 */
			String SCHEMA = "epf.persistence.schema";
		}
	}
	
	/**
	 * 
	 */
	String PLANNING = "planning";
	
	/**
	 * @author PC
	 *
	 */
	interface Planning {

		/**
		 * 
		 */
		String PLANNING_URL = "epf.planning.url";
		
	}
	
	/**
	 *
	 */
	String QUERY = "query";
	
	/**
	 * 
	 */
	interface Query {
		
		/**
		 *
		 */
		String QUERY_URL = "epf.query.url";
		
		/**
		 *
		 */
		String ENTITY_COUNT = "entity-count";
	}
	
	/**
	 * 
	 */
	String REGISTRY = "registry";
	
	/**
	 * @author PC
	 *
	 */
	interface Registry {
		
	    /**
	     * 
	     */
	    String REGISTRY_URL = "epf.registry.url";
	    
	    /**
	     * @author PC
	     *
	     */
	    interface Filter {
	    	
		    /**
		     * 
		     */
		    String ABSOLUTE = "absolute";
		    
		    /**
		     * 
		     */
		    String OPAQUE = "opaque";
		    
		    /**
		     * 
		     */
		    String AUTHORITY = "authority";
		    
		    /**
		     * 
		     */
		    String FRAGMENT = "fragment";
		    
		    /**
		     * 
		     */
		    String HOST = "host";
		    
		    /**
		     * 
		     */
		    String PATH = "path";
		    
		    /**
		     * 
		     */
		    String PORT = "port";
		    
		    /**
		     * 
		     */
		    String QUERY = "query";
		    
		    /**
		     * 
		     */
		    String SCHEME = "scheme";
		    
		    /**
		     * 
		     */
		    String USER_INFO = "user-info";
	    }
	}
	
	/**
	 * 
	 */
	String RULES = "rules";
	
	/**
	 * @author PC
	 *
	 */
	interface Rules {
		
		/**
		 * 
		 */
		String RULES_URL = "epf.rules.url";
		
		/**
		 * 
		 */
		String RULES_ADMIN = "rules/admin";
		
	}
	
	/**
	 * 
	 */
	String SCHEDULE = "schedule";
	
	/**
	 * @author PC
	 *
	 */
	interface Schedule {
		
		/**
		 * 
		 */
		String SCHEDULE_URL = "epf.schedule.url";
		
		/**
		 * 
		 */
		String EXECUTOR_SERVICE = "java:comp/DefaultManagedScheduledExecutorService";
	}
	
	/**
	 * 
	 */
	String SCHEMA = "schema";
	
	/**
	 * @author PC
	 *
	 */
	interface Schema {

		/**
		 * 
		 */
		String SCHEMA_URL = "epf.schema.url";
		
	}
	
	/**
	 * 
	 */
	String SCRIPT = "script";
	
	/**
	 * @author PC
	 *
	 */
	interface Script {
		
		/**
		 * 
		 */
		String SCRIPT_URL = "epf.script.url";
		
	}
	
	/**
	 * 
	 */
	String SEARCH = "search";
	
	/**
	 * @author PC
	 *
	 */
	interface Search {
		
	}
	
	/**
	 * 
	 */
	String SECURITY = "security";
	
	/**
	 * @author PC
	 *
	 */
	interface Security {
		
		/**
	     * 
	     */
	    String SECURITY_URL = "epf.security.url";
	    
	    /**
	     * 
	     */
	    String DEFAULT_ROLE = "Any_Role";
	    
	    /**
	     * @author PC
	     *
	     */
	    interface Internal {
	    	
	    }
	    
	    /**
	     * @author PC
	     *
	     */
	    interface JWT {
	    	
	        /**
	         * 
	         */
	        String ISSUE_KEY = "epf.security.jwt.issue.key";
	        
	        /**
	         * 
	         */
	        String VERIFY_KEY = "epf.security.jwt.verify.publickey";
	        
	        /**
	         * 
	         */
	        String EXPIRE_DURATION = "epf.security.jwt.exp.duration";
	        
	        /**
	         * 
	         */
	        String TOKEN_CLAIM = "token";
	    }
	    
	    /**
	     * @author PC
	     *
	     */
	    interface OTP {
	    	/**
	         * 
	         */
	        String EXPIRE_DURATION = "epf.security.otp.exp.duration";
	    }
	    
	    /**
	     * @author PC
	     *
	     */
	    interface Auth {
	    	
	    	/**
	    	 * 
	    	 */
	    	String AUTH_URL = "epf.security.auth.url";
	    	
	    	/**
	    	 *
	    	 */
	    	String GOOGLE = "google";
	    	
	    	/**
	    	 * 
	    	 */
	    	String GOOGLE_PROVIDER = "epf.security.auth.openid.discovery.google";
	    	
	    	/**
	    	 * 
	    	 */
	    	String GOOGLE_CLIENT_ID = "epf.security.auth.openid.client.id.google";
	    	
	    	/**
	    	 * 
	    	 */
	    	String GOOGLE_CLIENT_SECRET = "epf.security.auth.openid.client.secret.google";
	    	
	    	/**
	    	 *
	    	 */
	    	String FACEBOOK = "facebook";
	    	
	    	/**
	    	 * 
	    	 */
	    	String FACEBOOK_PROVIDER = "epf.security.auth.openid.discovery.facebook";
	    	
	    	/**
	    	 * 
	    	 */
	    	String FACEBOOK_CLIENT_ID = "epf.security.auth.openid.client.id.facebook";
	    	
	    	/**
	    	 * 
	    	 */
	    	String FACEBOOK_CLIENT_SECRET = "epf.security.auth.openid.client.secret.facebook";
	    }
	}
	
	/**
	 * 
	 */
	String SHELL = "shell";
	
	/**
	 * @author PC
	 *
	 */
	interface Shell {
		
		/**
		 * 
		 */
		String SHELL_URL = "epf.shell.url";

		/**
		 * 
		 */
		String SHELL_PATH = "epf.shell.path";
		
		/**
		 * 
		 */
		String SHELL_COMMAND = "epf.shell.command";
		
		/**
		 * 
		 */
		String SHELL_RUNNER = "epf.shell.runner";
	}
	
	/**
	 *
	 */
	String TRANSACTION = "transaction";
	
	/**
	 * 
	 */
	interface Transaction {
		
		/**
	     * 
	     */
	    String TRANSACTION_URL = "epf.transaction.url";
	}
	
	/**
	 * 
	 */
	String UTILITY = "utility";
	
	/**
	 * @author PC
	 *
	 */
	interface Utility {
		
	}
	
	String WEB_APP = "webapp";
	
	/**
	 * @author PC
	 *
	 */
	interface WebApp {
		
		/**
	     * 
	     */
	    String WEB_APP_URL = "epf.webapp.url";
	    
	    /**
	     * 
	     */
	    String SECURITY_WEB_APP_URL = "epf.security.webapp.url";
	    
	    /**
	     * @author PC
	     *
	     */
	    interface Messaging {
	    	
	    	/**
	    	 * 
	    	 */
	    	String MESSAGES_LIMIT = "epf.webapp.messaging.messages.limit";
	    }
	    
	    /**
	     * @author PC
	     *
	     */
	    interface Security {
	    	
	    	/**
	    	 * 
	    	 */
	    	String LOGIN = "/login";
	    }
	}
}
