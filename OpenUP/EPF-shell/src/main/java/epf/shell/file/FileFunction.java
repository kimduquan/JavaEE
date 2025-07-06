package epf.shell.file;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import jakarta.ws.rs.core.Response;
import epf.client.internal.ClientUtil;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.shell.Function;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "file")
@RequestScoped
@Function
public class FileFunction {

	@ConfigProperty(name = Naming.Gateway.GATEWAY_URL)
	@Inject
	private transient URI fileUrl;
	
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
		try(Client client = clientUtil.newClient(fileUrl)){
			try(InputStream input = Files.newInputStream(file.toPath())){
				final Response res = epf.file.client.Files.createFile(client, input, path);
				return res.getLink("self").getUri();
			}
		}
	}
}
