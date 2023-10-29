package epf.workflow.schema.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.nosql.Column;
import java.util.List;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class OAuth2PropertiesDefinition extends PropertiesDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	private List<String> scopes;
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
	private List<String> audiences;
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
