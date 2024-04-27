package epf.shell.workflow.management;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.naming.Naming;
import epf.shell.Function;
import epf.shell.security.CallerPrincipal;
import epf.shell.security.Credential;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * @author PC
 *
 */
@Command(name = Naming.MANAGEMENT)
@RequestScoped
@Function
public class Management {
	
	/**
	 * 
	 */
	@Inject
	@RestClient
	transient ManagementClient management;
	
	/**
	 * @param credential
	 * @param input
	 * @return
	 * @throws Exception
	 */
	@Command(name = "new")
	public String newWorkflowDefinition(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-i", "--input"}, description = "Input", interactive = true, echo = true)
			final String input
			) throws Exception {
		try(Response response = management.newWorkflowDefinition(credential.getAuthHeader(), input)){
			return response.readEntity(String.class);
		}
	}
}
