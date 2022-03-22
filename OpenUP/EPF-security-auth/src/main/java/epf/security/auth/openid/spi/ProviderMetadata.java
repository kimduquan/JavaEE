package epf.security.auth.openid.spi;

/**
 * @author PC
 *
 */
public class ProviderMetadata {

	/**
	 * 
	 */
	private String issuer;
	
	/**
	 * 
	 */
	private String authorization_endpoint;
	
	/**
	 * 
	 */
	private String token_endpoint;
	
	/**
	 * 
	 */
	private String userinfo_endpoint;
	
	/**
	 * 
	 */
	private String jwks_uri;
	
	/**
	 * 
	 */
	private String registration_endpoint;
	
	/**
	 * 
	 */
	private String[] scopes_supported;
	
	/**
	 * 
	 */
	private String[] response_types_supported;
	
	/**
	 * 
	 */
	private String[] subject_types_supported;
	
	/**
	 * 
	 */
	private String[] id_token_signing_alg_values_supported;
	
	/**
	 * 
	 */
	private String[] claims_supported;

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(final String issuer) {
		this.issuer = issuer;
	}

	public String getAuthorization_endpoint() {
		return authorization_endpoint;
	}

	public void setAuthorization_endpoint(final String authorization_endpoint) {
		this.authorization_endpoint = authorization_endpoint;
	}

	public String getToken_endpoint() {
		return token_endpoint;
	}

	public void setToken_endpoint(final String token_endpoint) {
		this.token_endpoint = token_endpoint;
	}

	public String getUserinfo_endpoint() {
		return userinfo_endpoint;
	}

	public void setUserinfo_endpoint(final String userinfo_endpoint) {
		this.userinfo_endpoint = userinfo_endpoint;
	}

	public String getJwks_uri() {
		return jwks_uri;
	}

	public void setJwks_uri(final String jwks_uri) {
		this.jwks_uri = jwks_uri;
	}

	public String getRegistration_endpoint() {
		return registration_endpoint;
	}

	public void setRegistration_endpoint(final String registration_endpoint) {
		this.registration_endpoint = registration_endpoint;
	}

	public String[] getScopes_supported() {
		return scopes_supported;
	}

	public void setScopes_supported(final String[] scopes_supported) {
		this.scopes_supported = scopes_supported;
	}

	public String[] getResponse_types_supported() {
		return response_types_supported;
	}

	public void setResponse_types_supported(final String[] response_types_supported) {
		this.response_types_supported = response_types_supported;
	}

	public String[] getSubject_types_supported() {
		return subject_types_supported;
	}

	public void setSubject_types_supported(final String[] subject_types_supported) {
		this.subject_types_supported = subject_types_supported;
	}

	public String[] getId_token_signing_alg_values_supported() {
		return id_token_signing_alg_values_supported;
	}

	public void setId_token_signing_alg_values_supported(final String[] id_token_signing_alg_values_supported) {
		this.id_token_signing_alg_values_supported = id_token_signing_alg_values_supported;
	}

	public String[] getClaims_supported() {
		return claims_supported;
	}

	public void setClaims_supported(final String[] claims_supported) {
		this.claims_supported = claims_supported;
	}
}
