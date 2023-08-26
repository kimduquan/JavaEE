package epf.tests.portlet;

import java.net.MalformedURLException;
import java.net.URL;
import epf.portlet.naming.Naming;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

public class PortletUtil {
	
	@Produces @Named(Naming.PORTLET_URL)
	public static URL getPortletURL() {
		URL url = null;
		try {
			url = new URL(System.getProperty(Naming.PORTLET_URL, "http://localhost:8080/pluto/portal/"));
		} 
		catch (MalformedURLException e) {
			
		}
		return url;
	}

}
