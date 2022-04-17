package epf.util.json;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
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
				final String className = jsonObject.getString(Naming.CLASS);
				final Class<?> cls = Class.forName(className);
				return jsonb.fromJson(string, cls);
			}
		}
	}
	
	/**
	 * @param jsonb
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public List<Object> decodeArray(final Jsonb jsonb, final String string) throws Exception {
		try(StringReader reader = new StringReader(string)){
			try(JsonReader jsonReader = Json.createReader(reader)){
				final JsonArray jsonArray = jsonReader.readArray();
				final Iterator<JsonValue> it = jsonArray.iterator();
				final List<Object> array = new ArrayList<>();
				while(it.hasNext()) {
					array.add(decode(jsonb, it.next().toString()));
				}
				return array;
			}
		}
	}
	
	/**
	 * @param jsonb
	 * @param stream
	 * @return
	 * @throws Exception
	 */
	public Object decode(final Jsonb jsonb, final InputStream stream) throws Exception {
		try(JsonReader jsonReader = Json.createReader(stream)){
			final JsonObject jsonObject = jsonReader.readObject();
			final String className = jsonObject.getString(Naming.CLASS);
			final Class<?> cls = Class.forName(className);
			return jsonb.fromJson(stream, cls);
		}
	}
	
	/**
	 * @param jsonb
	 * @param reader
	 * @return
	 * @throws Exception
	 */
	public Object decode(final Jsonb jsonb, final Reader reader) throws Exception {
		try(JsonReader jsonReader = Json.createReader(reader)){
			final JsonObject jsonObject = jsonReader.readObject();
			final String className = jsonObject.getString(Naming.CLASS);
			final Class<?> cls = Class.forName(className);
			return jsonb.fromJson(reader, cls);
		}
	}
	
	/**
	 * @param jsonb
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public List<Object> decodeArray(final Jsonb jsonb, final InputStream stream) throws Exception {
		try(JsonReader jsonReader = Json.createReader(stream)){
			final JsonArray jsonArray = jsonReader.readArray();
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
	
	/**
	 * @param jsonb
	 * @param reader
	 * @return
	 * @throws Exception
	 */
	public List<Object> decodeArray(final Jsonb jsonb, final Reader reader) throws Exception {
		try(JsonReader jsonReader = Json.createReader(reader)){
			final JsonArray jsonArray = jsonReader.readArray();
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
}
