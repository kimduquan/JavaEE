package epf.tests.webapp;

import java.net.URL;
import epf.naming.Naming;
import epf.util.config.ConfigUtil;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

public class WebAppUtil {

	@Produces @Named(Naming.WebApp.WEB_APP_URL)
	public static URL getPortletURL() {
		try {
			return ConfigUtil.getURL(Naming.WebApp.WEB_APP_URL);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
