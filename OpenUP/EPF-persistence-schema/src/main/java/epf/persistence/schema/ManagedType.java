package epf.persistence.schema;

import java.util.Set;

public class ManagedType extends Type {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Set<SingularAttribute> singularAttributes;
	private Set<SingularAttribute> declaredSingularAttributes;
	private Set<PluralAttribute> pluralAttributes;
	private Set<PluralAttribute> declaredPluralAttributes;
	
	public Set<SingularAttribute> getSingularAttributes() {
		return singularAttributes;
	}
	public void setSingularAttributes(Set<SingularAttribute> singularAttributes) {
		this.singularAttributes = singularAttributes;
	}
	public Set<SingularAttribute> getDeclaredSingularAttributes() {
		return declaredSingularAttributes;
	}
	public void setDeclaredSingularAttributes(Set<SingularAttribute> declaredSingularAttributes) {
		this.declaredSingularAttributes = declaredSingularAttributes;
	}
	public Set<PluralAttribute> getPluralAttributes() {
		return pluralAttributes;
	}
	public void setPluralAttributes(Set<PluralAttribute> pluralAttributes) {
		this.pluralAttributes = pluralAttributes;
	}
	public Set<PluralAttribute> getDeclaredPluralAttributes() {
		return declaredPluralAttributes;
	}
	public void setDeclaredPluralAttributes(Set<PluralAttribute> declaredPluralAttributes) {
		this.declaredPluralAttributes = declaredPluralAttributes;
	}
}
