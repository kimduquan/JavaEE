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
    public void setName(final String name) {
        this.name = name;
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
    public void setCode(final String code) {
        this.code = code;
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
    public void setDescription(final String description) {
        this.description = description;
    }
}
