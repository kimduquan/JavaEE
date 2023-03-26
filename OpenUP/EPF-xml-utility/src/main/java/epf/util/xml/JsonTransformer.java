package epf.util.xml;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author PC
 *
 */
public class JsonTransformer {
	
	/**
	 * @param element
	 * @param object
	 */
	protected void transform(final Document document, final Element element, final JsonObject object) {
		object.forEach((name, value) -> {
			final ValueType type = value.getValueType();
			switch(type) {
			case ARRAY:
				final JsonArray array = object.getJsonArray(name);
				final Iterator<JsonValue> it = array.iterator();
				while(it.hasNext()) {
					it.next();
				}
				break;
			case FALSE:
				element.setAttribute(name, String.valueOf(object.getBoolean(name)));
				break;
			case NULL:
				break;
			case NUMBER:
				element.setAttribute(name, String.valueOf(object.getInt(name)));
				break;
			case OBJECT:
				final JsonObject childObject = object.getJsonObject(name);
				final Element childElement = document.createElement(name);
				transform(document, childElement, childObject);
				break;
			case STRING:
				element.setAttribute(name, object.getString(name));
				break;
			case TRUE:
				element.setAttribute(name, String.valueOf(object.getBoolean(name)));
				break;
			default:
				break;
			
			}
		});
	}

	/**
	 * @param input
	 * @param output
	 * @throws Exception 
	 */
	public void transform(final String root, final InputStream input, final OutputStream output) throws Exception {
		final DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder builder = builderFactory.newDocumentBuilder();
		final Document document = builder.newDocument();
		final DOMSource source = new DOMSource(document);
		
		final TransformerFactory factory = TransformerFactory.newInstance();
		final Transformer transformer = factory.newTransformer();
		final StreamResult result = new StreamResult(output);
		
		final Element rootElement = document.createElement(root);
		try(JsonReader reader = Json.createReader(input)){
			transform(document, rootElement, reader.readObject());
		}
		transformer.transform(source, result);
	}
}
