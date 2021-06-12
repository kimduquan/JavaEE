/**
 * 
 */
package epf.shell.rules;

import java.net.URI;
import javax.ws.rs.core.Response;
import epf.shell.Function;
import epf.shell.client.ClientUtil;
import epf.shell.rules.admin.Admin;
import epf.shell.security.Credential;
import epf.shell.security.CallerPrincipal;
import epf.util.Var;
import epf.util.client.Client;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * @author PC
 *
 */
@Command(name = "rules", subcommands = {Admin.class})
@RequestScoped
@Function
public class Rules {
	
	/**
	 * 
	 */
	@Inject @Named(epf.client.rules.Rules.RULES_URL)
	private transient Var<URI> rulesUrl;
	
	/**
	 * 
	 */
	@Inject
	private transient ClientUtil clientUtil;
	
	/**
	 * @param tokenArg
	 * @param ruleSet
	 * @param input
	 * @return
	 * @throws Exception
	 */
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
		try(Client client = clientUtil.newClient(rulesUrl.get())){
			client.authorization(credential.getToken());
			try(Response response = epf.client.rules.Rules.executeRules(client, ruleSet, input)){
				return response.readEntity(String.class);
			}
		}
	}
	
	/**
	 * @param token
	 * @param ruleSet
	 * @param object
	 * @throws Exception
	 */
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
		try(Client client = clientUtil.newClient(rulesUrl.get())){
			client.authorization(credential.getToken());
			try(Response response = epf.client.rules.Rules.addObject(client, ruleSet, object)){
				response.getStatus();
			}
		}
	}
	
	/**
	 * @param token
	 * @param ruleSet
	 * @return
	 * @throws Exception
	 */
	@Command(name = "execute-session")
	public String executeRules(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-r", "--rule_set"}, description = "Rule Set")
			final String ruleSet
			) throws Exception {
		try(Client client = clientUtil.newClient(rulesUrl.get())){
			client.authorization(credential.getToken());
			try(Response response = epf.client.rules.Rules.executeRules(client, ruleSet)){
				return response.readEntity(String.class);
			}
		}
	}
	
	/**
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@Command(name = "registrations")
	public String getRegistrations(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential) throws Exception {
		try(Client client = clientUtil.newClient(rulesUrl.get())){
			client.authorization(credential.getToken());
			try(Response response = epf.client.rules.Rules.getRegistrations(client)){
				return response.readEntity(String.class);
			}
		}
	}
}
