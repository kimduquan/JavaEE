package epf.shell.messaging;

import java.io.PrintWriter;
import java.net.URI;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.net.ssl.SSLContext;
import javax.validation.constraints.NotBlank;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import epf.client.util.ssl.SSLContextUtil;
import epf.naming.Naming;
import epf.shell.Function;
import epf.shell.SYSTEM;
import epf.shell.security.CallerPrincipal;
import epf.shell.security.Credential;
import epf.shell.security.SecurityUtil;
import epf.util.StringUtil;
import io.undertow.websockets.DefaultWebSocketClientSslProvider;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * @author PC
 *
 */
@Command(name = Naming.MESSAGING)
@RequestScoped
@Function
public class Messaging {
	
	/**
	 *
	 */
	@ConfigProperty(name = Naming.Gateway.MESSAGING_URL)
	@Inject
	URI messagingUrl;
	
	/**
	 * 
	 */
	@Inject @Named(SYSTEM.OUT)
	transient PrintWriter out;
	
	/**
	 * 
	 */
	@Inject @Named(SYSTEM.ERR)
	transient PrintWriter err;
	
	/**
	 *
	 */
	@Inject
	transient SecurityUtil securityUtil;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			final SSLContext ssl = SSLContextUtil.getDefault(SSLContextUtil.TLS, securityUtil.getKeyStore(), securityUtil.getKeyStorePassword());
			DefaultWebSocketClientSslProvider.setSslContext(ssl);
		} 
		catch (Exception e) {
			e.printStackTrace(err);
		}
	}
	
	/**
	 * @param credential
	 * @throws Exception
	 */
	@Command(name = "connect")
	public void connectToServer(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-p", "--path"}, required = true, description = "Path")
			@NotBlank
			final String path) throws Exception {
		final Session session = ContainerProvider.getWebSocketContainer().connectToServer(MessagingClient.class, messagingUrl.resolve(path + "?token=" + StringUtil.encodeURL(credential.getToken())));
		System.in.read();
		session.close();
	}
}
