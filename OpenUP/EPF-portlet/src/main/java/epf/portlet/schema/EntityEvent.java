/**
 * 
 */
package epf.portlet.schema;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

import epf.client.schema.Entity;

/**
 * @author PC
 *
 */
@XmlRootElement
public class EntityEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private Entity entity;

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(final Entity entity) {
		this.entity = entity;
	}

}
