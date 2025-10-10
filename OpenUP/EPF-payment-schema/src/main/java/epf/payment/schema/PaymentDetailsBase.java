package epf.payment.schema;

public class PaymentDetailsBase {
	
	private PaymentItem[] displayItems;
	private PaymentShippingOption[] shippingOptions;
	private PaymentDetailsModifier[] modifiers;
	
	public PaymentItem[] getDisplayItems() {
		return displayItems;
	}
	public void setDisplayItems(PaymentItem[] displayItems) {
		this.displayItems = displayItems;
	}
	public PaymentShippingOption[] getShippingOptions() {
		return shippingOptions;
	}
	public void setShippingOptions(PaymentShippingOption[] shippingOptions) {
		this.shippingOptions = shippingOptions;
	}
	public PaymentDetailsModifier[] getModifiers() {
		return modifiers;
	}
	public void setModifiers(PaymentDetailsModifier[] modifiers) {
		this.modifiers = modifiers;
	}
}
