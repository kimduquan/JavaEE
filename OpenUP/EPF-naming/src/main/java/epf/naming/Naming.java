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
	public interface Batch {
		
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
	public interface Cache {

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
	public interface Config {
		
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
	public interface File {
	    
	    /**
	     * 
	     */
	    String FILE_URL = "epf.file.url";
		
	}
	
	/**
	 * 
	 */
	String GATEWAY = "gateway";
	
	/**
	 * @author PC
	 *
	 */
	public interface Gateway {
		
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
	public interface Health {
		
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
	public interface Image {

		/**
		 * 
		 */
		String IMAGE_URL = "epf.image.url";
		
	}
	
	/**
	 * @author PC
	 *
	 */
	public interface Lang {

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
	public interface Management {

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
	public interface Messaging {
		
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
	public interface Net {
		
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
	public interface Persistence {

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
		
	}
	
	/**
	 * 
	 */
	String PLANNING = "planning";
	
	/**
	 * @author PC
	 *
	 */
	public interface Planning {

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
	public interface Registry {
		
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
	public interface Rules {
		
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
	public interface Schedule {
		
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
	public interface Schema {

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
	public interface Script {
		
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
	public interface Security {
		
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
	public interface Shell {

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
