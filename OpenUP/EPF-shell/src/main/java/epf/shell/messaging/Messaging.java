package epf.shell.messaging;

import java.io.PrintWriter;
import java.net.URI;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import javax.net.ssl.SSLContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import epf.naming.Naming;
import epf.shell.Function;
import epf.shell.SYSTEM;
import epf.shell.security.SecurityUtil;
import epf.util.ssl.SSLContextUtil;
import io.undertow.websockets.DefaultWebSocketClientSslProvider;
import picocli.CommandLine.Command;

@Command(name = Naming.MESSAGING)
@RequestScoped
@Function
public class Messaging {
	
	@ConfigProperty(name = Naming.Gateway.MESSAGING_URL)
	@Inject
	URI messagingUrl;
	
	@Inject @Named(SYSTEM.OUT)
	transient PrintWriter out;
	
	@Inject @Named(SYSTEM.ERR)
	transient PrintWriter err;
	
	@Inject
	transient SecurityUtil securityUtil;
	
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
}
