package epf.workflow.schema.correlation;

import java.io.Serializable;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "contextAttributeName",
    "contextAttributeValue"
})
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
    private String contextAttributeName;
    /**
     * CloudEvent Extension Context Attribute value
     * 
     */
    @JsonbProperty("contextAttributeValue")
    @Size(min = 1)
    private String contextAttributeValue;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CorrelationDef() {
    }

    /**
     * 
     * @param contextAttributeName
     */
    public CorrelationDef(String contextAttributeName) {
        super();
        this.contextAttributeName = contextAttributeName;
    }

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
    public void setContextAttributeName(String contextAttributeName) {
        this.contextAttributeName = contextAttributeName;
    }

    public CorrelationDef withContextAttributeName(String contextAttributeName) {
        this.contextAttributeName = contextAttributeName;
        return this;
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
    public void setContextAttributeValue(String contextAttributeValue) {
        this.contextAttributeValue = contextAttributeValue;
    }

    public CorrelationDef withContextAttributeValue(String contextAttributeValue) {
        this.contextAttributeValue = contextAttributeValue;
        return this;
    }
}