package epf.persistence.schema;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Attribute implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private PersistentAttributeType persistentAttributeType;
	private String declaringType;
	private String javaType;
	private String javaMember;
	private boolean association;
	private boolean collection;
	private Column column;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public PersistentAttributeType getPersistentAttributeType() {
		return persistentAttributeType;
	}
	public void setPersistentAttributeType(PersistentAttributeType persistentAttributeType) {
		this.persistentAttributeType = persistentAttributeType;
	}
	public String getDeclaringType() {
		return declaringType;
	}
	public void setDeclaringType(String declaringType) {
		this.declaringType = declaringType;
	}
	public String getJavaType() {
		return javaType;
	}
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
	public String getJavaMember() {
		return javaMember;
	}
	public void setJavaMember(String javaMember) {
		this.javaMember = javaMember;
	}
	public boolean isAssociation() {
		return association;
	}
	public void setAssociation(boolean association) {
		this.association = association;
	}
	public boolean isCollection() {
		return collection;
	}
	public void setCollection(boolean collection) {
		this.collection = collection;
	}
	public Column getColumn() {
		return column;
	}
	public void setColumn(Column column) {
		this.column = column;
	}
}
