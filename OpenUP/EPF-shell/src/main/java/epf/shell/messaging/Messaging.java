package epf.shell.messaging;

import java.io.PrintWriter;
import java.net.URI;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.net.ssl.SSLContext;
import javax.validation.constraints.NotBlank;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import epf.messaging.client.Client;
import epf.naming.Naming;
import epf.shell.Function;
import epf.shell.SYSTEM;
import epf.shell.security.CallerPrincipal;
import epf.shell.security.Credential;
import epf.shell.security.SecurityUtil;
import epf.util.ssl.SSLContextUtil;
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
			@Option(names = {"-tn", "--tenant"}, description = "Tenant")
			final String tenant,
			@Option(names = {"-s", "--service"}, required = true, description = "Service")
			@NotBlank
			final String service,
			@Option(names = {"-p", "--path"}, description = "Path")
			final String path) throws Exception {
		URI url;
		if(path != null) {
			final String[] paths = path.split("/");
			url = epf.messaging.client.Messaging.getUrl(messagingUrl, Optional.ofNullable(tenant), service, Optional.of(credential.getRawToken()), paths);
		}
		else {
			url = epf.messaging.client.Messaging.getUrl(messagingUrl, Optional.ofNullable(tenant), service, Optional.of(credential.getRawToken()));
		}
		final Client client = epf.messaging.client.Messaging.connectToServer(url, MessagingClient.class);
		out.println(client.getSession().getId());
		System.in.read();
		client.close();
	}
}
