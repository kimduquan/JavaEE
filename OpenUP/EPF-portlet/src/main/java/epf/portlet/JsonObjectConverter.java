/**
 * 
 */
package epf.portlet;

import java.io.StringReader;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * @author PC
 *
 */
@FacesConverter(managed = true, forClass = JsonObject.class)
public class JsonObjectConverter implements Converter<JsonObject> {

	@Override
	public JsonObject getAsObject(final FacesContext context, final UIComponent component, final String value) {
		try(StringReader reader = new StringReader(value)){
			try(JsonReader json = Json.createReader(reader)){
				return json.readObject();
			}
		}
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final JsonObject value) {
		return value != null ? value.toString() : null;
	}

}
