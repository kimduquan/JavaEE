package epf.workflow.schema.error;

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
    "name",
    "code",
    "description"
})
public class ErrorDefinition implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Domain-specific error name
     * (Required)
     * 
     */
    @JsonbProperty("name")
    @Size(min = 1)
    @NotNull
    private String name;
    /**
     * Error code. Can be used in addition to the name to help runtimes resolve to technical errors/exceptions. Should not be defined if error is set to '*'
     * 
     */
    @JsonbProperty("code")
    @Size(min = 1)
    private String code;
    /**
     * Error description
     * 
     */
    @JsonbProperty("description")
    private String description;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ErrorDefinition() {
    }

    /**
     * 
     * @param name
     */
    public ErrorDefinition(String name) {
        super();
        this.name = name;
    }

    /**
     * Domain-specific error name
     * (Required)
     * 
     */
    @JsonbProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Domain-specific error name
     * (Required)
     * 
     */
    @JsonbProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public ErrorDefinition withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Error code. Can be used in addition to the name to help runtimes resolve to technical errors/exceptions. Should not be defined if error is set to '*'
     * 
     */
    @JsonbProperty("code")
    public String getCode() {
        return code;
    }

    /**
     * Error code. Can be used in addition to the name to help runtimes resolve to technical errors/exceptions. Should not be defined if error is set to '*'
     * 
     */
    @JsonbProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    public ErrorDefinition withCode(String code) {
        this.code = code;
        return this;
    }

    /**
     * Error description
     * 
     */
    @JsonbProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * Error description
     * 
     */
    @JsonbProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public ErrorDefinition withDescription(String description) {
        this.description = description;
        return this;
    }

}
