/**
 * 
 */
package epf.portlet.persistence;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import epf.portlet.Name;

/**
 * @author PC
 *
 */
@RequestScoped
@Named(Name.PERSISTENCE)
public class Persistence implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
