/**
 * 
 */
package epf.shell.image;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import javax.ws.rs.core.Response;
import epf.shell.Function;
import epf.shell.client.ClientUtil;
import epf.shell.security.CallerPrincipal;
import epf.shell.security.Credential;
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
@Command(name = "image")
@RequestScoped
@Function
public class Image {
	
	/**
	 * 
	 */
	@Inject @Named(epf.client.image.Image.IMAGE_URL)
	private transient Var<URI> imageUrl;
	
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
		try(Client client = clientUtil.newClient(imageUrl.get())){
			client.authorization(credential.getToken());
			try(InputStream input = Files.newInputStream(file.toPath())){
				try(Response response = epf.client.image.Image.findContours(client, input)){
					response.getStatus();
				}
			}
			
		}
	}
}
