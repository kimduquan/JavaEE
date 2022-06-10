package epf.util.json;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

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
		final JsonObject jsonObject = JsonUtil.readObject(string);
		final String className = jsonObject.getString(Naming.CLASS);
		final Class<?> cls = Class.forName(className);
		return jsonb.fromJson(string, cls);
	}
	
	/**
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public Object decode(final String string) throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create()){
			return decode(jsonb, string);
		}
	}
	
	/**
	 * @param jsonb
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public List<Object> decodeArray(final Jsonb jsonb, final String string) throws Exception {
		final JsonArray jsonArray = JsonUtil.readArray(string);
		final Iterator<JsonValue> it = jsonArray.iterator();
		final List<Object> array = new ArrayList<>();
		while(it.hasNext()) {
			array.add(decode(jsonb, it.next().toString()));
		}
		return array;
	}
	
	/**
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public List<Object> decodeArray(final String string) throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create()){
			return decodeArray(jsonb, string);
		}
	}
	
	/**
	 * @param jsonb
	 * @param stream
	 * @return
	 * @throws Exception
	 */
	public Object decode(final Jsonb jsonb, final InputStream stream) throws Exception {
		final JsonObject jsonObject = JsonUtil.readObject(stream);
		final String className = jsonObject.getString(Naming.CLASS);
		final Class<?> cls = Class.forName(className);
		return jsonb.fromJson(stream, cls);
	}
	
	/**
	 * @param jsonb
	 * @param reader
	 * @return
	 * @throws Exception
	 */
	public Object decode(final Jsonb jsonb, final Reader reader) throws Exception {
		final JsonObject jsonObject = JsonUtil.readObject(reader);
		final String className = jsonObject.getString(Naming.CLASS);
		final Class<?> cls = Class.forName(className);
		return jsonb.fromJson(reader, cls);
	}
	
	/**
	 * @param jsonb
	 * @param string
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * @param jsonb
	 * @param reader
	 * @return
	 * @throws Exception
	 */
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
