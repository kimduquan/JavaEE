package epf.shell.rules.admin;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import javax.ws.rs.core.Response;
import epf.shell.Function;
import epf.shell.client.RestClientUtil;
import epf.shell.security.Credential;
import epf.shell.security.CallerPrincipal;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * @author PC
 *
 */
@Command(name = "admin")
@RequestScoped
@Function
public class Admin {
	
	/**
	 * 
	 */
	@Inject
	transient RestClientUtil restClient;

	/**
	 * @param token
	 * @param name
	 * @param file
	 * @throws Exception 
	 */
	@Command(name = "register")
	public void registerRuleExecutionSet(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-n", "--name"}, description = "Name")
			final String name,
			@Option(names = {"-f", "--file"}, description = "Rules file")
			final File file 
			) throws Exception {
		try(InputStream input = Files.newInputStream(file.toPath())){
			try(Response response = restClient.newClient(AdminClient.class).registerRuleExecutionSet(credential.getAuthHeader(), name, input)){
				response.getStatus();
			}
		}
	}
	
	/**
	 * @param token
	 * @param name
	 * @throws Exception
	 */
	@Command(name = "de-register")
	public void deregisterRuleExecutionSet(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-n", "--name"}, description = "Name")
			final String name
			) throws Exception {
		try(Response response = restClient.newClient(AdminClient.class).deregisterRuleExecutionSet(credential.getAuthHeader(), name)){
			response.getStatus();
		}
	}
}
