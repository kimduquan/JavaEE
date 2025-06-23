package epf.workflow.schema;

import org.eclipse.microprofile.graphql.Description;
import jakarta.json.bind.annotation.JsonbSubtype;
import jakarta.json.bind.annotation.JsonbTypeInfo;
import jakarta.validation.constraints.NotNull;

@Description("Enables the execution of a specified function within a workflow, allowing seamless integration with custom business logic or external services.")
@JsonbTypeInfo(key = "call", value = {
		@JsonbSubtype(alias = "asyncapi", type = AsyncAPICall.class),
		@JsonbSubtype(alias = "grpc", type = gRPCCall.class),
		@JsonbSubtype(alias = "http", type = HTTPCall.class),
		@JsonbSubtype(alias = "openapi", type = OpenAPICall.class)
})
public class Call<T> {

	@NotNull
	@Description("The name of the function to call.")
	private String call;
	
	@Description("A name/value mapping of the parameters to call the function with")
	private T with;

	public String getCall() {
		return call;
	}

	public void setCall(String call) {
		this.call = call;
	}

	public T getWith() {
		return with;
	}

	public void setWith(T with) {
		this.with = with;
	}
}
