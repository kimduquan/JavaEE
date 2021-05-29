/**
 * 
 */
package epf.client.persistence.metamodel;

/**
 * @author PC
 *
 */
public class Attribute {
	
	/**
	 * 
	 */
	private transient String type;
	/**
	 * 
	 */
	private transient String name;
	/**
	 * 
	 */
	private transient AttributeType attributeType;
	/**
	 * 
	 */
	private transient boolean association;
	/**
	 * 
	 */
	private transient boolean collection;
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
	/**
	 * @return the attributeType
	 */
	public AttributeType getAttributeType() {
		return attributeType;
	}
	/**
	 * @param attributeType the attributeType to set
	 */
	public void setAttributeType(AttributeType attributeType) {
		this.attributeType = attributeType;
	}
	/**
	 * @return the association
	 */
	public boolean isAssociation() {
		return association;
	}
	/**
	 * @param association the association to set
	 */
	public void setAssociation(boolean association) {
		this.association = association;
	}
	/**
	 * @return the collection
	 */
	public boolean isCollection() {
		return collection;
	}
	/**
	 * @param collection the collection to set
	 */
	public void setCollection(boolean collection) {
		this.collection = collection;
	}
}
