package epf.persistence.schema;

import java.io.Serializable;

public class Type implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PersistenceType persistenceType;
    private String javaType;
    
	public PersistenceType getPersistenceType() {
		return persistenceType;
	}
	public void setPersistenceType(PersistenceType persistenceType) {
		this.persistenceType = persistenceType;
	}
	public String getJavaType() {
		return javaType;
	}
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
}
