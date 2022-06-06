package epf.portlet.util.json;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.json.JsonObject;
import epf.util.json.JsonUtil;

/**
 * @author PC
 *
 */
@FacesConverter(managed = true, forClass = JsonObject.class)
public class JsonObjectConverter implements Converter<JsonObject> {

	@Override
	public JsonObject getAsObject(final FacesContext context, final UIComponent component, final String value) {
		return JsonUtil.readObject(value);
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final JsonObject value) {
		return value != null ? value.toString() : null;
	}

}
