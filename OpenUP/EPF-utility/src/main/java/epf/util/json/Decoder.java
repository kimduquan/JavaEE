/**
 * 
 */
package epf.util.json;

import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.bind.Jsonb;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
public class Decoder {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(Decoder.class.getName());

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
		catch(Exception ex) {
			LOGGER.throwing(LOGGER.getName(), "decode", ex);
			throw ex;
		}
	}
	
	/**
	 * @param jsonb
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public List<Object> decodeArray(final Jsonb jsonb, final String string) throws Exception {
		final List<Object> array = new ArrayList<>();
		try(StringReader reader = new StringReader(string)){
			try(JsonReader jsonReader = Json.createReader(reader)){
				final JsonArray jsonArray = jsonReader.readArray();
				final Iterator<JsonValue> it = jsonArray.iterator();
				while(it.hasNext()) {
					array.add(decode(jsonb, it.next().toString()));
				}
			}
		}
		catch(Exception ex) {
			LOGGER.throwing(LOGGER.getName(), "decodeArray", ex);
			throw ex;
		}
		return array;
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
		catch(Exception ex) {
			LOGGER.throwing(LOGGER.getName(), "decode", ex);
			throw ex;
		}
	}
	
	/**
	 * @param jsonb
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public List<Object> decodeArray(final Jsonb jsonb, final InputStream stream) throws Exception {
		final List<Object> array = new ArrayList<>();
		try(JsonReader jsonReader = Json.createReader(stream)){
			final JsonArray jsonArray = jsonReader.readArray();
			final Iterator<JsonValue> it = jsonArray.iterator();
			while(it.hasNext()) {
				final JsonObject jsonObject = it.next().asJsonObject();
				final String className = jsonObject.getString(Naming.CLASS);
				final Class<?> cls = Class.forName(className);
				final Object object = jsonb.fromJson(jsonObject.toString(), cls);
				array.add(object);
			}
		}
		catch(Exception ex) {
			LOGGER.throwing(LOGGER.getName(), "decodeArray", ex);
			throw ex;
		}
		return array;
	}
}
