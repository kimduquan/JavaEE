package epf.shell.rules.admin;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.shell.Function;
import epf.shell.security.Credential;
import epf.shell.security.CallerPrincipal;
import jakarta.enterprise.context.RequestScoped;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "admin")
@RequestScoped
@Function
public class Admin {
	
	@RestClient
	transient AdminClient admin;

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
			try(Response response = admin.registerRuleExecutionSet(credential.getAuthHeader(), name, input)){
				response.getStatus();
			}
		}
	}
	
	@Command(name = "de-register")
	public void deregisterRuleExecutionSet(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-n", "--name"}, description = "Name")
			final String name
			) throws Exception {
		try(Response response = admin.deregisterRuleExecutionSet(credential.getAuthHeader(), name)){
			response.getStatus();
		}
	}
}
