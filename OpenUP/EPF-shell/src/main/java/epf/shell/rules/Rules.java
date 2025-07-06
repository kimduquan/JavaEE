package epf.shell.rules;

import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.naming.Naming;
import epf.shell.Function;
import epf.shell.rules.admin.Admin;
import epf.shell.security.Credential;
import epf.shell.security.CallerPrincipal;
import jakarta.enterprise.context.RequestScoped;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = Naming.RULES, subcommands = {Admin.class})
@RequestScoped
@Function
public class Rules {
	
	@RestClient
	transient RulesClient rules;
	
	@Command(name = "execute")
	public String executeRules(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-r", "--rule_set"}, description = "Rule Set")
			final String ruleSet,
			@Option(names = {"-i", "--input"}, description = "Input", interactive = true, echo = true)
			final String input
			) throws Exception {
		try(Response response = rules.executeRules(credential.getAuthHeader(), ruleSet, input)){
			return response.readEntity(String.class);
		}
	}
	
	@Command(name = "add")
	public void addObject(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-r", "--rule_set"}, description = "Rule Set")
			final String ruleSet,
			@Option(names = {"-o", "--object"}, description = "Object", interactive = true, echo = true)
			final String object
			) throws Exception {
		try(Response response = rules.addObject(credential.getAuthHeader(), ruleSet, object)){
			response.getStatus();
		}
	}
	
	@Command(name = "execute-session")
	public String executeRules(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-r", "--rule_set"}, description = "Rule Set")
			final String ruleSet
			) throws Exception {
		try(Response response = rules.executeRules(credential.getAuthHeader(), ruleSet)){
			return response.readEntity(String.class);
		}
	}
	
	@Command(name = "registrations")
	public String getRegistrations(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential) throws Exception {
		try(Response response = rules.getRegistrations(credential.getAuthHeader())){
			return response.readEntity(String.class);
		}
	}
}
