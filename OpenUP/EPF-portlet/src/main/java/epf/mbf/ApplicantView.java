package epf.mbf;

import java.io.Serializable;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;


/**
 * This is a JSF view managed-bean for the applicant.xhtml composition.
 *
 * @author  Kyle Stiemann
 */
@ViewScoped
@Named("applicantView")
public class ApplicantView implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 6063667782815767889L;

	// JavaBean Properties for UI
	private boolean commentsRendered;

	public boolean isCommentsRendered() {
		return commentsRendered;
	}

	public void setCommentsRendered(boolean commentsRendered) {
		this.commentsRendered = commentsRendered;
	}

	public void toggleComments(ActionEvent actionEvent) {
		commentsRendered = !commentsRendered;
	}

}
