/**
 * 
 */
package epf.client.schema;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author PC
 *
 */
@XmlRootElement
public class Attribute implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	private AttributeType attributeType;
	
	/**
	 * 
	 */
	private BindableType bindable;
	/**
	 * 
	 */
	private String bindableType;
	/**
	 * 
	 */
	private boolean association;
	/**
	 * 
	 */
	private boolean collection;
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
	 * @return the attributeType
	 */
	public AttributeType getAttributeType() {
		return attributeType;
	}
	/**
	 * @param attributeType the attributeType to set
	 */
	public void setAttributeType(final AttributeType attributeType) {
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
	public void setAssociation(final boolean association) {
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
	public void setCollection(final boolean collection) {
		this.collection = collection;
	}
	/**
	 * @return the bindableType
	 */
	public String getBindableType() {
		return bindableType;
	}
	/**
	 * @param bindableType the bindableType to set
	 */
	public void setBindableType(String bindableType) {
		this.bindableType = bindableType;
	}
	/**
	 * @return the bindable
	 */
	public BindableType getBindable() {
		return bindable;
	}
	/**
	 * @param bindable the bindable to set
	 */
	public void setBindable(BindableType bindable) {
		this.bindable = bindable;
	}
}
