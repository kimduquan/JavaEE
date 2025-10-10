package epf.payment.schema;

public class PaymentCurrencyAmount {
	
	private String currency;
	private String value;
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
