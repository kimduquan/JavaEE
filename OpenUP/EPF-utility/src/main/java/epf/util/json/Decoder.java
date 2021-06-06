/**
 * 
 */
package epf.util.json;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.bind.Jsonb;

/**
 * @author PC
 *
 */
public class Decoder {

	/**
	 * @param jsonb
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public Object decode(final Jsonb jsonb, final String string) throws Exception {
		try(StringReader reader = new StringReader(string)){
			try(JsonReader jsonReader = Json.createReader(reader)){
				final JsonObject jsonObject = jsonReader.readObject();
				final String className = jsonObject.getString("class");
				final Class<?> cls = Class.forName(className);
				return jsonb.fromJson(string, cls);
			}
		}
	}
}
