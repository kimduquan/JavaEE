package epf.persistence.schema.client;

/**
 * @author PC
 *
 */
public enum EntityType {
	/** Entity */
	ENTITY, 
	
	/** Embeddable class */
	EMBEDDABLE,
	
	/** Mapped superclass */
	MAPPED_SUPER_CLASS,
	
	/** Basic type */
	BASIC
}
