package epf.shell.lang;

import java.util.Scanner;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.naming.Naming;
import epf.shell.Function;
import epf.shell.client.ClientUtil;
import epf.shell.security.CallerPrincipal;
import epf.shell.security.Credential;
import epf.shell.stream.StreamClient;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.Session;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;

/**
 * 
 */
@Command(name = Naming.LANG)
@RequestScoped
@Function
public class Lang {

	/**
	 * 
	 */
	@Inject
	transient ClientUtil clientUtil;
	
	/**
	 * 
	 */
	@Inject
	transient StreamClient stream;
	
	/**
	 * 
	 */
	@RestClient
	transient LangClient lang;
	
	/**
	 * @param credential
	 * @throws Exception
	 */
	@Command(name = "send")
	public void send(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential) throws Exception {
		final Session session = ContainerProvider.getWebSocketContainer().connectToServer(stream, clientUtil.getWSUrl("/stream"));
		try(Scanner scanner = new Scanner(System.in)){
			while(scanner.hasNextLine()) {
				final String line = scanner.nextLine();
				if("\\q".equals(line)) {
					session.close();
					break;
				}
				lang.send(session.getId(), line);
			}
		}
	}
}
