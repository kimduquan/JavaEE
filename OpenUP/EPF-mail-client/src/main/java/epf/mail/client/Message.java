package epf.mail.client;

import java.util.Map;

public class Message extends MessagePart {
	
	public static final String TO = "To";
	
	public static final String CC = "Cc";
	
	public static final String BCC = "Bcc";
	
	private String from;
	
	private String subject;
	
	private Map<String, String> recipients;
	
	private MessageMultipart content;

	public String getFrom() {
		return from;
	}
	public void setFrom(final String from) {
		this.from = from;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(final String subject) {
		this.subject = subject;
	}
	public Map<String, String> getRecipients() {
		return recipients;
	}
	public void setRecipients(final Map<String, String> recipients) {
		this.recipients = recipients;
	}
	public MessageMultipart getContent() {
		return content;
	}
	public void setContent(final MessageMultipart content) {
		this.content = content;
	}
}
