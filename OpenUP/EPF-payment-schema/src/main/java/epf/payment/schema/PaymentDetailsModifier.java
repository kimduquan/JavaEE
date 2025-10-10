package epf.payment.schema;

public class PaymentDetailsModifier {

	private String supportedMethods;
	private PaymentItem total;
	private PaymentItem[] additionalDisplayItems;
	private Object data;
	
	public String getSupportedMethods() {
		return supportedMethods;
	}
	public void setSupportedMethods(String supportedMethods) {
		this.supportedMethods = supportedMethods;
	}
	public PaymentItem getTotal() {
		return total;
	}
	public void setTotal(PaymentItem total) {
		this.total = total;
	}
	public PaymentItem[] getAdditionalDisplayItems() {
		return additionalDisplayItems;
	}
	public void setAdditionalDisplayItems(PaymentItem[] additionalDisplayItems) {
		this.additionalDisplayItems = additionalDisplayItems;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
