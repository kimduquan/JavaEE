/**
 * 
 */
package epf.shell.image;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.shell.Function;
import epf.shell.client.ClientUtil;
import epf.shell.security.CallerPrincipal;
import epf.shell.security.Credential;
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
	transient ClientUtil clientUtil;
	
	/**
	 * 
	 */
	@ConfigProperty(name = Naming.Gateway.GATEWAY_URL)
	String gatewayUrl;
	
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
		try(Client client = clientUtil.newClient(gatewayUrl, Naming.IMAGE)){
			client.authorization(credential.getToken());
			try(InputStream input = Files.newInputStream(file.toPath())){
				try(Response response = epf.client.image.Image.findContours(client, input)){
					response.getStatus();
				}
			}
		}
	}
}
