package epf.shell.security;

import epf.naming.Naming;
import epf.shell.Function;
import epf.shell.security.schema.TokenInfo;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = Naming.SECURITY)
@RequestScoped
@Function
public class Security {
	
	public static final String TOKEN_ARG = "--token";
	public static final String TOKEN_DESC = "Token";
	
	@Inject
	@ConfigProperty(name = Naming.Security.Auth.GRANT_TYPE)
	String grant_type;
	
	@Inject
	@ConfigProperty(name = Naming.Security.Auth.CLIENT_ID)
	String client_id;
	
	@Inject
	@ConfigProperty(name = Naming.Security.Auth.CLIENT_SECRET)
	String client_secret;
	
	@Inject
	transient IdentityStore identityStore;
	
	@RestClient
	transient SecurityAuthClient security;

	@Command(name = "auth")
	public String authenticate(
			@Option(names = {"-u", "--username"}, required = true, description = "User name")
			@NotBlank
			final String username,
			@Option(names = {"-p", "--password"}, required = true, description = "Password", interactive = true)
		    @NotEmpty
			final char... password
			) throws Exception {
		final TokenInfo tokenInfo = security.login(grant_type, client_id, client_secret, username, new String(password));
		final Credential credential = new Credential();
		credential.setRawToken(tokenInfo.getAccess_token());
		credential.setTokenID(identityStore.getTokenId(tokenInfo.getAccess_token()));
		identityStore.put(credential);
		return tokenInfo.getAccess_token();
	}
	
	@Command(name = "logout")
	public void logout(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential
			) throws Exception {
		identityStore.remove(credential);
	}
}
