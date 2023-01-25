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
    "token",
    "metadata"
})
public class BearerAuthDefinition implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * String or a workflow expression. Contains the token
     * (Required)
     * 
     */
    @JsonbProperty("token")
    @Size(min = 1)
    @NotNull
    private String token;
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
    public BearerAuthDefinition() {
    }

    /**
     * 
     * @param token
     */
    public BearerAuthDefinition(String token) {
        super();
        this.token = token;
    }

    /**
     * String or a workflow expression. Contains the token
     * (Required)
     * 
     */
    @JsonbProperty("token")
    public String getToken() {
        return token;
    }

    /**
     * String or a workflow expression. Contains the token
     * (Required)
     * 
     */
    @JsonbProperty("token")
    public void setToken(String token) {
        this.token = token;
    }

    public BearerAuthDefinition withToken(String token) {
        this.token = token;
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

    public BearerAuthDefinition withMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }

}