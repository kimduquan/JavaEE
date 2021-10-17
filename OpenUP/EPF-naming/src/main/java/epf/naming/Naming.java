/**
 * 
 */
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
		String PERSISTENCE_URL = "epf.persistence.url";
		
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
		 * 
		 */
		String PERSISTENCE_ENTITY_LISTENERS_POSTLOAD = "persistence-PostLoad";
		
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
	}
	
	/**
	 * @author PC
	 *
	 */
	interface Shell {

		/**
		 * 
		 */
		String SHELL_PATH = "epf.shell.path";
		
		/**
		 * 
		 */
		String SHELL_COMMAND = "epf.shell.command";
	}
}
