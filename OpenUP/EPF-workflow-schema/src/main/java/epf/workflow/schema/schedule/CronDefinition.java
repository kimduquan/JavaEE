package epf.workflow.schema.schedule;

import jakarta.validation.constraints.NotNull;
import jakarta.nosql.Column;
import java.io.Serializable;
import org.eclipse.jnosql.mapping.Embeddable;
import org.eclipse.microprofile.graphql.Description;

@Embeddable
public class CronDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column
	@Description("Cron expression describing when the workflow instance should be created (automatically)")
	private String expression;
	
	@Column
	@Description("Specific date and time (ISO 8601 format, literal or expression producing it) when the cron expression is no longer valid")
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
