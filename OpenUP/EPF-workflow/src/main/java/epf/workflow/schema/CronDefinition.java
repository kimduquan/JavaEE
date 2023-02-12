package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class CronDefinition {

	/**
	 * 
	 */
	private String expression;
	
	/**
	 * 
	 */
	private String validUntil;

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getValidUntil() {
		return validUntil;
	}

	public void setValidUntil(String validUntil) {
		this.validUntil = validUntil;
	}
}
