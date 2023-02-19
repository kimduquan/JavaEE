package epf.workflow.schema;

import javax.validation.constraints.NotNull;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class CronDefinition {

	/**
	 * 
	 */
	@NotNull
	@Column
	private String expression;
	
	/**
	 * 
	 */
	@Column
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
