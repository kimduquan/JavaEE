package epf.workflow.schema;

import java.net.URI;

public interface StandardErrorTypes {

	URI configuration = URI.create("https://serverlessworkflow.io/spec/1.0.0/errors/configuration");
	
	URI validation = URI.create("https://serverlessworkflow.io/spec/1.0.0/errors/validation");
	
	URI expression = URI.create("https://serverlessworkflow.io/spec/1.0.0/errors/expression");
	
	URI authentication = URI.create("https://serverlessworkflow.io/spec/1.0.0/errors/authentication");
	
	URI authorization = URI.create("https://serverlessworkflow.io/spec/1.0.0/errors/authorization");
	
	URI timeout = URI.create("https://serverlessworkflow.io/spec/1.0.0/errors/timeout");
	
	URI communication = URI.create("https://serverlessworkflow.io/spec/1.0.0/errors/communication");
	
	URI runtime = URI.create("https://serverlessworkflow.io/spec/1.0.0/errors/runtime");
}
