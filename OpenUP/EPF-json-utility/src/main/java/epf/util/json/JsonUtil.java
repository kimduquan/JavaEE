package epf.util.json;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

/**
 * @author PC
 *
 */
public interface JsonUtil {

	/**
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	static JsonObject toJson(final Object object) throws Exception {
		try(StringWriter writer = new StringWriter()){
			try(Jsonb jsonb = JsonbBuilder.create()){
				jsonb.toJson(object, writer);
    		}
			try(StringReader reader = new StringReader(writer.toString())){
				JsonReader objectReader = Json.createReader(reader);
				return objectReader.readObject();
			}
		}
	}
	
	/**
	 * @param collection
	 * @return
	 * @throws Exception 
	 */
	static JsonArray toJsonArray(final Collection<?> collection) throws Exception {
		final JsonArrayBuilder builder = Json.createArrayBuilder();
		for(Object object : collection) {
			builder.add(toJson(object));
		}
		return builder.build();
	}
	
	/**
	 * @return
	 */
	static JsonArray emptyArray() {
		return Json.createArrayBuilder().build();
	}
	
	/**
	 * @return
	 */
	static JsonObject empty() {
		return Json.createObjectBuilder().build();
	}
}
