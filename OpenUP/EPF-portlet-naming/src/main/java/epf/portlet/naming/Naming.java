/**
 * 
 */
package epf.portlet.naming;

import javax.xml.namespace.QName;

/**
 * @author PC
 *
 */
public interface Naming {
	
	/**
	 * 
	 */
	String PORTLET_URL = "epf.portlet.url";
	
	/**
	 * 
	 */
	String STRING = "string";
	
	/**
	 * 
	 */
	String STYLE = "style";
	
	/**
	 * @author PC
	 *
	 */
	interface Bridge {

		/**
		 * 
		 */
		String PORTLET_PREFERENCES = "portletPreferences";
		
		/**
		 * 
		 */
		String PORTLET_REQUEST = "portletRequest";
		
		/**
		 * 
		 */
		String STATE_AWARE_RESPONSE = "stateAwareResponse";
		
		/**
		 * 
		 */
		String MUTABLE_RENDER_PARAMETERS = "mutableRenderParams";
		
		/**
		 * 
		 */
		String PORTLET_SESSION = "portletSession";
		
		/**
		 * 
		 */
		String PORTLET_RESPONSE = "portletResponse";
	}
	
	/**
	 * @author PC
	 *
	 */
	interface Event {
		
		/**
		 * 
		 */
		QName SCHEMA_ENTITY = QName.valueOf("{http://openup.org/epf/schema}entity");
	}
	
	/**
	 * @author PC
	 *
	 */
	interface Parameter {

		/**
		 * 
		 */
		String SCHEMA_ENTITY = "entity";
		/**
		 * 
		 */
		String PERSISTENCE_ENTITY_ID = "id";
	}
	
	/**
	 * @author PC
	 *
	 */
	interface Schema {

		/**
		 * 
		 */
		String SCHEMA = "epf_schema";

		/**
		 * 
		 */
		QName SCHEMA_ENTITY = QName.valueOf("{http://openup.org/epf/schema}entity");
	}
	
	/**
	 * @author PC
	 *
	 */
	interface Persistence {

		/**
		 * 
		 */
		String PERSISTENCE = "epf_persistence";
		/**
		 * 
		 */
		String PERSISTENCE_ENTITY = "epf_persistence_entity";
		/**
		 * 
		 */
		String PERSISTENCE_QUERY = "epf_persistence_query";
		/**
		 * 
		 */
		String PERSISTENCE_SEARCH = "epf_persistence_search";
		
	}
	
	/**
	 * @author PC
	 *
	 */
	interface Security {

		/**
		 * 
		 */
		String SECURITY = "epf_security";
		/**
		 * 
		 */
		String SECURITY_SESSION = "epf_security_session";
		/**
		 * 
		 */
		String SECURITY_TOKEN = "epf_security_token";
		
		/**
		 * 
		 */
		String SECURITY_PRINCIPAL = "epf_security_principal";
	}
}
