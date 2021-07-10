/**
 * 
 */
package epf.portlet;

import javax.json.JsonString;
import javax.json.JsonValue;

/**
 * @author PC
 *
 */
public interface JsonUtil {

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
}
