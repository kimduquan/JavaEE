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
	 * @author PC
	 *
	 */
	interface Schema {

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
		
	}
}
