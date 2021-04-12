/**
 * 
 */
package epf.client.messaging;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * @author PC
 *
 */
public class MessageDecoder implements Decoder.Text<Object> {

	@Override
	public void init(final EndpointConfig config) {
		
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public Object decode(final String string) throws DecodeException {
		final int separatorIndex = string.indexOf('=');
		final String className = string.substring(0, separatorIndex);
		final String jsonString = string.substring(separatorIndex + 1);
		Object object;
		try(Jsonb json = JsonbBuilder.create()){
			final Class<?> cls = Class.forName(className);
			object = json.fromJson(jsonString, cls);
		}
		catch (Exception e) {
			throw new DecodeException(string, e.getMessage(), e);
		}
		return object;
	}

	@Override
	public boolean willDecode(final String string) {
		final int separatorIndex = string.indexOf('=');
		return separatorIndex != -1;
	}

}
