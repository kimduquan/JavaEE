package epf.payment.schema;

public class PaymentMethodData {

	private String supportedMethods;
	private Object data;
	
	public String getSupportedMethods() {
		return supportedMethods;
	}
	public void setSupportedMethods(String supportedMethods) {
		this.supportedMethods = supportedMethods;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
