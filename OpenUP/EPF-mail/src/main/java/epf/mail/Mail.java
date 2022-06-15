package epf.mail;

import java.util.Map.Entry;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import epf.client.mail.AttachFile;
import epf.client.mail.Message;
import epf.client.mail.MessageBodyPart;
import epf.client.mail.MessageMultipart;
import epf.client.mail.MessagePart;
import epf.naming.Naming;
import epf.naming.Naming.Security;

/**
 * 
 */
@Path(Naming.MAIL)
@RolesAllowed({Security.DEFAULT_ROLE, Naming.EPF})
@ApplicationScoped
public class Mail implements epf.client.mail.Mail {
	
	/**
	 *
	 */
	@Resource(name = "java:comp/env/mail/gmailSMTPSession")
	private transient Session session;
	
	/**
	 * @param email
	 * @param type
	 * @param addresses
	 * @throws Exception
	 */
	private void addRecipients(final MimeMessage email, final String type, final String addresses) throws Exception {
		switch (type) {
			case Message.TO:
				email.addRecipients(RecipientType.TO, addresses);
				break;
			case Message.BCC:
				email.addRecipients(RecipientType.BCC, addresses);
				break;
			case Message.CC:
				email.addRecipients(RecipientType.CC, addresses);
				break;
		}
	}
	
	/**
	 * @param mimePart
	 * @param part
	 * @throws Exception
	 */
	private void buildPart(final MimePart mimePart, final MessagePart part) throws Exception {
		if(part.getHeaders() != null) {
			for(Entry<String, String> header : part.getHeaders().entrySet()) {
				mimePart.addHeader(header.getKey(), header.getValue());
			}
		}
		if(part.getHeaderLines() != null) {
			for(String line : part.getHeaderLines()) {
				mimePart.addHeaderLine(line);
			}
		}
		if(part.getDescription() != null) {
			mimePart.setDescription(part.getDescription());
		}
		if(part.getText() != null) {
			mimePart.setText(part.getText());
		}
	}
	
	/**
	 * @param bodyPart
	 * @param part
	 * @throws Exception
	 */
	private void buildBodyPart(final MimeBodyPart bodyPart, final MessageBodyPart part) throws Exception {
		if(part.getAttachFiles() != null) {
			for(AttachFile file : part.getAttachFiles()) {
				bodyPart.attachFile(file.getFile(), file.getContentType(), file.getEncoding());
			}
		}
		buildPart(bodyPart, part);
	}
	
	/**
	 * @param mimeMultipart
	 * @param multipart
	 * @throws Exception
	 */
	private void buildMultipart(final MimeMultipart mimeMultipart, final MessageMultipart multipart) throws Exception {
		if(multipart.getBodyParts() != null) {
			for(MessageBodyPart part : multipart.getBodyParts()) {
				final MimeBodyPart bodyPart = new MimeBodyPart();
				buildBodyPart(bodyPart, part);
				mimeMultipart.addBodyPart(bodyPart);
			}
		}
	}
	
	/**
	 * @param email
	 * @param message
	 * @throws Exception
	 */
	private void buildMessage(final MimeMessage email, final Message message) throws Exception {
		if(message.getFrom() != null) {
			email.setFrom(message.getFrom());
		}
		if(message.getSubject() != null) {
			email.setSubject(message.getSubject());
		}
		if(message.getRecipients() != null) {
			for(Entry<String, String> recipients : message.getRecipients().entrySet()) {
				addRecipients(email, recipients.getKey(), recipients.getValue());
			}
		}
		if(message.getContent() != null) {
			final MimeMultipart content = new MimeMultipart();
			buildMultipart(content, message.getContent());
			email.setContent(content);
		}
		buildPart(email, message);
	}

	@Override
	public Response send(final Message message) throws Exception {
		final MimeMessage email = new MimeMessage(session);
		buildMessage(email, message);
		Transport.send(email);
		return Response.ok().build();
	}
}
