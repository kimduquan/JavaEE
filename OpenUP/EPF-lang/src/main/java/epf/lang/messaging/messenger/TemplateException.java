package epf.lang.messaging.messenger;

import java.io.IOException;

public class TemplateException extends IOException {

	private static final long serialVersionUID = 1L;
	
	private  final String context;
	
	public TemplateException(String context) {
		super("Could not resovle attribute \"" + context +"\"");
		this.context = context;
	}

	public String getContext() {
		return context;
	}
}
