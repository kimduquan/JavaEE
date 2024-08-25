package epf.shell.workflow;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.naming.Naming;
import epf.shell.Function;
import epf.shell.security.CallerPrincipal;
import epf.shell.security.Credential;
import epf.shell.workflow.management.Management;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * @author PC
 *
 */
@Command(name = Naming.WORKFLOW, subcommands = {Management.class})
@RequestScoped
@Function
public class Workflow {
	
	/**
	 * 
	 */
	@RestClient
	transient WorkflowClient client;
	
	/**
	 * @param credential
	 * @param workflow
	 * @param version
	 * @param input
	 * @return
	 * @throws Exception
	 */
	@Command(name = "start")
	public String start(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-w", "--workflow"}, description = "Workflow")
			final String workflow,
			@Option(names = {"-v", "--version"}, description = "Version")
			final String version,
			@Option(names = {"-i", "--input"}, description = "Input", interactive = true, echo = true)
			final String input
			) throws Exception {
		try(Response response = client.start(credential.getAuthHeader(), workflow, version, input)){
			return response.readEntity(String.class);
		}
	}
}
