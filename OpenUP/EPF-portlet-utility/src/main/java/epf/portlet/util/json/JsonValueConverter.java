package epf.portlet.util.json;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.json.JsonValue;
import epf.util.json.JsonUtil;

/**
 * @author PC
 *
 */
@FacesConverter(managed = true, forClass = JsonValue.class)
public class JsonValueConverter implements Converter<JsonValue> {

	@Override
	public JsonValue getAsObject(final FacesContext context, final UIComponent component, final String value) {
		return JsonUtil.readValue(value);
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final JsonValue value) {
		return JsonUtil.toString(value);
	}

}
