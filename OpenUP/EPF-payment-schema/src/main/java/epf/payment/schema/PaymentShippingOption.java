package epf.payment.schema;

public class PaymentShippingOption {

	private String id;
	private String label;
	private PaymentCurrencyAmount amount;
	private boolean selected = false;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public PaymentCurrencyAmount getAmount() {
		return amount;
	}
	public void setAmount(PaymentCurrencyAmount amount) {
		this.amount = amount;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
