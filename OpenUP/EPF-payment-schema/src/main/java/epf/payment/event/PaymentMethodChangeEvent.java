package epf.payment.event;

public class PaymentMethodChangeEvent extends PaymentRequestUpdateEvent {

	private String methodName;
	private Object methodDetails;
	
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Object getMethodDetails() {
		return methodDetails;
	}
	public void setMethodDetails(Object methodDetails) {
		this.methodDetails = methodDetails;
	}
}
