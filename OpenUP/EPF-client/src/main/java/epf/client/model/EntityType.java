/**
 * 
 */
package epf.client.model;

import java.util.HashSet;
import java.util.Set;

/**
 * @author PC
 *
 */
public class EntityType {

	/**
	 * 
	 */
	private transient Set<Attribute> attributes = new HashSet<>();
	
	/**
	 * 
	 */
	private transient String idType;
	
	/**
	 * 
	 */
	private transient String type;
	
	/**
	 * 
	 */
	private transient String name;
	
	/**
	 * @return the attributes
	 */
	public Set<Attribute> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(Set<Attribute> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the idType
	 */
	public String getIdType() {
		return idType;
	}

	/**
	 * @param idType the idType to set
	 */
	public void setIdType(String idType) {
		this.idType = idType;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
