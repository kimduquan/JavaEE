package epf.shell.mail;

import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.mail.client.Message;
import epf.naming.Naming;
import epf.shell.Function;
import epf.shell.security.CallerPrincipal;
import epf.shell.security.Credential;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * 
 */
@Command(name = Naming.MAIL)
@RequestScoped
@Function
public class Mail {
	
	/**
	 * 
	 */
	@Inject
	@RestClient
	transient MailClient mail;
	
	/**
	 * @throws Exception 
	 * 
	 */
	@Command(name = "send")
	public void send(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-d", "--desc"}, description = "Description")
			final String description,
			@Option(names = {"-f", "--from"}, description = "From")
			final String from,
			@Option(names = {"-h", "--headers"}, description = "Headers")
			final Map<String, String> header,
			@Option(names = {"-r", "--recipients"}, description = "Recipients")
			final Map<String, String> recipients,
			@Option(names = {"-s", "--subject"}, description = "Subject")
			final String subject,
			@Option(names = {"-text", "--text"}, description = "Text", interactive = true, echo = true)
			final String text) throws Exception {
		final Message message = new Message();
		message.setDescription(description);
		message.setFrom(from);
		message.setHeaders(header);
		message.setRecipients(recipients);
		message.setSubject(subject);
		message.setText(text);
		try(Response response = mail.send(message)){
			response.getStatus();
		}
	}
}
