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
	QName SECURITY_PRINCIPAL = QName.valueOf("{http://openup.org/epf/security}principal");
	
	/**
	 * 
	 */
	QName SCHEMA_ENTITY = QName.valueOf("{http://openup.org/epf/schema}entity");
}
