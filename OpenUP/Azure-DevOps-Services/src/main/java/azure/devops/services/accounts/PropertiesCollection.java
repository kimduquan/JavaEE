package azure.devops.services.accounts;

import java.util.HashMap;

/**
 * @author PC
 * The class represents a property bag as a collection of key-value pairs. 
 * Values of all primitive types (any type with a TypeCode != TypeCode.Object) except for DBNull are accepted. 
 * Values of type Byte[], Int32, Double, DateType and String preserve their type, other primitives are retuned as a String. 
 * Byte[] expected as base64 encoded string.
 */
public class PropertiesCollection extends HashMap<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The count of properties in the collection.
	 */
	int count;
	Object item;
	/**
	 * The set of keys in the collection.
	 */
	String[] keys;
	/**
	 * The set of values in the collection.
	 */
	String[] values;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Object getItem() {
		return item;
	}
	public void setItem(Object item) {
		this.item = item;
	}
	public String[] getKeys() {
		return keys;
	}
	public void setKeys(String[] keys) {
		this.keys = keys;
	}
	public String[] getValues() {
		return values;
	}
	public void setValues(String[] values) {
		this.values = values;
	}

}
