/**
 * 
 */
package epf.portlet;

import javax.xml.namespace.QName;

/**
 * @author PC
 *
 */
public interface Event {

	/**
	 * 
	 */
	QName SECURITY_TOKEN = QName.valueOf("{http://openup.org/epf/security}token");
	
	/**
	 * 
	 */
	QName SCHEMA_ENTITY = QName.valueOf("{http://openup.org/epf/schema}entity");
}
