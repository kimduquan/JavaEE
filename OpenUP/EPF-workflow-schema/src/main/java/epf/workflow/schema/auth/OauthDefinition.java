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
    private String authority;
    /**
     * Defines the grant type
     * (Required)
     * 
     */
    @JsonbProperty("grantType")
    @NotNull
    private OauthDefinition.GrantType grantType;
    /**
     * String or a workflow expression. Contains the client identifier
     * (Required)
     * 
     */
    @JsonbProperty("clientId")
    @Size(min = 1)
    @NotNull
    private String clientId;
    /**
     * Workflow secret or a workflow expression. Contains the client secret
     * 
     */
    @JsonbProperty("clientSecret")
    @Size(min = 1)
    private String clientSecret;
    /**
     * Array containing strings or workflow expressions. Contains the OAuth2 scopes
     * 
     */
    @JsonbProperty("scopes")
    @Size(min = 1)
    @Valid
    private List<String> scopes = new ArrayList<String>();
    /**
     * String or a workflow expression. Contains the user name. Used only if grantType is 'resourceOwner'
     * 
     */
    @JsonbProperty("username")
    @Size(min = 1)
    private String username;
    /**
     * String or a workflow expression. Contains the user password. Used only if grantType is 'resourceOwner'
     * 
     */
    @JsonbProperty("password")
    @Size(min = 1)
    private String password;
    /**
     * Array containing strings or workflow expressions. Contains the OAuth2 audiences
     * 
     */
    @JsonbProperty("audiences")
    @Size(min = 1)
    @Valid
    private List<String> audiences = new ArrayList<String>();
    /**
     * String or a workflow expression. Contains the subject token
     * 
     */
    @JsonbProperty("subjectToken")
    @Size(min = 1)
    private String subjectToken;
    /**
     * String or a workflow expression. Contains the requested subject
     * 
     */
    @JsonbProperty("requestedSubject")
    @Size(min = 1)
    private String requestedSubject;
    /**
     * String or a workflow expression. Contains the requested issuer
     * 
     */
    @JsonbProperty("requestedIssuer")
    @Size(min = 1)
    private String requestedIssuer;
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
    public OauthDefinition() {
    }

    /**
     * 
     * @param clientId
     * @param grantType
     */
    public OauthDefinition(OauthDefinition.GrantType grantType, String clientId) {
        super();
        this.grantType = grantType;
        this.clientId = clientId;
    }

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
    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public OauthDefinition withAuthority(String authority) {
        this.authority = authority;
        return this;
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
    public void setGrantType(OauthDefinition.GrantType grantType) {
        this.grantType = grantType;
    }

    public OauthDefinition withGrantType(OauthDefinition.GrantType grantType) {
        this.grantType = grantType;
        return this;
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
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public OauthDefinition withClientId(String clientId) {
        this.clientId = clientId;
        return this;
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
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public OauthDefinition withClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
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
    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    public OauthDefinition withScopes(List<String> scopes) {
        this.scopes = scopes;
        return this;
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
    public void setUsername(String username) {
        this.username = username;
    }

    public OauthDefinition withUsername(String username) {
        this.username = username;
        return this;
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
    public void setPassword(String password) {
        this.password = password;
    }

    public OauthDefinition withPassword(String password) {
        this.password = password;
        return this;
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
    public void setAudiences(List<String> audiences) {
        this.audiences = audiences;
    }

    public OauthDefinition withAudiences(List<String> audiences) {
        this.audiences = audiences;
        return this;
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
    public void setSubjectToken(String subjectToken) {
        this.subjectToken = subjectToken;
    }

    public OauthDefinition withSubjectToken(String subjectToken) {
        this.subjectToken = subjectToken;
        return this;
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
    public void setRequestedSubject(String requestedSubject) {
        this.requestedSubject = requestedSubject;
    }

    public OauthDefinition withRequestedSubject(String requestedSubject) {
        this.requestedSubject = requestedSubject;
        return this;
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
    public void setRequestedIssuer(String requestedIssuer) {
        this.requestedIssuer = requestedIssuer;
    }

    public OauthDefinition withRequestedIssuer(String requestedIssuer) {
        this.requestedIssuer = requestedIssuer;
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

    public OauthDefinition withMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }

    public enum GrantType {

        PASSWORD("password"),
        CLIENT_CREDENTIALS("clientCredentials"),
        TOKEN_EXCHANGE("tokenExchange");
        private final String value;
        private final static Map<String, OauthDefinition.GrantType> CONSTANTS = new HashMap<String, OauthDefinition.GrantType>();

        static {
            for (OauthDefinition.GrantType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        GrantType(String value) {
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
        public static OauthDefinition.GrantType fromValue(String value) {
            OauthDefinition.GrantType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }
}
