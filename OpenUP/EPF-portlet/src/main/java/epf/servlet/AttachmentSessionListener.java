package epf.servlet;

import java.io.File;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import epf.util.logging.Logging;

@WebListener
public class AttachmentSessionListener implements HttpSessionListener {

	private static final Logger logger = Logging.getLogger(AttachmentSessionListener.class.getName());

	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

		HttpSession httpSession = httpSessionEvent.getSession();
		ServletContext servletContext = httpSession.getServletContext();
		File tempDir = (File) servletContext.getAttribute(ServletContext.TEMPDIR);

		File attachmentDir = new File(tempDir, httpSession.getId());

		if (attachmentDir.exists()) {

			File[] files = attachmentDir.listFiles();

			for (File file : files) {

				file.delete();

				logger.severe("Deleted file: " + file.getAbsolutePath());
			}
		}
	}
}
