package epf.workflow.schema.auth;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.Valid;
import javax.validation.constraints.Size;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "name",
    "scheme",
    "basicauth",
    "bearerauth",
    "oauth"
})
public class AuthDefinition implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Unique auth definition name
     * 
     */
	@JsonbProperty("name")
    @Size(min = 1)
    private String name;
    /**
     * Defines the auth type
     * 
     */
    @JsonbProperty("scheme")
    private AuthDefinition.Scheme scheme = AuthDefinition.Scheme.fromValue("basic");
    @JsonbProperty("basicauth")
    @Valid
    private BasicAuthDefinition basicauth;
    @JsonbProperty("bearerauth")
    @Valid
    private BearerAuthDefinition bearerauth;
    @JsonbProperty("oauth")
    @Valid
    private OauthDefinition oauth;

    /**
     * Unique auth definition name
     * 
     */
    @JsonbProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Unique auth definition name
     * 
     */
    @JsonbProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public AuthDefinition withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Defines the auth type
     * 
     */
    @JsonbProperty("scheme")
    public AuthDefinition.Scheme getScheme() {
        return scheme;
    }

    /**
     * Defines the auth type
     * 
     */
    @JsonbProperty("scheme")
    public void setScheme(AuthDefinition.Scheme scheme) {
        this.scheme = scheme;
    }

    public AuthDefinition withScheme(AuthDefinition.Scheme scheme) {
        this.scheme = scheme;
        return this;
    }

    @JsonbProperty("basicauth")
    public BasicAuthDefinition getBasicauth() {
        return basicauth;
    }

    @JsonbProperty("basicauth")
    public void setBasicauth(BasicAuthDefinition basicauth) {
        this.basicauth = basicauth;
    }

    public AuthDefinition withBasicauth(BasicAuthDefinition basicauth) {
        this.basicauth = basicauth;
        return this;
    }

    @JsonbProperty("bearerauth")
    public BearerAuthDefinition getBearerauth() {
        return bearerauth;
    }

    @JsonbProperty("bearerauth")
    public void setBearerauth(BearerAuthDefinition bearerauth) {
        this.bearerauth = bearerauth;
    }

    public AuthDefinition withBearerauth(BearerAuthDefinition bearerauth) {
        this.bearerauth = bearerauth;
        return this;
    }

    @JsonbProperty("oauth")
    public OauthDefinition getOauth() {
        return oauth;
    }

    @JsonbProperty("oauth")
    public void setOauth(OauthDefinition oauth) {
        this.oauth = oauth;
    }

    public AuthDefinition withOauth(OauthDefinition oauth) {
        this.oauth = oauth;
        return this;
    }

    public enum Scheme {

        BASIC("basic"),
        BEARER("bearer"),
        OAUTH_2("oauth2");
        private final String value;
        private final static Map<String, AuthDefinition.Scheme> CONSTANTS = new HashMap<String, AuthDefinition.Scheme>();

        static {
            for (AuthDefinition.Scheme c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Scheme(String value) {
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
        public static AuthDefinition.Scheme fromValue(String value) {
            AuthDefinition.Scheme constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }
}