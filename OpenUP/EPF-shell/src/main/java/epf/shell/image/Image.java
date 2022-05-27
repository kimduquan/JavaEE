package epf.shell.image;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
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
		try(InputStream input = Files.newInputStream(file.toPath())){
			try(Response response = clientUtil.newClient(ImageClient.class).findContours(credential.getAuthHeader(), input)){
				response.getStatus();
			}
		}
	}
}
