package epf.portlet.util.json;

import java.util.Comparator;
import javax.json.JsonObject;
import javax.json.JsonValue;

import epf.util.json.JsonUtil;

/**
 * @author PC
 *
 */
public class JsonObjectComparator implements Comparator<JsonObject> {
	
	/**
	 * 
	 */
	private final String name;
	
	/**
	 * 
	 */
	private boolean ascending;
	
	/**
	 * @param name
	 */
	public JsonObjectComparator(final String name) {
		this.name = name;
		ascending = true;
	}

	@Override
	public int compare(final JsonObject o1, final JsonObject o2) {
		final JsonValue jsonValue1 = o1.get(name);
		final JsonValue jsonValue2 = o2.get(name);
		int result = 0;
		if(jsonValue1 == null && jsonValue2 != null) {
			result = -1;
		}
		else if(jsonValue1 != null && jsonValue2 == null) {
			result = 1;
		}
		else if(jsonValue1 != null && jsonValue2 != null) {
			result = JsonUtil.toString(jsonValue1).compareTo(JsonUtil.toString(jsonValue2));
		}
		return result * (ascending ? 1 : -1);
	}

	/**
	 * @return the ascending
	 */
	public boolean isAscending() {
		return ascending;
	}

	/**
	 * @param ascending the ascending to set
	 */
	public void setAscending(final boolean ascending) {
		this.ascending = ascending;
	}

}
