/**
 * 
 */
package epf.portlet.util.net;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@FacesConverter(forClass=URL.class)
public class URLConverter implements Converter<URL> {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(URLConverter.class.getName());

	@Override
	public URL getAsObject(final FacesContext context, final UIComponent component, final String value) {
		URL url = null;
		if(value != null) {
			try {
				url = new URL(value);
			} 
			catch (MalformedURLException e) {
				LOGGER.throwing(getClass().getName(), "getAsObject", e);
			}
		}
		return url;
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final URL value) {
		return value != null ? value.toString() : null;
	}

}
