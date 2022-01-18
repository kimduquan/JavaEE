/**
 * 
 */
package epf.shell.file;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.naming.Naming;
import epf.shell.Function;
import epf.shell.security.Credential;
import epf.shell.security.CallerPrincipal;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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
	@Inject
	@RestClient
	transient FilesClient files;
	
	/**
	 * @param path
	 * @return
	 */
	protected List<String> toList(final Path path){
		final List<String> paths = new ArrayList<>();
		final Iterator<Path> it = path.iterator();
		while(it.hasNext()) {
			paths.add(it.next().toString());
		}
		return paths;
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
		try(InputStream input = Files.newInputStream(file.toPath())){
			try(Response res = files.createFile(credential.getAuthHeader(), toList(file.toPath()), input)){
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
		final StreamingOutput stream = files.read(credential.getAuthHeader(), toList(path));
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
		try(Response response = files.delete(credential.getAuthHeader(), toList(path))){
			response.getStatus();
		}
	}
}
