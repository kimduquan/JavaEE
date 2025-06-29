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
		String SSL_KEY_ALIAS = "epf.client.net.ssl.keyAlias";
		
		/**
		 * 
		 */
		String SSL_KEY_PASSWORD = "epf.client.net.ssl.keyPassword";
		
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
		
		/**
		 * @author PC
		 *
		 */
		interface Link {
			
			/**
			 * 
			 */
			String WAIT = "wait";
			
			/**
			 * 
			 */
			String SELF = "self";
		}
	}
	
	/**
	 * 
	 */
	String CONCURRENT = "concurrent";
	
	/**
	 * 
	 */
	interface Concurrent {
		
		/**
		 * 
		 */
		String CONCURRENT_URL = "epf.concurrent.url";
		
		/**
		 * 
		 */
		String SYNCHRONIZED = "synchronized";
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
	String EVENT = "event";
	
	/**
	 * 
	 */
	interface Event {
		
		/**
		 * 
		 */
		String EVENT_URL = "epf.config.url";
		
		/**
		 * 
		 */
		String EPF_EVENT_OBSERVES = "epf-event-observes";
		
		/**
		 * 
		 */
		interface Internal {
			
			/**
			 * 
			 */
			String EPF_EVENT_CACHE = "epf-event-cache";
		}
		
		/**
		 * 
		 */
		interface Schema {
			
			/**
			 * 
			 */
			String ID = "id";
			
			/**
			 * 
			 */
			String DATA = "data";
			
			/**
			 * 
			 */
			String DATA_CONTENT_TYPE = "dataContentType";
			
			/**
			 * 
			 */
			String DATA_SCHEMA = "dataSchema";
			
			/**
			 * 
			 */
			String SOURCE = "source";
			
			/**
			 * 
			 */
			String SPEC_VERSION = "specVersion";
			
			/**
			 * 
			 */
			String SUBJECT = "subject";
			
			/**
			 * 
			 */
			String TIME = "time";
			
			/**
			 * 
			 */
			String TYPE = "type";
		}
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
	    
	    /**
	     * 
	     */
	    interface Cache {
	    	
	    	/**
		     * 
		     */
		    String ROOT = "epf.file.cache.root";
	    }
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
	     *
	     */
	    String HEALTH_URL = "epf.gateway.health.url";
	    
	    /**
	     * @author PC
	     *
	     */
	    interface Headers {
	    	
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
	 * 
	 */
	String LANG = "lang";
	
	/**
	 * 
	 */
	interface Lang {
		
		/**
		 * 
		 */
		String LANG_URL = "epf.lang.url";
		
		/**
		 * 
		 */
		interface Internal {
			
			/**
			 * 
			 */
			String GRAPH = "graph";
			
			/**
			 * 
			 */
			String OLLAMA = "ollama";
			
			/**
			 * 
			 */
			String MODEL = "model";
			
			/**
			 * 
			 */
			String LANGUAGE_MODEL = "epf.lang.model";
			
			/**
			 * 
			 */
			String EMBED_LANGUAGE_MODEL = "epf.lang.embed.model";
			
			/**
			 * 
			 */
			String CACHE_PATH = "epf.lang.cache.path";
			
			/**
			 * 
			 */
			String PERSISTENCE_PATH = "epf.lang.persistence.path";
			
			/**
			 * 
			 */
			interface Graph {
				
				/**
				 * 
				 */
				String GRAPH_URL = "epf.lang.graph.url";
				
				/**
				 * 
				 */
				String GRAPH_DATABASE = "epf.lang.graph.database";
				
				/**
				 * 
				 */
				String GRAPH_USERNAME = "epf.lang.graph.username";
				
				/**
				 * 
				 */
				String GRAPH_PASSWORD = "epf.lang.graph.password";
				
				/**
				 * 
				 */
				interface Property {
					
					/**
					 * 
					 */
					String TEXT = "epf.lang.graph.property.text";
					
					/**
					 * 
					 */
					String ID = "epf.lang.graph.property.id";
				}
			}
			
			/**
			 * 
			 */
			interface RDF {
				/**
				 * 
				 */
				String SERVER = "epf.lang.rdf.server";
			}
		}
	}
	
	/**
	 *
	 */
	String MAIL = "mail";
	
	/**
	 * 
	 */
	interface Mail {
		
		/**
		 *
		 */
		String MAIL_URL = "epf.mail.url";
		
		/**
		 *
		 */
		String MAIL_FROM = "epf.mail.from";
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
		
		/**
		 *
		 */
		String MANAGEMENT_TENANT = "epf.management.tenant";
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
		
	    /**
	     *
	     */
	    String DEFAULT_PATH = "_";
	    
	    /**
	     * 
	     */
	    interface Internal {
	    	
	    	/**
	    	 * 
	    	 */
	    	String EPF_MESSAGING_TEXT_IN = "epf-messaging-text-in";
	    	
	    	/**
	    	 * 
	    	 */
	    	String EPF_MESSAGING_TEXT_OUT = "epf-messaging-text-out";
	    	
	    	/**
	    	 * 
	    	 */
	    	String EPF_MESSAGING_BYTES_IN = "epf-messaging-bytes-in";
	    	
	    	/**
	    	 * 
	    	 */
	    	String EPF_MESSAGING_BYTES_OUT = "epf-messaging-bytes-out";
	    }
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

		/**
		 * 
		 */
		String URL = "url";
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
		String ENTITY_LISTENERS = "persistence";
		
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
		}
		
		/**
		 * 
		 */
		interface Client {
			
			/**
			 * 
			 */
			String SCHEMA = "schema";
			
			/**
			 * 
			 */
			String ENTITY = "entity";
			
			/**
			 * 
			 */
			String ID = "id";
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
		String FIRST_RESULT_DEFAULT = "epf.query.firstResult.default";
		
		/**
		 * 
		 */
		String MAX_RESULTS_DEFAULT = "epf.query.maxResults.default";
		
		/**
		 * 
		 */
		String SEARCH = "search";

		/**
		 * @author PC
		 *
		 */
		interface Client {
			/**
			 *
			 */
			String SCHEMA = "schema";
			/**
			 *
			 */
			String ENTITY = "entity";
			/**
			 * 
			 */
			String ID = "id";
			/**
			 * 
			 */
			String ENTITY_PATH = "entity/{schema}/{entity}/{id}";
			/**
			 * 
			 */
			String FIRST = "first";
			/**
			 * 
			 */
			String MAX = "max";
			/**
			 *
			 */
			String SORT = "sort";
			/**
			 * 
			 */
			String PARAM_SEPARATOR = ".";
			/**
			 * 
			 */
			String SORT_ASC = "asc";
			/**
			 * 
			 */
			String SORT_DESC = "desc";
			/**
			 * 
			 */
			String LIKE = "like";
			/**
			 *
			 */
			String ENTITY_COUNT = "entity-count";
			/**
			 * 
			 */
			String TEXT = "text";
			
		}
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
    	 * 
    	 */
    	String EPF_REGISTRY_REMOTES = "epf-registry-remotes";
	    
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
	    
	    /**
	     * 
	     */
	    interface Client {
	    	/**
	    	 * 
	    	 */
	    	String REMOTE = "remote";
	    	
	    	/**
	    	 * 
	    	 */
	    	String NAME = "name";
	    	
	    	/**
	    	 * 
	    	 */
	    	String VERSION = "version";
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

		/**
		 * 
		 */
		String PROVIDER_CLASS = "epf.rules.provider.class";

		/**
		 * 
		 */
		String PROVIDER_URI = "epf.rules.provider.uri";
		
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
		/**
		 * 
		 */
		String ROOT = "epf.script.root";
		
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
	     *
	     */
	    String PRINCIPAL = "principal";
	    
	    /**
	     *
	     */
	    String CREDENTIAL = "credential";
	    
	    /**
	     *
	     */
	    String AUTH = "auth";
	    
	    /**
	     *
	     */
	    String SECURITY_MANAGEMENT = "security-management";
	    
	    /**
	     * 
	     */
	    interface Management {
	    	
	    	/**
		     * 
		     */
		    String SECURITY_MANAGEMENT_URL = "epf.security.management.url";
	    }
	    
	    /**
	     * 
	     */
	    interface Credential {
	    	
	    	/**
	    	 *
	    	 */
	    	String USERNAME = "username";
	    	
	    	/**
	    	 *
	    	 */
	    	String PASSWORD = "password";
	    	
	    	/**
	    	 *
	    	 */
	    	String PASSWORD_HASH = "password_hash";
	    }
	    
	    /**
	     * @author PC
	     *
	     */
	    interface Internal {
	    	
	    	/**
	    	 * 
	    	 */
	    	String SECURITY_UNIT_NAME = "EPF-security";
	    	
	    	/**
	    	 * 
	    	 */
	    	String SECURITY_MANAGEMENT_UNIT_NAME = "EPF-security-management";
	    	
	    	/**
	    	 * 
	    	 */
	    	String SECURITY_PRINCIPAL_UNIT_NAME = "EPF-security-principal";
	    	
	    	/**
	    	 * 
	    	 */
	    	String SECURITY_CACHE = "EPF-security-cache";
	    }
	    
	    /**
	     * @author PC
	     *
	     */
	    interface JWT {
	    	
	    	/**
	    	 * 
	    	 */
	    	String VERIFIER_PUBLIC_KEY_LOCATION = "epf.security.jwt.verify.publickey.location";
	    	
	    	/**
	    	 * 
	    	 */
	    	String VERIFIER_PUBLIC_KEY = "epf.security.jwt.verify.publickey";
	    	
	    	/**
	    	 * 
	    	 */
	    	String DECRYPTOR_KEY_LOCATION = "epf.security.jwt.decrypt.key.location";
	    	
	    	/**
	    	 * 
	    	 */
	    	String AUDIENCES = "epf.security.jwt.verify.audiences";
	        
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
	    	String CLIENT_SECRET_KEY = "epf.security.auth.openid.client.secret.key";
	    	
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
	    
	    /**
	     * 
	     */
	    interface Claims {
	    	
	    	/**
	    	 *
	    	 */
	    	String FIRST_NAME = "given_name";
	    	
	    	/**
	    	 *
	    	 */
	    	String LAST_NAME = "family_name";
	    	
	    	/**
	    	 *
	    	 */
	    	String EMAIL = "email";
	    	
	    	/**
	    	 *
	    	 */
	    	String EMAIL_REGEXP = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
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
		String SHELL_RUNNER = "epf.shell.runner";
	}
	
	/**
	 *
	 */
	String TRANSACTION = "transaction";
	
	/**
	 *
	 */
	String TRANSACTION_ACTIVE = "transaction/active";
	
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
	
	/**
	 * 
	 */
	String WORKFLOW = "workflow";
	
	/**
	 * @author PC
	 *
	 */
	interface Workflow {
		
		/**
	     * 
	     */
	    String WORKFLOW_URL = "epf.workflow.url";
		
		/**
		 * 
		 */
		String EVENT = "Event";
		
		/**
	     *
	     */
	    String WORKFLOW_MANAGEMENT = "workflow-management";
	    
	    /**
	     * 
	     */
	    String WORKFLOW_LIFECYCLE_EVENTS = "workflow-lifecycle-events";
	    
	    /**
	     * 
	     */
	    String TASK_LIFECYCLE_EVENTS = "task-lifecycle-events";
	    
	    /**
	     * 
	     */
	    String EVENTS = "events";
		
		/**
		 * 
		 */
		interface Management {
	    	
	    	/**
		     * 
		     */
		    String WORKFLOW_MANAGEMENT_URL = "epf.workflow.management.url";
	    }
		
		/**
		 * 
		 */
		interface Schema {
			
			/**
			 * 
			 */
			String DATA_INPUT_SCHEMA = "dataInputSchema";
		}
		
		/**
		 * 
		 */
		interface Internal {
			
			/**
			 * 
			 */
			String WORKFLOW_CACHE = "EPF-workflow-cache";
			
			/**
			 * 
			 */
			String WORKFLOW_DEFINITION_CACHE = "EPF-workflow-definition-cache";
		}
	}
}
