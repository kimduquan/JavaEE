package epf.management.payment.schema;

import java.util.List;

public class Subscription {

	private String id;
	private String customer_id;
	private String description;
	private List<SubscriptionItem> items;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<SubscriptionItem> getItems() {
		return items;
	}
	public void setItems(List<SubscriptionItem> items) {
		this.items = items;
	}
}
