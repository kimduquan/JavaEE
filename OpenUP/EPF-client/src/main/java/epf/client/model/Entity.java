/**
 * 
 */
package epf.client.model;

import java.util.Set;

/**
 * @author PC
 *
 */
public class Entity {

	/**
	 * 
	 */
	private Set<Attribute> attributes;
	
	/**
	 * 
	 */
	private String idType;
	
	/**
	 * 
	 */
	private String type;
	
	/**
	 * 
	 */
	private String name;
	
	/**
	 * 
	 */
	private EntityType entityType;
	
	/**
	 * @return the attributes
	 */
	public Set<Attribute> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(final Set<Attribute> attributes) {
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
	public void setIdType(final String idType) {
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
	public void setType(final String type) {
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
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the entityType
	 */
	public EntityType getEntityType() {
		return entityType;
	}

	/**
	 * @param entityType the entityType to set
	 */
	public void setEntityType(final EntityType entityType) {
		this.entityType = entityType;
	}
}
