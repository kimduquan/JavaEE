package epf.payment.schema;

public class PaymentRequest {
	
	private String id;
	private ContactAddress shippingAddress;
	private String shippingOption;
	private PaymentShippingType shippingType;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ContactAddress getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(ContactAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public String getShippingOption() {
		return shippingOption;
	}
	public void setShippingOption(String shippingOption) {
		this.shippingOption = shippingOption;
	}
	public PaymentShippingType getShippingType() {
		return shippingType;
	}
	public void setShippingType(PaymentShippingType shippingType) {
		this.shippingType = shippingType;
	}
}
