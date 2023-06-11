package epf.workflow.schema.auth;

import javax.validation.constraints.NotNull;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class OAuth2PropertiesDefinition extends PropertiesDefinition {

	/**
	 * 
	 */
	@Column
	private String authority;
	/**
	 * 
	 */
	@Column
	@NotNull
	private GrantType grantType;
	/**
	 * 
	 */
	@Column
	@NotNull
	private String clientId;
	/**
	 * 
	 */
	@Column
	private String clientSecret;
	/**
	 * 
	 */
	@Column
	private String[] scopes;
	/**
	 * 
	 */
	@Column
	private String username;
	/**
	 * 
	 */
	@Column
	private String password;
	/**
	 * 
	 */
	@Column
	private String[] audiences;
	/**
	 * 
	 */
	@Column
	private String subjectToken;
	/**
	 * 
	 */
	@Column
	private String requestedSubject;
	/**
	 * 
	 */
	@Column
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
	public String[] getScopes() {
		return scopes;
	}
	public void setScopes(String[] scopes) {
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
	public String[] getAudiences() {
		return audiences;
	}
	public void setAudiences(String[] audiences) {
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
