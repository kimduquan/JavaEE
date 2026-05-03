package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("The definition of a retry backoff strategy.")
public class Backoff {

	@JsonPropertyDescription("The definition of the constant backoff to use, if any. Required if exponential and linear are not set, otherwise ignored.")
	private Object constant;
	
	@JsonPropertyDescription("The definition of the exponential backoff to use, if any. Required if constant and linear are not set, otherwise ignored.")
	private Object exponential;
	
	@JsonPropertyDescription("The definition of the linear backoff to use, if any. Required if constant and exponential are not set, otherwise ignored.")
	private Object linear;

	public Object getConstant() {
		return constant;
	}

	public void setConstant(Object constant) {
		this.constant = constant;
	}

	public Object getExponential() {
		return exponential;
	}

	public void setExponential(Object exponential) {
		this.exponential = exponential;
	}

	public Object getLinear() {
		return linear;
	}

	public void setLinear(Object linear) {
		this.linear = linear;
	}
}
