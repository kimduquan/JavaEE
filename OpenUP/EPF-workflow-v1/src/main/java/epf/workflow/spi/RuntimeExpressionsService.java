package epf.workflow.spi;

import java.util.Map;
import epf.workflow.schema.AuthorizationDescriptor;
import epf.workflow.schema.ExpressionError;
import epf.workflow.schema.RuntimeDescriptor;
import epf.workflow.schema.TaskDescriptor;
import epf.workflow.schema.WorkflowDescriptor;

public interface RuntimeExpressionsService {

	Object evaluate(final String workflowInputFrom, final Map<String, Object> secrets, final WorkflowDescriptor workflow, final RuntimeDescriptor runtime) throws ExpressionError;
	
	Object evaluate(final String rawTaskInput, final Map<String, Object> context, final Map<String, Object> secrets, final TaskDescriptor task, final WorkflowDescriptor workflow, final RuntimeDescriptor runtime) throws ExpressionError;
	
	void evaluate(final String transformedTaskInput, final Object input, final Map<String, Object> context, final Map<String, Object> secrets, final TaskDescriptor task, final WorkflowDescriptor workflow, final RuntimeDescriptor runtime) throws ExpressionError;
	
	void evaluate(final String transformedTaskInput, final Object input, final Map<String, Object> context, final Map<String, Object> secrets, final TaskDescriptor task, final WorkflowDescriptor workflow, final RuntimeDescriptor runtime, final AuthorizationDescriptor authorization) throws ExpressionError;
	
	Object evaluate(final String rawTaskOutput, final Map<String, Object> context, final Object input, final Map<String, Object> secrets, final TaskDescriptor task, final WorkflowDescriptor workflow, final RuntimeDescriptor runtime, final AuthorizationDescriptor authorization) throws ExpressionError;
	
	Map<String, Object> evaluate(final String transformedTaskOutput, final Map<String, Object> context, final Object input, final Object output, final Map<String, Object> secrets, final TaskDescriptor task, final WorkflowDescriptor workflow, final RuntimeDescriptor runtime, final AuthorizationDescriptor authorization) throws ExpressionError;
	
	Object evaluate(final String lastTaskTransformedOutput, final Map<String, Object> context, final Map<String, Object> secrets, final WorkflowDescriptor workflow, final RuntimeDescriptor runtime) throws ExpressionError;
	
	boolean if_(final String condition, final Map<String, Object> context, final Map<String, Object> secrets) throws ExpressionError;
}
