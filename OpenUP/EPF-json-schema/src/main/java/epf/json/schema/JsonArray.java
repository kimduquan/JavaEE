package epf.json.schema;

/**
 * @author PC
 *
 */
public class JsonArray extends TypeValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Value items;
	/**
	 * 
	 */
	private Value[] prefixItems;
	/**
	 * 
	 */
	private Value contains;
	/**
	 * 
	 */
	private int minContains;
	/**
	 * 
	 */
	private int maxContains;
	/**
	 * 
	 */
	private int minItems;
	/**
	 * 
	 */
	private int maxItems;
	/**
	 * 
	 */
	private boolean uniqueItems;

	public Value getItems() {
		return items;
	}

	public void setItems(Value items) {
		this.items = items;
	}

	public Value[] getPrefixItems() {
		return prefixItems;
	}

	public void setPrefixItems(Value[] prefixItems) {
		this.prefixItems = prefixItems;
	}

	public Value getContains() {
		return contains;
	}

	public void setContains(Value contains) {
		this.contains = contains;
	}

	public int getMinContains() {
		return minContains;
	}

	public void setMinContains(int minContains) {
		this.minContains = minContains;
	}

	public int getMaxContains() {
		return maxContains;
	}

	public void setMaxContains(int maxContains) {
		this.maxContains = maxContains;
	}

	public int getMinItems() {
		return minItems;
	}

	public void setMinItems(int minItems) {
		this.minItems = minItems;
	}

	public int getMaxItems() {
		return maxItems;
	}

	public void setMaxItems(int maxItems) {
		this.maxItems = maxItems;
	}

	public boolean isUniqueItems() {
		return uniqueItems;
	}

	public void setUniqueItems(boolean uniqueItems) {
		this.uniqueItems = uniqueItems;
	}
}
