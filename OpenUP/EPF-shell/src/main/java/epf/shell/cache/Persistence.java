/**
 * 
 */
package epf.shell.cache;

import java.io.PrintWriter;
import javax.ws.rs.core.Response;
import javax.ws.rs.sse.SseEventSource;
import epf.client.gateway.GatewayUtil;
import epf.shell.Function;
import epf.shell.SYSTEM;
import epf.shell.client.ClientUtil;
import epf.shell.security.CallerPrincipal;
import epf.shell.security.Credential;
import epf.util.client.Client;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * @author PC
 *
 */
@Command(name = "persistence")
@RequestScoped
@Function
public class Persistence {

	/**
	 * 
	 */
	@Inject
	private transient ClientUtil clientUtil;
	
	/**
	 * 
	 */
	@Inject @Named(SYSTEM.OUT)
	private transient Instance<PrintWriter> out;
	
	/**
	 * @param credential
	 * @param name
	 * @param entityId
	 * @return
	 * @throws Exception
	 */
	@Command(name = "get")
	public String get(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-n", "--name"}, description = "Name")
			final String name, 
			@Option(names = {"-i", "--id"}, description = "ID")
			final String entityId
			) throws Exception {
		try(Client client = clientUtil.newClient(GatewayUtil.get("cache"))){
			client.authorization(credential.getToken());
			try(Response response = epf.client.cache.Cache.getEntity(client, name, entityId)){
				return response.readEntity(String.class);
			}
		}
	}
	
	/**
	 * @param credential
	 * @param name
	 * @throws Exception 
	 */
	@Command(name = "for-each")
	public void forEach(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-n", "--name"}, description = "Name")
			final String name) throws Exception {
		try(Client client = clientUtil.newClient(GatewayUtil.get("cache"))){
			client.authorization(credential.getToken());
			try(SseEventSource stream = epf.client.cache.Cache.forEachEntity(client, name)){
				stream.register(e -> {
					out.get().println(e.readData());
				});
				stream.open();
			}
		}
	}
}
