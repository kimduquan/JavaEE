package epf.persistence.schema;

import java.util.Set;

public class IdentifiableType extends ManagedType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IdentifiableType supertype;
	private boolean hasSingleIdAttribute;
	private boolean hasVersionAttribute;
	private Set<SingularAttribute> idClassAttributes;
	private Type idType;
	
	public IdentifiableType getSupertype() {
		return supertype;
	}
	public void setSupertype(IdentifiableType supertype) {
		this.supertype = supertype;
	}
	public boolean isHasSingleIdAttribute() {
		return hasSingleIdAttribute;
	}
	public void setHasSingleIdAttribute(boolean hasSingleIdAttribute) {
		this.hasSingleIdAttribute = hasSingleIdAttribute;
	}
	public boolean isHasVersionAttribute() {
		return hasVersionAttribute;
	}
	public void setHasVersionAttribute(boolean hasVersionAttribute) {
		this.hasVersionAttribute = hasVersionAttribute;
	}
	public Set<SingularAttribute> getIdClassAttributes() {
		return idClassAttributes;
	}
	public void setIdClassAttributes(Set<SingularAttribute> idClassAttributes) {
		this.idClassAttributes = idClassAttributes;
	}
	public Type getIdType() {
		return idType;
	}
	public void setIdType(Type idType) {
		this.idType = idType;
	}
}
