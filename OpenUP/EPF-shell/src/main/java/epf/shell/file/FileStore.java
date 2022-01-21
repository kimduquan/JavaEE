/**
 * 
 */
package epf.shell.file;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import epf.file.util.PathUtil;
import epf.naming.Naming;
import epf.shell.Function;
import epf.shell.security.Credential;
import epf.shell.security.CallerPrincipal;
import javax.enterprise.context.RequestScoped;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * @author PC
 *
 */
@Command(name = Naming.FILE)
@RequestScoped
@Function
public class FileStore {
	
	/**
	 * 
	 */
	@ConfigProperty(name = Naming.Client.CLIENT_CONFIG + "/mp-rest/uri")
	String gatewayUrl;
	
	/**
	 * @param path
	 * @return
	 * @throws Exception
	 */
	protected FilesClient buildClient(final Path path) throws Exception {
		final URI baseUrl = new URI(gatewayUrl + Naming.FILE + "/" + PathUtil.toURI(path));
		System.out.println(baseUrl.toString());
		final FilesClient files = RestClientBuilder.newBuilder().baseUrl(baseUrl.toURL()).build(FilesClient.class);
		return files;
	}
	
	/**
	 * @param token
	 * @param file
	 * @param path
	 * @return
	 * @throws Exception
	 */
	@Command(name = "create")
	public String createFile(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-f", "--file"}, description = "File")
			final File file,
			@Option(names = {"-p", "--path"}, description = "Path")
			final Path path) throws Exception {
		final FilesClient files = buildClient(path);
		try(InputStream input = Files.newInputStream(file.toPath())){
			try(Response res = files.createFile(credential.getAuthHeader(), input)){
				res.getStatus();
				return res.getLink("self").getTitle();
			}
		}
	}
	
	/**
	 * @param token
	 * @param path
	 * @param output
	 * @throws Exception
	 */
	@Command(name = "read")
	public void read(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-p", "--path"}, description = "Path")
			final Path path,
			@Option(names = {"-o", "--output"}, description = "Output")
			final File output
			) throws Exception {
		final FilesClient files = buildClient(path);
		final StreamingOutput stream = files.read(credential.getAuthHeader());
		try(OutputStream outputStream = Files.newOutputStream(output.toPath(), StandardOpenOption.TRUNCATE_EXISTING)){
			stream.write(outputStream);
		}
	}
	
	/**
	 * @param token
	 * @param path
	 * @throws Exception
	 */
	@Command(name = "delete")
	public void delete(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-p", "--path"}, description = "Path")
			final Path path
			) throws Exception {
		final FilesClient files = buildClient(path);
		try(Response response = files.delete(credential.getAuthHeader())){
			response.getStatus();
		}
	}
}
