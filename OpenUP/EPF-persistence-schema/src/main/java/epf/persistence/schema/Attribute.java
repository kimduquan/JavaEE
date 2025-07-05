package epf.persistence.schema;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Attribute implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String type;
	private String name;
	private AttributeType attributeType;
	private BindableType bindable;
	private String bindableType;
	private boolean association;
	private boolean collection;
	private Column column;
	
	public String getType() {
		return type;
	}
	
	public void setType(final String type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
	
	public AttributeType getAttributeType() {
		return attributeType;
	}
	
	public void setAttributeType(final AttributeType attributeType) {
		this.attributeType = attributeType;
	}
	
	public boolean isAssociation() {
		return association;
	}
	
	public void setAssociation(final boolean association) {
		this.association = association;
	}
	
	public boolean isCollection() {
		return collection;
	}
	
	public void setCollection(final boolean collection) {
		this.collection = collection;
	}
	
	public String getBindableType() {
		return bindableType;
	}
	
	public void setBindableType(String bindableType) {
		this.bindableType = bindableType;
	}
	
	public BindableType getBindable() {
		return bindable;
	}
	
	public void setBindable(BindableType bindable) {
		this.bindable = bindable;
	}
	
	public Column getColumn() {
		return column;
	}
	
	public void setColumn(final Column column) {
		this.column = column;
	}
}
