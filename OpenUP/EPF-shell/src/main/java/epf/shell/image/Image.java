/**
 * 
 */
package epf.shell.image;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import javax.ws.rs.core.Response;
import epf.client.gateway.GatewayUtil;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.shell.Function;
import epf.shell.client.ClientUtil;
import epf.shell.security.CallerPrincipal;
import epf.shell.security.Credential;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * @author PC
 *
 */
@Command(name = Naming.IMAGE)
@RequestScoped
@Function
public class Image {
	
	/**
	 * 
	 */
	@Inject
	private transient ClientUtil clientUtil;
	
	/**
	 * @param credential
	 * @param file
	 * @throws Exception 
	 */
	@Command(name = "find-contours")
	public void findContours(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-f", "--file"}, description = "File")
			final File file) throws Exception {
		try(Client client = clientUtil.newClient(GatewayUtil.get(Naming.IMAGE))){
			client.authorization(credential.getToken());
			try(InputStream input = Files.newInputStream(file.toPath())){
				try(Response response = epf.client.image.Image.findContours(client, input)){
					response.getStatus();
				}
			}
		}
	}
}
