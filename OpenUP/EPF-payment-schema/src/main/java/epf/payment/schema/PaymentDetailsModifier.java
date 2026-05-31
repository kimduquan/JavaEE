package epf.payment.schema;

import java.util.List;

public class PaymentDetailsModifier {

	private String supportedMethods;
	private PaymentItem total;
	private List<PaymentItem> additionalDisplayItems;
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
	public List<PaymentItem> getAdditionalDisplayItems() {
		return additionalDisplayItems;
	}
	public void setAdditionalDisplayItems(List<PaymentItem> additionalDisplayItems) {
		this.additionalDisplayItems = additionalDisplayItems;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
