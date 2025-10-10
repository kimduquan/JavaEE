package epf.payment.schema;

public class PaymentDetailsInit extends PaymentDetailsBase {

	private String id;
	private PaymentItem total;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public PaymentItem getTotal() {
		return total;
	}
	public void setTotal(PaymentItem total) {
		this.total = total;
	}
}
