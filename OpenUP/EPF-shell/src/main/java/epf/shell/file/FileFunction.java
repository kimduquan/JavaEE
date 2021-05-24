/**
 * 
 */
package epf.shell.file;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
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
@Command(name = "file")
@RequestScoped
@Function
public class FileFunction {

	/**
	 * 
	 */
	@Inject @Named(epf.client.file.Files.FILE_URL)
	private transient Var<URI> fileUrl;
	
	/**
	 * 
	 */
	@Inject
	private transient ClientUtil clientUtil;
	
	@Command(name = "create")
	public URI createFile(
			@Option(names = {"-t", "--token"}, description = "Token") 
			final String token,
			@Option(names = {"-f", "--file"}, description = "File")
			final File file,
			@Option(names = {"-p", "--path"}, description = "Path")
			final Path path) throws Exception {
		try(Client client = clientUtil.newClient(fileUrl.get())){
			try(InputStream input = Files.newInputStream(file.toPath())){
				final Response res = epf.client.file.Files.createFile(client, input, path);
				return res.getLink("self").getUri();
			}
		}
	}
}
