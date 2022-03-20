package epf.tests.webapp;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import epf.naming.Naming;
import epf.util.config.ConfigUtil;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

public class WebAppUtil {

	@Produces @Named(Naming.WebApp.WEB_APP_URL)
	public static URL getPortletURL() {
		try {
			return ConfigUtil.getURI(Naming.WebApp.WEB_APP_URL).toURL();
		} 
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
