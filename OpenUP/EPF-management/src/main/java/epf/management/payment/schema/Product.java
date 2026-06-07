package epf.management.payment.schema;

public class Product {

	private String id;
	private String name;
	private String description;
	private String url;
	private String default_price_id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDefault_price_id() {
		return default_price_id;
	}
	public void setDefault_price_id(String default_price_id) {
		this.default_price_id = default_price_id;
	}
}
