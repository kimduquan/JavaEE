package epf.util.json;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Function;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonMergePatch;
import javax.json.JsonNumber;
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
	static JsonObject toJsonObject(final Object object) throws Exception {
		try(StringWriter writer = new StringWriter()){
			try(Jsonb jsonb = JsonbBuilder.create()){
				jsonb.toJson(object, writer);
    		}
			return readObject(writer.toString());
		}
	}
	
	/**
	 * @param object
	 * @return
	 * @throws Exception
	 */
	static JsonValue toJsonValue(final Object object) throws Exception {
		if(object != null) {
			try(StringWriter writer = new StringWriter()){
				try(Jsonb jsonb = JsonbBuilder.create()){
					jsonb.toJson(object, writer);
	    		}
				return readValue(writer.toString());
			}
		}
		return JsonValue.NULL;
	}
	
	/**
	 * @param <T>
	 * @param e
	 * @return
	 * @throws Exception
	 */
	static <T extends Enum<T>> JsonValue toJsonEnum(final Enum<T> e) throws Exception {
		try(StringWriter writer = new StringWriter()){
			try(Jsonb jsonb = JsonbBuilder.create()){
				jsonb.toJson(e.name(), writer);
    		}
			return readValue(writer.toString());
		}
	}
	
	/**
	 * @param <T>
	 * @param cls
	 * @param value
	 * @return
	 */
	static <T extends Enum<T>> T asEnum(final Class<T> cls, final JsonValue value) {
		final String name = ((JsonString)value).getString();
		for(T e : cls.getEnumConstants()) {
			if(e.name().equals(name) || e.name().toLowerCase().equals(name)) {
				return e;
			}
		}
		return null;
	}
	
	/**
	 * @param collection
	 * @return
	 * @throws Exception 
	 */
	static JsonArray toJsonArray(final Collection<?> collection) throws Exception {
		final JsonArrayBuilder builder = Json.createArrayBuilder();
		for(Object object : collection) {
			builder.add(toJsonValue(object));
		}
		return builder.build();
	}
	
	/**
	 * @param <T>
	 * @param array
	 * @return
	 * @throws Exception
	 */
	static <T> JsonArray toJsonArray(final T[] array) throws Exception {
		final JsonArrayBuilder builder = Json.createArrayBuilder();
		for(T object : array) {
			builder.add(toJsonValue(object));
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
		return Json.createDiff(toJsonObject(source), toJsonObject(target));
	}
	
	/**
	 * @param <T>
	 * @param input
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	static <T> T fromJson(final InputStream input, final Class<T> cls) throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create()){
			return jsonb.fromJson(input, cls);
		}
	}
	
	/**
	 * @param <T>
	 * @param input
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	static <T> T fromJson(final String input, final Class<T> cls) throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create()){
			return jsonb.fromJson(input, cls);
		}
	}
	
	/**
	 * @param map
	 * @param jsonObject
	 */
	static void asMap(final Map<String, Object> map, final JsonObject jsonObject) {
		for(Entry<String, JsonValue> entry : jsonObject.entrySet()) {
			map.put(entry.getKey(), asValue(entry.getValue()));
		}
	}
	
	/**
	 * @param jsonObject
	 * @return
	 */
	static Map<String, Object> asMap(final JsonObject jsonObject) {
		final Map<String, Object> map = new HashMap<>();
		asMap(map, jsonObject);
		return map;
	}
	
	/**
	 * @param jsonValue
	 * @return
	 */
	static Object asValue(final JsonValue jsonValue) {
		if(jsonValue != null) {
			switch(jsonValue.getValueType()) {
				case ARRAY:
					final List<Object> list = asList(jsonValue.asJsonArray());
					return list;
				case FALSE:
					return false;
				case NULL:
					return null;
				case NUMBER:
					final JsonNumber number = (JsonNumber) jsonValue;
					return number.numberValue();
				case OBJECT:
					final Map<String, Object> map = new HashMap<String, Object>();
					asMap(map, jsonValue.asJsonObject());
					return map;
				case STRING:
					return ((JsonString)jsonValue).getString();
				case TRUE:
					return true;
				default:
					break;
			}
		}
		return null;
	}
	
	/**
	 * @param jsonArray
	 * @return
	 */
	static List<Object> asList(final JsonArray jsonArray) {
		final List<Object> list = new ArrayList<>();
		jsonArray.forEach(jsonValue -> list.add(asValue(jsonValue)));
		return list;
	}
	
	/**
	 * @param <T>
	 * @param array
	 * @return
	 * @throws Exception 
	 */
	static <T> List<Object> toList(final T[] array) throws Exception {
		final JsonArray jsonArray = toJsonArray(array);
		return asList(jsonArray);
	}
	
	/**
	 * @param <T>
	 * @param map
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	static <T> T fromMap(final Map<String, Object> map, final Class<T> cls) throws Exception {
		Objects.requireNonNull(map, "Map");
		Objects.requireNonNull(cls, "Class");
		try(Jsonb jsonb = JsonbBuilder.create()){
			final String json = jsonb.toJson(map);
			return jsonb.fromJson(json, cls);
		}
	}
	
	/**
	 * @param <T>
	 * @param list
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	static <T> List<T> fromLisṭ̣(final List<Object> list, final Class<T> cls) throws Exception{
		Objects.requireNonNull(list, "List");
		Objects.requireNonNull(cls, "Class");
		final List<T> result = new ArrayList<>();
		try(Jsonb jsonb = JsonbBuilder.create()){
			for(Object object : list) {
				if(object instanceof Map) {
					@SuppressWarnings("unchecked")
					final Map<String, Object> map = (Map<String, Object>) object;
					final T t = fromMap(map, cls);
					result.add(t);
				}
				else {
					result.add(null);
				}
			}
		}
		return result;
	}
	
	/**
	 * @param <T>
	 * @param list
	 * @param convert
	 * @return
	 * @throws Exception
	 */
	static <T> List<T> fromLisṭ̣(final List<Object> list, final Function<Map<String, Object>, T> convert) throws Exception{
		Objects.requireNonNull(list, "List");
		Objects.requireNonNull(convert, "Function");
		final List<T> result = new ArrayList<>();
		try(Jsonb jsonb = JsonbBuilder.create()){
			for(Object object : list) {
				if(object instanceof Map) {
					@SuppressWarnings("unchecked")
					final Map<String, Object> map = (Map<String, Object>) object;
					final T t = convert.apply(map);
					result.add(t);
				}
				else {
					result.add(null);
				}
			}
		}
		return result;
	}
	
	/**
	 * @param <T>
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	static <T> Map<String, Object> toMap(final T obj) throws Exception {
		Objects.requireNonNull(obj, "Object");
		final Map<String, Object> map = new HashMap<>();
		asMap(map, toJsonObject(obj));
		return map;
	}
	
	/**
	 * @param source
	 * @param target
	 * @return
	 * @throws Exception
	 */
	static JsonMergePatch createMergeDiff(final Map<String, Object> source, final Map<String, Object> target) throws Exception{
		return Json.createMergeDiff(toJsonValue(source), toJsonValue(target));
	}
}
