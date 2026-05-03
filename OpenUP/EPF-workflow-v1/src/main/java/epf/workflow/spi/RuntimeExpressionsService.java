package epf.workflow.spi;

import java.util.List;
import java.util.Map;
import epf.workflow.schema.AuthorizationDescriptor;
import epf.workflow.schema.RuntimeDescriptor;
import epf.workflow.schema.TaskDescriptor;
import epf.workflow.schema.WorkflowDescriptor;

public interface RuntimeExpressionsService {

	Object evaluate(final String workflowInputFrom, final Map<String, Object> secrets, final WorkflowDescriptor workflow, final RuntimeDescriptor runtime) throws Exception;
	
	Object evaluate(final String rawTaskInput, final Map<String, Object> context, final Map<String, Object> secrets, final TaskDescriptor task, final WorkflowDescriptor workflow, final RuntimeDescriptor runtime) throws Exception;
	
	void evaluate(final String transformedTaskInput, final Object input, final Map<String, Object> context, final Map<String, Object> secrets, final TaskDescriptor task, final WorkflowDescriptor workflow, final RuntimeDescriptor runtime) throws Exception;
	
	void evaluate(final String transformedTaskInput, final Object input, final Map<String, Object> context, final Map<String, Object> secrets, final TaskDescriptor task, final WorkflowDescriptor workflow, final RuntimeDescriptor runtime, final AuthorizationDescriptor authorization) throws Exception;
	
	Object evaluate(final String rawTaskOutput, final Map<String, Object> context, final Object input, final Map<String, Object> secrets, final TaskDescriptor task, final WorkflowDescriptor workflow, final RuntimeDescriptor runtime, final AuthorizationDescriptor authorization) throws Exception;
	
	Map<String, Object> evaluate(final String transformedTaskOutput, final Map<String, Object> context, final Object input, final Object output, final Map<String, Object> secrets, final TaskDescriptor task, final WorkflowDescriptor workflow, final RuntimeDescriptor runtime, final AuthorizationDescriptor authorization) throws Exception;
	
	Object evaluate(final String lastTaskTransformedOutput, final Map<String, Object> context, final Map<String, Object> secrets, final WorkflowDescriptor workflow, final RuntimeDescriptor runtime) throws Exception;
	
	boolean if_(final String condition, final Map<String, Object> context, final Map<String, Object> secrets) throws Exception;
	
	<T> List<T> in(final String in, final Map<String, Object> context, final Map<String, Object> secrets) throws Exception;
	
	Map<String, Object> createContext(final Map<String, Object> context) throws Exception;
	
	void set(final Map<String, Object> context, final String name, final Object value) throws Exception;
	
	<T> T get(final Map<String, Object> context, final String name) throws Exception;
}
