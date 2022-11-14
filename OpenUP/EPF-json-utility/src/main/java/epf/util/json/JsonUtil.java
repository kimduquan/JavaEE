package epf.util.json;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonPatch;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;
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
			return readObject(writer.toString());
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
	
	/**
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	static String toString(final Object object) throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create()){
			return jsonb.toJson(object);
		}
	}
	
	/**
	 * @param input
	 * @return
	 */
	static JsonArray readArray(final InputStream input) {
		try(JsonReader jsonReader = Json.createReader(input)){
			return jsonReader.readArray();
		}
	}
	
	/**
	 * @param input
	 * @return
	 */
	static JsonObject readObject(final InputStream input) {
		try(JsonReader jsonReader = Json.createReader(input)){
			return jsonReader.readObject();
		}
	}
	
	/**
	 * @param reader
	 * @return
	 */
	static JsonArray readArray(final Reader reader) {
		try(JsonReader jsonReader = Json.createReader(reader)){
			return jsonReader.readArray();
		}
	}
	
	/**
	 * @param reader
	 * @return
	 */
	static JsonObject readObject(final Reader reader) {
		try(JsonReader jsonReader = Json.createReader(reader)){
			return jsonReader.readObject();
		}
	}
	
	/**
	 * @param input
	 * @return
	 */
	static JsonArray readArray(final String input) {
		try(StringReader reader = new StringReader(input)){
			return readArray(reader);
		}
	}
	
	/**
	 * @param input
	 * @return
	 */
	static JsonObject readObject(final String input) {
		try(StringReader reader = new StringReader(input)){
			return readObject(reader);
		}
	}
	
	/**
	 * @param input
	 * @return
	 */
	static JsonValue readValue(final String input) {
		try(StringReader reader = new StringReader(input)){
			return readValue(reader);
		}
	}
	
	/**
	 * @param input
	 * @return
	 */
	static JsonValue readValue(final InputStream input) {
		try(JsonReader jsonReader = Json.createReader(input)){
			return jsonReader.readValue();
		}
	}
	
	/**
	 * @param reader
	 * @return
	 */
	static JsonValue readValue(final Reader reader) {
		try(JsonReader jsonReader = Json.createReader(reader)){
			return jsonReader.readValue();
		}
	}
	
	/**
	 * @param value
	 * @return
	 */
	static String toString(final JsonValue value) {
		String string = null;
		if(value instanceof JsonString) {
			string = ((JsonString)value).getString();
		}
		else if(value != null){
			string = value.toString();
		}
		return string;
	}
	
	/**
	 * @param object
	 * @param attribute
	 * @param def
	 * @return
	 */
	static String getString(final JsonObject object, final String attribute, final String def) {
		final JsonValue value = object.get(attribute);
		String string = def;
		if(value != null) {
			string = value.toString();
			switch(value.getValueType()) {
				case NULL:
					string = def;
					break;
				case STRING:
					string = object.getString(attribute);
				default:
					break;
				
			}
		}
		return string;
	}
	
	/**
	 * @param source
	 * @param target
	 * @return
	 * @throws Exception
	 */
	static JsonPatch createDiff(final Object source, final Object target) throws Exception {
		return Json.createDiff(toJson(source), toJson(target));
	}
	
	/**
	 * @param <T>
	 * @param cls
	 * @param input
	 * @return
	 * @throws Exception
	 */
	static <T> T fromJson(final Class<T> cls, final InputStream input) throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create()){
			return jsonb.fromJson(input, cls);
		}
	}
}
