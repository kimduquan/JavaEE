/**
 * 
 */
package epf.shell.rules.admin;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import javax.ws.rs.core.Response;
import epf.shell.Function;
import epf.shell.client.ClientUtil;
import epf.util.Var;
import epf.util.client.Client;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
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
	@Inject @Named(epf.client.rules.Rules.RULES_URL)
	private transient Var<URI> rulesUrl;
	
	/**
	 * 
	 */
	@Inject
	private transient ClientUtil clientUtil;

	/**
	 * @param token
	 * @param name
	 * @param file
	 * @throws Exception 
	 */
	@Command(name = "register")
	public void registerRuleExecutionSet(
			@Option(names = {"-t", "--token"}, description = "Token") 
			final String token,
			@Option(names = {"-n", "--name"}, description = "Name")
			final String name,
			@Option(names = {"-f", "--file"}, description = "Rules file")
			final File file 
			) throws Exception {
		try(Client client = clientUtil.newClient(rulesUrl.get())){
			client.authorization(token);
			try(InputStream input = Files.newInputStream(file.toPath())){
				try(Response response = epf.client.rules.admin.Admin.registerRuleExecutionSet(client, name, input)){
					response.getStatus();
				}
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
			@Option(names = {"-t", "--token"}, description = "Token") 
			final String token,
			@Option(names = {"-n", "--name"}, description = "Name")
			final String name
			) throws Exception {
		try(Client client = clientUtil.newClient(rulesUrl.get())){
			client.authorization(token);
			try(Response response = epf.client.rules.admin.Admin.deregisterRuleExecutionSet(client, name)){
				response.getStatus();
			}
		}
	}
}
