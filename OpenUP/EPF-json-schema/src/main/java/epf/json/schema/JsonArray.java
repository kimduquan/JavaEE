package epf.json.schema;

public class JsonArray extends TypeValue {

	private static final long serialVersionUID = 1L;
	
	private Value items;
	private Value[] prefixItems;
	private Value contains;
	private Integer minContains;
	private Integer maxContains;
	private Integer minItems;
	private Integer maxItems;
	private Boolean uniqueItems;
	
	public JsonArray() {
		setType(Type.array);
	}

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

	public void setMinContains(Integer minContains) {
		this.minContains = minContains;
	}

	public Integer getMaxContains() {
		return maxContains;
	}

	public void setMaxContains(Integer maxContains) {
		this.maxContains = maxContains;
	}

	public Integer getMinItems() {
		return minItems;
	}

	public void setMinItems(Integer minItems) {
		this.minItems = minItems;
	}

	public Integer getMaxItems() {
		return maxItems;
	}

	public void setMaxItems(Integer maxItems) {
		this.maxItems = maxItems;
	}

	public Boolean isUniqueItems() {
		return uniqueItems;
	}

	public void setUniqueItems(Boolean uniqueItems) {
		this.uniqueItems = uniqueItems;
	}
}
