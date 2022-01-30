/**
 * 
 */
package epf.portlet.util.json;

import java.io.StringReader;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonValue;

/**
 * @author PC
 *
 */
@FacesConverter(managed = true, forClass = JsonValue.class)
public class JsonValueConverter implements Converter<JsonValue> {

	@Override
	public JsonValue getAsObject(final FacesContext context, final UIComponent component, final String value) {
		try(StringReader reader = new StringReader(value)){
			try(JsonReader json = Json.createReader(reader)){
				return json.readValue();
			}
		}
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final JsonValue value) {
		return JsonUtil.toString(value);
	}

}
