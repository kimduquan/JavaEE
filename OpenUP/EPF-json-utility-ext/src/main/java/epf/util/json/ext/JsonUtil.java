package epf.util.json.ext;

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
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonMergePatch;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonPatch;
import jakarta.json.JsonReader;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;

public interface JsonUtil {

	static JsonObject toJsonObject(final Object object) throws Exception {
		try(StringWriter writer = new StringWriter()){
			try(Jsonb jsonb = JsonbBuilder.create()){
				jsonb.toJson(object, writer);
    		}
			return readObject(writer.toString());
		}
	}
	
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
	
	static <T extends Enum<T>> JsonValue toJsonEnum(final Enum<T> e) throws Exception {
		try(StringWriter writer = new StringWriter()){
			try(Jsonb jsonb = JsonbBuilder.create()){
				jsonb.toJson(e.name(), writer);
    		}
			return readValue(writer.toString());
		}
	}
	
	static <T extends Enum<T>> T asEnum(final Class<T> cls, final JsonValue value) {
		final String name = ((JsonString)value).getString();
		for(T e : cls.getEnumConstants()) {
			if(e.name().equals(name) || e.name().toLowerCase().equals(name)) {
				return e;
			}
		}
		return null;
	}
	
	static <T> T asObject(final Class<T> cls, final JsonValue value) throws Exception {
		return fromJson(value.toString(), cls);
	}
	
	static JsonArray toJsonArray(final Collection<?> collection) throws Exception {
		final JsonArrayBuilder builder = Json.createArrayBuilder();
		for(Object object : collection) {
			builder.add(toJsonValue(object));
		}
		return builder.build();
	}
	
	static <T> JsonArray toJsonArray(final T[] array) throws Exception {
		final JsonArrayBuilder builder = Json.createArrayBuilder();
		for(T object : array) {
			builder.add(toJsonValue(object));
		}
		return builder.build();
	}
	
	static JsonArray emptyArray() {
		return Json.createArrayBuilder().build();
	}
	
	static JsonObject empty() {
		return Json.createObjectBuilder().build();
	}
	
	static String toString(final Object object) throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create()){
			return jsonb.toJson(object);
		}
	}
	
	static JsonArray readArray(final InputStream input) {
		try(JsonReader jsonReader = Json.createReader(input)){
			return jsonReader.readArray();
		}
	}
	
	static JsonObject readObject(final InputStream input) {
		try(JsonReader jsonReader = Json.createReader(input)){
			return jsonReader.readObject();
		}
	}
	
	static JsonArray readArray(final Reader reader) {
		try(JsonReader jsonReader = Json.createReader(reader)){
			return jsonReader.readArray();
		}
	}
	
	static JsonObject readObject(final Reader reader) {
		try(JsonReader jsonReader = Json.createReader(reader)){
			return jsonReader.readObject();
		}
	}
	
	static JsonArray readArray(final String input) {
		try(StringReader reader = new StringReader(input)){
			return readArray(reader);
		}
	}
	
	static JsonObject readObject(final String input) {
		try(StringReader reader = new StringReader(input)){
			return readObject(reader);
		}
	}
	
	static JsonValue readValue(final String input) {
		try(StringReader reader = new StringReader(input)){
			return readValue(reader);
		}
	}
	
	static JsonValue readValue(final InputStream input) {
		try(JsonReader jsonReader = Json.createReader(input)){
			return jsonReader.readValue();
		}
	}
	
	static JsonValue readValue(final Reader reader) {
		try(JsonReader jsonReader = Json.createReader(reader)){
			return jsonReader.readValue();
		}
	}
	
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
	
	static JsonPatch createDiff(final Object source, final Object target) throws Exception {
		return Json.createDiff(toJsonObject(source), toJsonObject(target));
	}
	
	static <T> T fromJson(final InputStream input, final Class<T> cls) throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create()){
			return jsonb.fromJson(input, cls);
		}
	}
	
	static <T> T fromJson(final String input, final Class<T> cls) throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create()){
			return jsonb.fromJson(input, cls);
		}
	}
	
	static void asMap(final Map<String, Object> map, final JsonObject jsonObject) {
		for(Entry<String, JsonValue> entry : jsonObject.entrySet()) {
			map.put(entry.getKey(), asValue(entry.getValue()));
		}
	}
	
	static Map<String, Object> asMap(final JsonObject jsonObject) {
		final Map<String, Object> map = new HashMap<>();
		asMap(map, jsonObject);
		return map;
	}
	
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
	
	static List<Object> asList(final JsonArray jsonArray) {
		final List<Object> list = new ArrayList<>();
		jsonArray.forEach(jsonValue -> list.add(asValue(jsonValue)));
		return list;
	}
	
	static <T> List<Object> toList(final T[] array) throws Exception {
		final JsonArray jsonArray = toJsonArray(array);
		return asList(jsonArray);
	}
	
	static <T> T fromMap(final Map<?, ?> map, final Class<T> cls) throws Exception {
		Objects.requireNonNull(map, "Map");
		Objects.requireNonNull(cls, "Class");
		try(Jsonb jsonb = JsonbBuilder.create()){
			final String json = jsonb.toJson(map);
			return jsonb.fromJson(json, cls);
		}
	}
	
	static <T> T fromMap(final Map<?, ?> map, final Class<T> cls, final JsonbConfig config) throws Exception {
		Objects.requireNonNull(map, "Map");
		Objects.requireNonNull(cls, "Class");
		Objects.requireNonNull(config, "JsonbConfig");
		try(Jsonb jsonb = JsonbBuilder.create(config)){
			final String json = jsonb.toJson(map);
			return jsonb.fromJson(json, cls);
		}
	}
	
	static <T> List<T> fromLisṭ̣(final List<?> list, final Class<T> cls) throws Exception{
		Objects.requireNonNull(list, "List");
		Objects.requireNonNull(cls, "Class");
		final List<T> result = new ArrayList<>();
		for(Object object : list) {
			if(object instanceof Map) {
				final Map<?, ?> map = (Map<?, ?>) object;
				final T t = fromMap(map, cls);
				result.add(t);
			}
			else {
				result.add(null);
			}
		}
		return result;
	}
	
	static <T> List<T> fromLisṭ̣(final List<?> list, final Function<Map<?, ?>, T> convert) throws Exception{
		Objects.requireNonNull(list, "List");
		Objects.requireNonNull(convert, "Function");
		final List<T> result = new ArrayList<>();
		for(Object object : list) {
			if(object instanceof Map) {
				final Map<?, ?> map = (Map<?, ?>) object;
				final T t = convert.apply(map);
				result.add(t);
			}
			else {
				result.add(null);
			}
		}
		return result;
	}
	
	static <T> Map<String, Object> toMap(final T obj) throws Exception {
		Objects.requireNonNull(obj, "Object");
		final Map<String, Object> map = new HashMap<>();
		asMap(map, toJsonObject(obj));
		return map;
	}
	
	static JsonMergePatch createMergeDiff(final Map<?, ?> source, final Map<?, ?> target) throws Exception{
		return Json.createMergeDiff(toJsonValue(source), toJsonValue(target));
	}
	
	static boolean isPrimitive(final Class<?> clazz) {
		return clazz.isAssignableFrom(String.class) || clazz.isAssignableFrom(Boolean.class) || clazz.isAssignableFrom(Number.class);
	}
}
