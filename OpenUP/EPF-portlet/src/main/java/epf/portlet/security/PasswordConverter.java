/**
 * 
 */
package epf.portlet.security;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * @author PC
 *
 */
@FacesConverter(value = "PasswordConverter")
public class PasswordConverter implements Converter {

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		return value.toCharArray();
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		return ((char[])value).toString();
	}

}
