package epf.workflow.schema.auth;

import java.util.List;
import org.eclipse.jnosql.mapping.Embeddable;
import org.eclipse.microprofile.graphql.Description;
import jakarta.nosql.Column;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class OAuth2PropertiesDefinition extends PropertiesDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column
	@Description("String or a workflow expression. Contains the authority information")
	private String authority;
	
	@NotNull
	@Column
	@Description("Defines the grant type. Can be \"password\", \"clientCredentials\", or \"tokenExchange\"	")
	private GrantType grantType;
	
	@NotNull
	@Column
	@Description("String or a workflow expression. Contains the client identifier")
	private String clientId;
	
	@Column
	@Description("Workflow secret or a workflow expression. Contains the client secret")
	private String clientSecret;
	
	@Column
	@Description("Array containing strings or workflow expressions. Contains the OAuth2 scopes")
	private List<String> scopes;
	
	@Column
	@Description("String or a workflow expression. Contains the user name. Used only if grantType is 'resourceOwner'")
	private String username;
	
	@Column
	@Description("String or a workflow expression. Contains the user password. Used only if grantType is 'resourceOwner'")
	private String password;
	
	@Column
	@Description("Array containing strings or workflow expressions. Contains the OAuth2 audiences")
	private List<String> audiences;
	
	@Column
	@Description("String or a workflow expression. Contains the subject token")
	private String subjectToken;
	
	@Column
	@Description("String or a workflow expression. Contains the requested subject")
	private String requestedSubject;
	
	@Column
	@Description("String or a workflow expression. Contains the requested issuer")
	private String requestedIssuer;

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public GrantType getGrantType() {
		return grantType;
	}

	public void setGrantType(GrantType grantType) {
		this.grantType = grantType;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public List<String> getScopes() {
		return scopes;
	}

	public void setScopes(List<String> scopes) {
		this.scopes = scopes;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getAudiences() {
		return audiences;
	}

	public void setAudiences(List<String> audiences) {
		this.audiences = audiences;
	}

	public String getSubjectToken() {
		return subjectToken;
	}

	public void setSubjectToken(String subjectToken) {
		this.subjectToken = subjectToken;
	}

	public String getRequestedSubject() {
		return requestedSubject;
	}

	public void setRequestedSubject(String requestedSubject) {
		this.requestedSubject = requestedSubject;
	}

	public String getRequestedIssuer() {
		return requestedIssuer;
	}

	public void setRequestedIssuer(String requestedIssuer) {
		this.requestedIssuer = requestedIssuer;
	}
}
