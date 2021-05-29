/**
 * 
 */
package epf.client.persistence.metamodel;

/**
 * @author PC
 *
 */
public enum AttributeType {

	/** Many-to-one association */
    ManyToOne, 

	 /** One-to-one association */
    OneToOne, 
    
    /** Basic attribute */
    Basic, 

    /** Embeddable class attribute */
    Embedded,

    /** Many-to-many association */
    ManyToMany, 

    /** One-to-many association */
    OneToMany, 

    /** Element collection */
    ElementCollection
}
