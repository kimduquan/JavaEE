package epf.mbf;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import javax.servlet.http.Part;
import epf.dto.Applicant;
import epf.dto.Attachment;
import epf.dto.City;
import epf.util.PartUtil;
import epf.util.logging.Logging;
import javax.faces.annotation.ManagedProperty;

@RequestScoped
@Named("applicantBacking")
public class ApplicantBacking {

	// Logger
	private static final Logger logger = Logging.getLogger(ApplicantBacking.class.getName());

	// Injections
	@ManagedProperty(value = "#{attachmentManager}")
	private AttachmentManager attachmentManager;
	@ManagedProperty(value = "#{listManager}")
	private ListManager listManager;

	// Private Data Members
	private Applicant applicant;
	private Part uploadedPart;

	public void deleteUploadedFile(ActionEvent actionEvent) {

		UICommand uiCommand = (UICommand) actionEvent.getComponent();
		int attachmentIndex = (Integer) uiCommand.getValue();

		try {
			List<Attachment> attachments = applicant.getAttachments();

			Attachment attachmentToDelete = null;

			for (Attachment attachment : attachments) {

				if (attachment.getIndex() == attachmentIndex) {
					attachmentToDelete = attachment;

					break;
				}
			}

			if (attachmentToDelete != null) {
				attachmentToDelete.getFile().delete();
				attachments.remove(attachmentToDelete);
			}
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public Applicant getModel() {
		return applicant;
	}

	public Part getUploadedPart() {
		return uploadedPart;
	}

	public void postalCodeListener(ValueChangeEvent valueChangeEvent) {

		try {
			String newPostalCode = (String) valueChangeEvent.getNewValue();
			City city = listManager.getCityByPostalCode(newPostalCode);

			if (city != null) {
				applicant.setAutoFillCity(city.getCityName());
				applicant.setAutoFillProvinceId(city.getProvinceId());
			}
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@PostConstruct
	public void postConstruct() {
		applicant = new Applicant();

		FacesContext facesContext = FacesContext.getCurrentInstance();
		File attachmentDir = attachmentManager.getAttachmentDir(facesContext);
		List<Attachment> attachments = attachmentManager.getAttachments(attachmentDir);
		applicant.setAttachments(attachments);
	}

	public void setApplicant(Applicant applicant) {

		// Injected via @ManagedProperty annotation
		this.applicant = applicant;
	}

	public void setAttachmentManager(AttachmentManager attachmentManager) {

		// Injected via @ManagedProperty annotation
		this.attachmentManager = attachmentManager;
	}

	public void setListManager(ListManager listManager) {

		// Injected via @ManagedProperty annotation
		this.listManager = listManager;
	}

	public void setUploadedPart(Part uploadedPart) {
		this.uploadedPart = uploadedPart;

		FacesContext facesContext = FacesContext.getCurrentInstance();
		File attachmentDir = attachmentManager.getAttachmentDir(facesContext);

		if (!attachmentDir.exists()) {
			attachmentDir.mkdir();
		}

		File copiedFile = new File(attachmentDir, PartUtil.getFileName(uploadedPart));

		try {
			uploadedPart.write(copiedFile.getAbsolutePath());
			uploadedPart.delete();

			List<Attachment> attachments = attachmentManager.getAttachments(attachmentDir);
			applicant.setAttachments(attachments);
		}
		catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public String submit() {

		// Delete the uploaded files.
		try {
			List<Attachment> attachments = applicant.getAttachments();

			for (Attachment attachment : attachments) {
				attachment.getFile().delete();
			}

			// Store the applicant's first name in JSF 2 Flash Scope so that it can be picked up
			// for use inside of confirmation.xhtml
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.getExternalContext().getFlash().put("firstName", applicant.getFirstName());

			applicant.clearProperties();

			return "success";

		}
		catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);

			return "failure";
		}
	}
}
