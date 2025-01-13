package epf.lang.messaging.messenger;

import java.io.IOException;

public class TemplateException extends IOException {

	private static final long serialVersionUID = 1L;
	
	private  final Object context;
	
	public TemplateException(Object context) {
		super("Could not resovle attribute \"" + context +"\"");
		this.context = context;
	}

	public Object getContext() {
		return context;
	}
}
