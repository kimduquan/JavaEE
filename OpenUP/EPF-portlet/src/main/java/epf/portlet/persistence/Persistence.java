/**
 * 
 */
package epf.portlet.persistence;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import epf.portlet.Portlet;

/**
 * @author PC
 *
 */
@RequestScoped
@Named(Portlet.PERSISTENCE)
public class Persistence implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
