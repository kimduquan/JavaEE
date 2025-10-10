package epf.payment.schema;

public class PaymentDetailsUpdate extends PaymentDetailsBase {

	private String error;
	private PaymentItem total;
	private AddressErrors shippingAddressErrors;
	private PayerErrors payerErrors;
	private Object paymentMethodErrors;
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public PaymentItem getTotal() {
		return total;
	}
	public void setTotal(PaymentItem total) {
		this.total = total;
	}
	public AddressErrors getShippingAddressErrors() {
		return shippingAddressErrors;
	}
	public void setShippingAddressErrors(AddressErrors shippingAddressErrors) {
		this.shippingAddressErrors = shippingAddressErrors;
	}
	public PayerErrors getPayerErrors() {
		return payerErrors;
	}
	public void setPayerErrors(PayerErrors payerErrors) {
		this.payerErrors = payerErrors;
	}
	public Object getPaymentMethodErrors() {
		return paymentMethodErrors;
	}
	public void setPaymentMethodErrors(Object paymentMethodErrors) {
		this.paymentMethodErrors = paymentMethodErrors;
	}
}
