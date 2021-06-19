/**
 * 
 */
package epf.portlet;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
@FacesConverter(forClass=URL.class)
public class URLConverter implements Converter {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(URLConverter.class.getName());

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		URL url = null;
		try {
			url = new URL(value);
		} 
		catch (MalformedURLException e) {
			LOGGER.throwing(getClass().getName(), "getAsObject", e);
		}
		return url;
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		return value != null ? value.toString() : null;
	}

}
