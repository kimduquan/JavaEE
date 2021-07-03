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
@FacesConverter(value = "PasswordConverter", managed = true)
public class PasswordConverter implements Converter<char[]> {

	@Override
	public char[] getAsObject(final FacesContext context, final UIComponent component, final String value) {
		return value != null ? value.toCharArray() : null;
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final char[] value) {
		return value != null ? new String(value) : null;
	}
}
