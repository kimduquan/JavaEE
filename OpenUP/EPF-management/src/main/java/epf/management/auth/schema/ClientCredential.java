package epf.management.auth.schema;

public class ClientCredential {
	
	public static final String CLIENT_CREDENTIALS_GRANT_TYPE = "client_credentials";

	private String grant_type = CLIENT_CREDENTIALS_GRANT_TYPE;
	private String client_id;
	private String client_secret;
	
	public String getGrant_type() {
		return grant_type;
	}
	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getClient_secret() {
		return client_secret;
	}
	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}
}
