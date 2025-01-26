package epf.workflow.schema;

import java.net.URI;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotNull;

@Description("Defines the fundamentals of an 'oidc' authentication.")
public class OpenIdConnectAuthentication {
	
	public class Client {
		
		@Description("The client id to use.")
		private String id;
		
		@Description("The client secret to use, if any.")
		private String secret;
		
		@Description("A JWT containing a signed assertion with your application credentials.")
		private String assertion;
		
		@Description("The client authentication method to use.")
		@DefaultValue("client_secret_post")
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
		
		@Description("The encoding of the token request.")
		@DefaultValue("application/x-www-form-urlencoded")
		private String encoding = "application/x-www-form-urlencoded";

		public String getEncoding() {
			return encoding;
		}

		public void setEncoding(String encoding) {
			this.encoding = encoding;
		}
	}

	@NotNull
	@Description("The URI that references the authority to use when making OpenIdConnect calls.")
	private URI authority;
	
	@NotNull
	@Description("The grant type to use.")
	private String grant;
	
	private Client client;
	
	private Request request;
	
	@Description("A list that contains that contains valid issuers that will be used to check against the issuer of generated tokens.")
	private URI[] issuers;
	
	@Description("The scopes, if any, to request the token for.")
	private String[] scopes;
	
	@Description("The audiences, if any, to request the token for.")
	private String[] audiences;
	
	@Description("The username to use. Used only if the grant type is Password.")
	private String username;
	
	@Description("The password to use. Used only if the grant type is Password.")
	private String password;
	
	@Description("The security token that represents the identity of the party on behalf of whom the request is being made.")
	private OAUTH2Token subject;
	
	@Description("The security token that represents the identity of the acting party.")
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

	public URI[] getIssuers() {
		return issuers;
	}

	public void setIssuers(URI[] issuers) {
		this.issuers = issuers;
	}

	public String[] getScopes() {
		return scopes;
	}

	public void setScopes(String[] scopes) {
		this.scopes = scopes;
	}

	public String[] getAudiences() {
		return audiences;
	}

	public void setAudiences(String[] audiences) {
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
