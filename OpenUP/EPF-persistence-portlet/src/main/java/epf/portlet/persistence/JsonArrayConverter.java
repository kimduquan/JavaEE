/**
 * 
 */
package epf.portlet.persistence;

import java.io.StringReader;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;

/**
 * @author PC
 *
 */
@FacesConverter(managed = true, forClass = JsonArray.class)
public class JsonArrayConverter implements Converter<JsonArray> {

	@Override
	public JsonArray getAsObject(final FacesContext context, final UIComponent component, final String value) {
		try(StringReader reader = new StringReader(value)){
			try(JsonReader json = Json.createReader(reader)){
				return json.readArray();
			}
		}
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final JsonArray value) {
		return value != null ? value.toString() : null;
	}

}
