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
     * No args constructor for use in serialization
     * 
     */
    public BasicAuthDefinition() {
    }

    /**
     * 
     * @param password
     * @param username
     */
    public BasicAuthDefinition(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

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
    public void setUsername(String username) {
        this.username = username;
    }

    public BasicAuthDefinition withUsername(String username) {
        this.username = username;
        return this;
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
    public void setPassword(String password) {
        this.password = password;
    }

    public BasicAuthDefinition withPassword(String password) {
        this.password = password;
        return this;
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
    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public BasicAuthDefinition withMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }
}