package epf.util.json.ext;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

public class Decoder {

	public Object decode(final Jsonb jsonb, final String string) throws Exception {
		final JsonObject jsonObject = JsonUtil.readObject(string);
		final String className = jsonObject.getString(Naming.CLASS);
		final Class<?> cls = Class.forName(className);
		return jsonb.fromJson(string, cls);
	}
	
	public Object decode(final String string) throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create()){
			return decode(jsonb, string);
		}
	}
	
	public List<Object> decodeArray(final Jsonb jsonb, final String string) throws Exception {
		final JsonArray jsonArray = JsonUtil.readArray(string);
		final Iterator<JsonValue> it = jsonArray.iterator();
		final List<Object> array = new ArrayList<>();
		while(it.hasNext()) {
			array.add(decode(jsonb, it.next().toString()));
		}
		return array;
	}
	
	public List<Object> decodeArray(final String string) throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create()){
			return decodeArray(jsonb, string);
		}
	}
	
	public Object decode(final Jsonb jsonb, final InputStream stream) throws Exception {
		final JsonObject jsonObject = JsonUtil.readObject(stream);
		final String className = jsonObject.getString(Naming.CLASS);
		final Class<?> cls = Class.forName(className);
		return jsonb.fromJson(stream, cls);
	}
	
	public Object decode(final Jsonb jsonb, final Reader reader) throws Exception {
		final JsonObject jsonObject = JsonUtil.readObject(reader);
		final String className = jsonObject.getString(Naming.CLASS);
		final Class<?> cls = Class.forName(className);
		return jsonb.fromJson(reader, cls);
	}
	
	public List<Object> decodeArray(final Jsonb jsonb, final InputStream stream) throws Exception {
		final JsonArray jsonArray = JsonUtil.readArray(stream);
		final Iterator<JsonValue> it = jsonArray.iterator();
		final List<Object> array = new ArrayList<>();
		while(it.hasNext()) {
			final JsonObject jsonObject = it.next().asJsonObject();
			final String className = jsonObject.getString(Naming.CLASS);
			final Class<?> cls = Class.forName(className);
			final Object object = jsonb.fromJson(jsonObject.toString(), cls);
			array.add(object);
		}
		return array;
	}
	
	public List<Object> decodeArray(final Jsonb jsonb, final Reader reader) throws Exception {
		final JsonArray jsonArray = JsonUtil.readArray(reader);
		final Iterator<JsonValue> it = jsonArray.iterator();
		final List<Object> array = new ArrayList<>();
		while(it.hasNext()) {
			final JsonObject jsonObject = it.next().asJsonObject();
			final String className = jsonObject.getString(Naming.CLASS);
			final Class<?> cls = Class.forName(className);
			final Object object = jsonb.fromJson(jsonObject.toString(), cls);
			array.add(object);
		}
		return array;
	}
}
