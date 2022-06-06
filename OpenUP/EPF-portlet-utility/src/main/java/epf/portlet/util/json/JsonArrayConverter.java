package epf.portlet.util.json;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.json.JsonArray;
import epf.util.json.JsonUtil;

/**
 * @author PC
 *
 */
@FacesConverter(managed = true, forClass = JsonArray.class)
public class JsonArrayConverter implements Converter<JsonArray> {

	@Override
	public JsonArray getAsObject(final FacesContext context, final UIComponent component, final String value) {
		return JsonUtil.readArray(value);
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final JsonArray value) {
		return value != null ? value.toString() : null;
	}

}
