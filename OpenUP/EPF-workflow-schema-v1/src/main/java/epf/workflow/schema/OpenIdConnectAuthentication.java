package epf.workflow.schema;

import java.net.URI;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Defines the fundamentals of an 'oidc' authentication.")
public class OpenIdConnectAuthentication {
	
	public class Client {
		
		@JsonPropertyDescription("The client id to use. Required if the client.authentication method has not been set to none.")
		private String id;
		
		@JsonPropertyDescription("The client secret to use, if any.")
		private String secret;
		
		@JsonPropertyDescription("A JWT containing a signed assertion with your application credentials. Required when client.authentication has been set to private_key_jwt.")
		private String assertion;
		
		@JsonPropertyDescription("The client authentication method to use. Supported values are client_secret_basic, client_secret_post, client_secret_jwt, private_key_jwt or none. Defaults to client_secret_post.")
		private String authentication = "client_secret_post";

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getSecret() {
			return secret;
		}

		public void setSecret(String secret) {
			this.secret = secret;
		}

		public String getAssertion() {
			return assertion;
		}

		public void setAssertion(String assertion) {
			this.assertion = assertion;
		}

		public String getAuthentication() {
			return authentication;
		}

		public void setAuthentication(String authentication) {
			this.authentication = authentication;
		}
	}
	
	public class Request {
		
		@JsonPropertyDescription("The encoding of the token request. Supported values are application/x-www-form-urlencoded and application/json. Defaults to application/x-www-form-urlencoded.")
		private String encoding = "application/x-www-form-urlencoded";

		public String getEncoding() {
			return encoding;
		}

		public void setEncoding(String encoding) {
			this.encoding = encoding;
		}
	}

	@NotNull
	@JsonPropertyDescription("The URI that references the authority to use when making OpenIdConnect calls.")
	private URI authority;
	
	@NotNull
	@JsonPropertyDescription("The grant type to use. Supported values are authorization_code, client_credentials, password, refresh_token and urn:ietf:params:oauth:grant-type:token-exchange.")
	private String grant;
	
	private Client client;
	
	private Request request;
	
	@JsonPropertyDescription("A list that contains that contains valid issuers that will be used to check against the issuer of generated tokens.")
	private List<URI> issuers;
	
	@JsonPropertyDescription("The scopes, if any, to request the token for.")
	private List<String> scopes;
	
	@JsonPropertyDescription("The audiences, if any, to request the token for.")
	private List<String> audiences;
	
	@JsonPropertyDescription("The username to use. Used only if the grant type is Password.")
	private String username;
	
	@JsonPropertyDescription("The password to use. Used only if the grant type is Password.")
	private String password;
	
	@JsonPropertyDescription("The security token that represents the identity of the party on behalf of whom the request is being made.")
	private OAUTH2Token subject;
	
	@JsonPropertyDescription("The security token that represents the identity of the acting party.")
	private OAUTH2Token actor;

	public URI getAuthority() {
		return authority;
	}

	public void setAuthority(URI authority) {
		this.authority = authority;
	}

	public String getGrant() {
		return grant;
	}

	public void setGrant(String grant) {
		this.grant = grant;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public List<URI> getIssuers() {
		return issuers;
	}

	public void setIssuers(List<URI> issuers) {
		this.issuers = issuers;
	}

	public List<String> getScopes() {
		return scopes;
	}

	public void setScopes(List<String> scopes) {
		this.scopes = scopes;
	}

	public List<String> getAudiences() {
		return audiences;
	}

	public void setAudiences(List<String> audiences) {
		this.audiences = audiences;
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

	public OAUTH2Token getSubject() {
		return subject;
	}

	public void setSubject(OAUTH2Token subject) {
		this.subject = subject;
	}

	public OAUTH2Token getActor() {
		return actor;
	}

	public void setActor(OAUTH2Token actor) {
		this.actor = actor;
	}
}
