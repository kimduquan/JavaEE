package epf.mbf;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.portlet.PortletContext;
import javax.servlet.ServletContext;
import epf.dto.Attachment;
@ApplicationScoped
@Named("attachmentManager")
public class AttachmentManager {

	public File getAttachmentDir(FacesContext facesContext) {

		ExternalContext externalContext = facesContext.getExternalContext();
		String sessionId = externalContext.getSessionId(true);
		PortletContext portletContext = (PortletContext) externalContext.getContext();
		File tempDir = (File) portletContext.getAttribute(ServletContext.TEMPDIR);

		return new File(tempDir, sessionId);
	}

	public List<Attachment> getAttachments(File attachmentDir) {

		List<Attachment> attachments = new ArrayList<>();

		if (attachmentDir.exists()) {

			File[] files = attachmentDir.listFiles();

			for (int i = 0; i < files.length; i++) {
				attachments.add(new Attachment(files[i], i));
			}
		}

		return attachments;
	}
}
