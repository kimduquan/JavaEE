package epf.workflow.schema.auth;

import java.io.Serializable;
import java.util.Map;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "username",
    "password",
    "metadata"
})
public class BasicAuthDefinition implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * String or a workflow expression. Contains the user name
     * (Required)
     * 
     */
    @JsonbProperty("username")
    @Size(min = 1)
    @NotNull
    private String username;
    /**
     * String or a workflow expression. Contains the user password
     * (Required)
     * 
     */
    @JsonbProperty("password")
    @Size(min = 1)
    @NotNull
    private String password;
    /**
     * Metadata
     * 
     */
    @JsonbProperty("metadata")
    @Valid
    private Map<String, String> metadata;

    /**
     * String or a workflow expression. Contains the user name
     * (Required)
     * 
     */
    @JsonbProperty("username")
    public String getUsername() {
        return username;
    }

    /**
     * String or a workflow expression. Contains the user name
     * (Required)
     * 
     */
    @JsonbProperty("username")
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * String or a workflow expression. Contains the user password
     * (Required)
     * 
     */
    @JsonbProperty("password")
    public String getPassword() {
        return password;
    }

    /**
     * String or a workflow expression. Contains the user password
     * (Required)
     * 
     */
    @JsonbProperty("password")
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Metadata
     * 
     */
    @JsonbProperty("metadata")
    public Map<String, String> getMetadata() {
        return metadata;
    }

    /**
     * Metadata
     * 
     */
    @JsonbProperty("metadata")
    public void setMetadata(final Map<String, String> metadata) {
        this.metadata = metadata;
    }
}