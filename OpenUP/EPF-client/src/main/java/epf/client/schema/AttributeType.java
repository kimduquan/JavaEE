/**
 * 
 */
package epf.client.schema;

/**
 * @author PC
 *
 */
public enum AttributeType {

	/** Many-to-one association */
    MANY_TO_ONE, 

	 /** One-to-one association */
    ONE_TO_ONE, 
    
    /** Basic attribute */
    BASIC, 

    /** Embeddable class attribute */
    EMBEDDED,

    /** Many-to-many association */
    MANY_TO_MANY, 

    /** One-to-many association */
    ONE_TO_MANY, 

    /** Element collection */
    ELEMENT_COLLECTION
}
