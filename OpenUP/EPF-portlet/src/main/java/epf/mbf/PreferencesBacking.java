package epf.mbf;

import java.util.Enumeration;
import java.util.Map;
import javax.el.ELResolver;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;
import javax.portlet.faces.preference.Preference;
@RequestScoped
@Named("preferencesBacking")
public class PreferencesBacking {

	/**
	 * Resets/restores the values in the preferences.xhtml Facelet composition with portlet preference default values.
	 */
	public void reset() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
		PortletPreferences portletPreferences = portletRequest.getPreferences();

		try {
			Enumeration<String> preferenceNames = portletPreferences.getNames();

			while (preferenceNames.hasMoreElements()) {
				String preferenceName = preferenceNames.nextElement();
				portletPreferences.reset(preferenceName);
			}

			portletPreferences.store();

			// Switch the portlet mode back to VIEW.
			ActionResponse actionResponse = (ActionResponse) externalContext.getResponse();
			actionResponse.setPortletMode(PortletMode.VIEW);
			actionResponse.setWindowState(WindowState.NORMAL);
		}
		catch (Exception e) {
		}
	}

	/**
	 * Saves the values in the preferences.xhtml Facelet composition as portlet preferences.
	 */
	public void submit() {

		// The JSR 329 specification defines an EL variable named mutablePortletPreferencesValues that is being used in
		// the preferences.xhtml Facelet composition. This object is of type Map<String, Preference> and is
		// designed to be a model managed-bean (in a sense) that contain preference values. However the only way to
		// access this from a Java class is to evaluate an EL expression (effectively self-injecting) the map into
		// this backing bean.
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		String elExpression = "mutablePortletPreferencesValues";
		ELResolver elResolver = facesContext.getApplication().getELResolver();
		@SuppressWarnings("unchecked")
		Map<String, Preference> mutablePreferenceMap = (Map<String, Preference>) elResolver.getValue(
				facesContext.getELContext(), null, elExpression);

		// Get a list of portlet preference names.
		PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
		PortletPreferences portletPreferences = portletRequest.getPreferences();
		Enumeration<String> preferenceNames = portletPreferences.getNames();

		try {

			// For each portlet preference name:
			while (preferenceNames.hasMoreElements()) {

				// Get the value specified by the user.
				String preferenceName = preferenceNames.nextElement();
				String preferenceValue = mutablePreferenceMap.get(preferenceName).getValue();

				// Prepare to save the value.
				if (!portletPreferences.isReadOnly(preferenceName)) {
					portletPreferences.setValue(preferenceName, preferenceValue);
				}
			}

			// Save the preference values.
			portletPreferences.store();

			// Switch the portlet mode back to VIEW.
			ActionResponse actionResponse = (ActionResponse) externalContext.getResponse();
			actionResponse.setPortletMode(PortletMode.VIEW);
			actionResponse.setWindowState(WindowState.NORMAL);

			// Report a successful message back to the user as feedback.
		}
		catch (Exception e) {
		}
	}
}
