package epf.payment.event;

public class PaymentMethodChangeEventInit extends PaymentRequestUpdateEventInit {

	private String methodName = "";
	private Object methodDetails = null;
	
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
