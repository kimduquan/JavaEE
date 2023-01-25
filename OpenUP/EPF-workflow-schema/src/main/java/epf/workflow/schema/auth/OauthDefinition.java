package epf.workflow.schema.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;
import jakarta.nosql.mapping.Entity;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "authority",
    "grantType",
    "clientId",
    "clientSecret",
    "scopes",
    "username",
    "password",
    "audiences",
    "subjectToken",
    "requestedSubject",
    "requestedIssuer",
    "metadata"
})
@Entity
public class OauthDefinition implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * String or a workflow expression. Contains the authority information
     * 
     */
    @JsonbProperty("authority")
    @Size(min = 1)
    @Column
    private String authority;
    /**
     * Defines the grant type
     * (Required)
     * 
     */
    @JsonbProperty("grantType")
    @NotNull
    @Column
    private OauthDefinition.GrantType grantType;
    /**
     * String or a workflow expression. Contains the client identifier
     * (Required)
     * 
     */
    @JsonbProperty("clientId")
    @Size(min = 1)
    @NotNull
    @Column
    private String clientId;
    /**
     * Workflow secret or a workflow expression. Contains the client secret
     * 
     */
    @JsonbProperty("clientSecret")
    @Size(min = 1)
    @Column
    private String clientSecret;
    /**
     * Array containing strings or workflow expressions. Contains the OAuth2 scopes
     * 
     */
    @JsonbProperty("scopes")
    @Size(min = 1)
    @Valid
    @Column
    private List<String> scopes = new ArrayList<String>();
    /**
     * String or a workflow expression. Contains the user name. Used only if grantType is 'resourceOwner'
     * 
     */
    @JsonbProperty("username")
    @Size(min = 1)
    @Column
    private String username;
    /**
     * String or a workflow expression. Contains the user password. Used only if grantType is 'resourceOwner'
     * 
     */
    @JsonbProperty("password")
    @Size(min = 1)
    @Column
    private String password;
    /**
     * Array containing strings or workflow expressions. Contains the OAuth2 audiences
     * 
     */
    @JsonbProperty("audiences")
    @Size(min = 1)
    @Valid
    @Column
    private List<String> audiences = new ArrayList<String>();
    /**
     * String or a workflow expression. Contains the subject token
     * 
     */
    @JsonbProperty("subjectToken")
    @Size(min = 1)
    @Column
    private String subjectToken;
    /**
     * String or a workflow expression. Contains the requested subject
     * 
     */
    @JsonbProperty("requestedSubject")
    @Size(min = 1)
    @Column
    private String requestedSubject;
    /**
     * String or a workflow expression. Contains the requested issuer
     * 
     */
    @JsonbProperty("requestedIssuer")
    @Size(min = 1)
    @Column
    private String requestedIssuer;
    /**
     * Metadata
     * 
     */
    @JsonbProperty("metadata")
    @Valid
    @Column
    private Map<String, String> metadata;

    /**
     * String or a workflow expression. Contains the authority information
     * 
     */
    @JsonbProperty("authority")
    public String getAuthority() {
        return authority;
    }

    /**
     * String or a workflow expression. Contains the authority information
     * 
     */
    @JsonbProperty("authority")
    public void setAuthority(final String authority) {
        this.authority = authority;
    }

    /**
     * Defines the grant type
     * (Required)
     * 
     */
    @JsonbProperty("grantType")
    public OauthDefinition.GrantType getGrantType() {
        return grantType;
    }

    /**
     * Defines the grant type
     * (Required)
     * 
     */
    @JsonbProperty("grantType")
    public void setGrantType(final OauthDefinition.GrantType grantType) {
        this.grantType = grantType;
    }

    /**
     * String or a workflow expression. Contains the client identifier
     * (Required)
     * 
     */
    @JsonbProperty("clientId")
    public String getClientId() {
        return clientId;
    }

    /**
     * String or a workflow expression. Contains the client identifier
     * (Required)
     * 
     */
    @JsonbProperty("clientId")
    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    /**
     * Workflow secret or a workflow expression. Contains the client secret
     * 
     */
    @JsonbProperty("clientSecret")
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * Workflow secret or a workflow expression. Contains the client secret
     * 
     */
    @JsonbProperty("clientSecret")
    public void setClientSecret(final String clientSecret) {
        this.clientSecret = clientSecret;
    }

    /**
     * Array containing strings or workflow expressions. Contains the OAuth2 scopes
     * 
     */
    @JsonbProperty("scopes")
    public List<String> getScopes() {
        return scopes;
    }

    /**
     * Array containing strings or workflow expressions. Contains the OAuth2 scopes
     * 
     */
    @JsonbProperty("scopes")
    public void setScopes(final List<String> scopes) {
        this.scopes = scopes;
    }

    /**
     * String or a workflow expression. Contains the user name. Used only if grantType is 'resourceOwner'
     * 
     */
    @JsonbProperty("username")
    public String getUsername() {
        return username;
    }

    /**
     * String or a workflow expression. Contains the user name. Used only if grantType is 'resourceOwner'
     * 
     */
    @JsonbProperty("username")
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * String or a workflow expression. Contains the user password. Used only if grantType is 'resourceOwner'
     * 
     */
    @JsonbProperty("password")
    public String getPassword() {
        return password;
    }

    /**
     * String or a workflow expression. Contains the user password. Used only if grantType is 'resourceOwner'
     * 
     */
    @JsonbProperty("password")
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Array containing strings or workflow expressions. Contains the OAuth2 audiences
     * 
     */
    @JsonbProperty("audiences")
    public List<String> getAudiences() {
        return audiences;
    }

    /**
     * Array containing strings or workflow expressions. Contains the OAuth2 audiences
     * 
     */
    @JsonbProperty("audiences")
    public void setAudiences(final List<String> audiences) {
        this.audiences = audiences;
    }

    /**
     * String or a workflow expression. Contains the subject token
     * 
     */
    @JsonbProperty("subjectToken")
    public String getSubjectToken() {
        return subjectToken;
    }

    /**
     * String or a workflow expression. Contains the subject token
     * 
     */
    @JsonbProperty("subjectToken")
    public void setSubjectToken(final String subjectToken) {
        this.subjectToken = subjectToken;
    }

    /**
     * String or a workflow expression. Contains the requested subject
     * 
     */
    @JsonbProperty("requestedSubject")
    public String getRequestedSubject() {
        return requestedSubject;
    }

    /**
     * String or a workflow expression. Contains the requested subject
     * 
     */
    @JsonbProperty("requestedSubject")
    public void setRequestedSubject(final String requestedSubject) {
        this.requestedSubject = requestedSubject;
    }

    /**
     * String or a workflow expression. Contains the requested issuer
     * 
     */
    @JsonbProperty("requestedIssuer")
    public String getRequestedIssuer() {
        return requestedIssuer;
    }

    /**
     * String or a workflow expression. Contains the requested issuer
     * 
     */
    @JsonbProperty("requestedIssuer")
    public void setRequestedIssuer(final String requestedIssuer) {
        this.requestedIssuer = requestedIssuer;
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

    /**
     * @author PC
     *
     */
    @Embeddable
    public enum GrantType {

        PASSWORD("password"),
        CLIENT_CREDENTIALS("clientCredentials"),
        TOKEN_EXCHANGE("tokenExchange");
        /**
         * 
         */
        private final String value;
        /**
         * 
         */
        private final static Map<String, OauthDefinition.GrantType> CONSTANTS = new HashMap<String, OauthDefinition.GrantType>();

        static {
            for (OauthDefinition.GrantType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        /**
         * @param value
         */
        GrantType(final String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        @JsonbCreator
        public static OauthDefinition.GrantType fromValue(final String value) {
        	final OauthDefinition.GrantType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }
    }
}
