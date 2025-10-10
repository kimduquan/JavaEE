package epf.payment.schema;

public class PaymentItem {

	private String label;
	private PaymentCurrencyAmount amount;
	private boolean pending = false;
	
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
	public boolean isPending() {
		return pending;
	}
	public void setPending(boolean pending) {
		this.pending = pending;
	}
}
