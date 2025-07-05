package epf.workflow.schema;

import org.eclipse.microprofile.graphql.Description;

@Description("The definition of a retry backoff strategy.")
public class Backoff {

	@Description("The definition of the constant backoff to use, if any.")
	private Object constant;

	@Description("The definition of the exponential backoff to use, if any.")
	private Object exponential;
	
	@Description("The definition of the linear backoff to use, if any.")
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
