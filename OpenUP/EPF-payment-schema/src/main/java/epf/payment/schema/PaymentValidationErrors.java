package epf.payment.schema;

public class PaymentValidationErrors {

	private PayerErrors payer;
	private AddressErrors shippingAddress;
	private String error;
	private Object paymentMethod;
	
	public PayerErrors getPayer() {
		return payer;
	}
	public void setPayer(PayerErrors payer) {
		this.payer = payer;
	}
	public AddressErrors getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(AddressErrors shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Object getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(Object paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
}
