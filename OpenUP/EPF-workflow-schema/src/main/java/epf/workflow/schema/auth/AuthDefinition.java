package epf.workflow.schema.auth;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

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
@Embeddable
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
	@Column
    private String name;
    /**
     * Defines the auth type
     * 
     */
    @JsonbProperty("scheme")
	@Column
    private AuthDefinition.Scheme scheme = AuthDefinition.Scheme.fromValue("basic");
    /**
     * 
     */
    @JsonbProperty("basicauth")
    @Valid
	@Column
    private BasicAuthDefinition basicauth;
    /**
     * 
     */
    @JsonbProperty("bearerauth")
    @Valid
	@Column
    private BearerAuthDefinition bearerauth;
    /**
     * 
     */
    @JsonbProperty("oauth")
    @Valid
	@Column
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
    public void setName(final String name) {
        this.name = name;
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
    public void setScheme(final AuthDefinition.Scheme scheme) {
        this.scheme = scheme;
    }

    @JsonbProperty("basicauth")
    public BasicAuthDefinition getBasicauth() {
        return basicauth;
    }

    @JsonbProperty("basicauth")
    public void setBasicauth(final BasicAuthDefinition basicauth) {
        this.basicauth = basicauth;
    }

    @JsonbProperty("bearerauth")
    public BearerAuthDefinition getBearerauth() {
        return bearerauth;
    }

    @JsonbProperty("bearerauth")
    public void setBearerauth(final BearerAuthDefinition bearerauth) {
        this.bearerauth = bearerauth;
    }

    @JsonbProperty("oauth")
    public OauthDefinition getOauth() {
        return oauth;
    }

    @JsonbProperty("oauth")
    public void setOauth(final OauthDefinition oauth) {
        this.oauth = oauth;
    }

    /**
     * @author PC
     *
     */
    @Embeddable
    public enum Scheme {

        BASIC("basic"),
        BEARER("bearer"),
        OAUTH_2("oauth2");
        /**
         * 
         */
        private final String value;
        /**
         * 
         */
        private final static Map<String, AuthDefinition.Scheme> CONSTANTS = new HashMap<String, AuthDefinition.Scheme>();

        static {
            for (AuthDefinition.Scheme c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        /**
         * @param value
         */
        Scheme(final String value) {
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
        public static AuthDefinition.Scheme fromValue(final String value) {
        	final AuthDefinition.Scheme constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }
    }
}