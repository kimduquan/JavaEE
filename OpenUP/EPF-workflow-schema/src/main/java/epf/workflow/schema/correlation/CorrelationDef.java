package epf.workflow.schema.correlation;

import java.io.Serializable;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "contextAttributeName",
    "contextAttributeValue"
})
@Embeddable
public class CorrelationDef implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * CloudEvent Extension Context Attribute name
     * (Required)
     * 
     */
    @JsonbProperty("contextAttributeName")
    @Size(min = 1)
    @NotNull
    @Column
    private String contextAttributeName;
    /**
     * CloudEvent Extension Context Attribute value
     * 
     */
    @JsonbProperty("contextAttributeValue")
    @Size(min = 1)
    @Column
    private String contextAttributeValue;

    /**
     * CloudEvent Extension Context Attribute name
     * (Required)
     * 
     */
    @JsonbProperty("contextAttributeName")
    public String getContextAttributeName() {
        return contextAttributeName;
    }

    /**
     * CloudEvent Extension Context Attribute name
     * (Required)
     * 
     */
    @JsonbProperty("contextAttributeName")
    public void setContextAttributeName(final String contextAttributeName) {
        this.contextAttributeName = contextAttributeName;
    }

    /**
     * CloudEvent Extension Context Attribute value
     * 
     */
    @JsonbProperty("contextAttributeValue")
    public String getContextAttributeValue() {
        return contextAttributeValue;
    }

    /**
     * CloudEvent Extension Context Attribute value
     * 
     */
    @JsonbProperty("contextAttributeValue")
    public void setContextAttributeValue(final String contextAttributeValue) {
        this.contextAttributeValue = contextAttributeValue;
    }
}