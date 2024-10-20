package epf.workflow.schema;

import java.io.Serializable;
import java.util.List;
import org.eclipse.jnosql.mapping.Embeddable;
import org.eclipse.microprofile.graphql.Description;
import epf.nosql.schema.StringOrObject;
import epf.workflow.schema.adapter.StringOrRetryDefinitionAdapter;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.nosql.Column;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class ErrorHandlerDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column
	@Description("The unique name which is used to reference the defined handler.")
	private String name;

	@Column
	@Description("References the errors to handle. If null or empty, and if exceptWhen is null or empty, all errors are caught.")
	private List<ErrorReference> when;
	
	@Column
	@Description("References the errors not to handle. If null or empty, and if when is null or empty, all errors are caught.")
	private List<ErrorReference> exceptWhen;
	
	@Column
	@Description("The retry policy to use, if any. If a string, references an existing retry definition.")
	@JsonbTypeAdapter(value = StringOrRetryDefinitionAdapter.class)
	private StringOrObject<RetryDefinition> retry;
	
	@Column
	@Description("Defines the outcome, if any, when handling errors")
	private ErrorOutcomeDefinition then;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ErrorReference> getWhen() {
		return when;
	}

	public void setWhen(List<ErrorReference> when) {
		this.when = when;
	}

	public List<ErrorReference> getExceptWhen() {
		return exceptWhen;
	}

	public void setExceptWhen(List<ErrorReference> exceptWhen) {
		this.exceptWhen = exceptWhen;
	}

	public StringOrObject<RetryDefinition> getRetry() {
		return retry;
	}

	public void setRetry(StringOrObject<RetryDefinition> retry) {
		this.retry = retry;
	}

	public ErrorOutcomeDefinition getThen() {
		return then;
	}

	public void setThen(ErrorOutcomeDefinition then) {
		this.then = then;
	}
}
