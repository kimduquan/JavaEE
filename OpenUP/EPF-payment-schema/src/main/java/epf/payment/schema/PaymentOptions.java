package epf.payment.schema;

public class PaymentOptions {

	private boolean requestPayerName = false;
	private boolean requestBillingAddress = false;
	private boolean requestPayerEmail = false;
	private boolean requestPayerPhone = false;
	private boolean requestShipping = false;
	private PaymentShippingType shippingType = PaymentShippingType.shipping;
	
	public boolean isRequestPayerName() {
		return requestPayerName;
	}
	public void setRequestPayerName(boolean requestPayerName) {
		this.requestPayerName = requestPayerName;
	}
	public boolean isRequestBillingAddress() {
		return requestBillingAddress;
	}
	public void setRequestBillingAddress(boolean requestBillingAddress) {
		this.requestBillingAddress = requestBillingAddress;
	}
	public boolean isRequestPayerEmail() {
		return requestPayerEmail;
	}
	public void setRequestPayerEmail(boolean requestPayerEmail) {
		this.requestPayerEmail = requestPayerEmail;
	}
	public boolean isRequestPayerPhone() {
		return requestPayerPhone;
	}
	public void setRequestPayerPhone(boolean requestPayerPhone) {
		this.requestPayerPhone = requestPayerPhone;
	}
	public boolean isRequestShipping() {
		return requestShipping;
	}
	public void setRequestShipping(boolean requestShipping) {
		this.requestShipping = requestShipping;
	}
	public PaymentShippingType getShippingType() {
		return shippingType;
	}
	public void setShippingType(PaymentShippingType shippingType) {
		this.shippingType = shippingType;
	}
}
