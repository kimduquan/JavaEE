/**
 * 
 */
package epf.client.messaging;

import java.io.StringWriter;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @author PC
 *
 */
public class MessageEncoder implements Encoder.Text<Object> {

	@Override
	public void init(final EndpointConfig config) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public String encode(final Object object) throws EncodeException {
		String jsonString;
		try(Jsonb json = JsonbBuilder.create()){
			try(StringWriter writer = new StringWriter()){
				writer.append(object.getClass().getName()).append('=');
				json.toJson(object, writer);
				jsonString = writer.toString();
			}
		}
		catch (Exception e) {
			throw new EncodeException(object, e.getMessage(), e);
		}
		return jsonString;
	}

}
