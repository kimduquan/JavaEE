/**
 * 
 */
package epf.shell.file;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.ws.rs.core.Response;
import epf.client.gateway.GatewayUtil;
import epf.shell.Function;
import epf.shell.client.ClientUtil;
import epf.shell.security.Credential;
import epf.shell.security.CallerPrincipal;
import epf.util.client.Client;
import epf.util.io.IOUtil;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * @author PC
 *
 */
@Command(name = "file")
@RequestScoped
@Function
public class FileStore {
	
	/**
	 * 
	 */
	@Inject
	private transient ClientUtil clientUtil;
	
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
		try(Client client = clientUtil.newClient(GatewayUtil.get("file"))){
			client.authorization(credential.getToken());
			try(InputStream input = Files.newInputStream(file.toPath())){
				try(Response res = epf.client.file.Files.createFile(client, input, path)){
					res.getStatus();
					return res.getLink("self").getTitle();
				}
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
		try(Client client = clientUtil.newClient(GatewayUtil.get("file"))){
			client.authorization(credential.getToken());
			try(InputStream in = epf.client.file.Files.read(client, path)){
				try(OutputStream out = Files.newOutputStream(output.toPath())){
					IOUtil.transferTo(in, out);
				}
			}
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
		try(Client client = clientUtil.newClient(GatewayUtil.get("file"))){
			client.authorization(credential.getToken());
			try(Response response = epf.client.file.Files.delete(client, path)){
				response.getStatus();
			}
		}
	}
}
