package epf.workflow.internal;

import java.util.Map;
import epf.workflow.schema.AuthorizationDescriptor;
import epf.workflow.schema.ExpressionError;
import epf.workflow.schema.RuntimeDescriptor;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.schema.TaskDescriptor;
import epf.workflow.schema.WorkflowDescriptor;
import epf.workflow.spi.RuntimeExpressionsService;

public abstract class RuntimeExpressionsServiceBase implements RuntimeExpressionsService {

	protected abstract void evaluate(final String expression, final RuntimeExpressionArguments arguments);

	@Override
	public Object evaluate(final String rawWorkflowInput, final Map<String, Object> secrets, final WorkflowDescriptor workflow, final RuntimeDescriptor runtime) throws ExpressionError {
		final RuntimeExpressionArguments arguments = new RuntimeExpressionArguments();
		arguments.setSecrets(secrets);
		arguments.setWorkflow(workflow);
		arguments.setRuntime(runtime);
		evaluate(rawWorkflowInput, arguments);
		return arguments.getInput();
	}

	@Override
	public Object evaluate(final String rawTaskInput, final Map<String, Object> context, final Map<String, Object> secrets, final TaskDescriptor task, final WorkflowDescriptor workflow, final RuntimeDescriptor runtime) throws ExpressionError {
		final RuntimeExpressionArguments arguments = new RuntimeExpressionArguments();
		arguments.setContext(context);
		arguments.setSecrets(secrets);
		arguments.setTask(task);
		arguments.setWorkflow(workflow);
		arguments.setRuntime(runtime);
		evaluate(rawTaskInput, arguments);
		return arguments.getInput();
	}

	@Override
	public void evaluate(final String transformedTaskInput, final Object input, final Map<String, Object> context, final Map<String, Object> secrets, final TaskDescriptor task, final WorkflowDescriptor workflow, final RuntimeDescriptor runtime) throws ExpressionError {
		final RuntimeExpressionArguments arguments = new RuntimeExpressionArguments();
		arguments.setInput(input);
		arguments.setContext(context);
		arguments.setSecrets(secrets);
		arguments.setTask(task);
		arguments.setWorkflow(workflow);
		arguments.setRuntime(runtime);
		evaluate(transformedTaskInput, arguments);
	}

	@Override
	public void evaluate(final String transformedTaskInput, final Object input, final Map<String, Object> context, final Map<String, Object> secrets, final TaskDescriptor task, final WorkflowDescriptor workflow, final RuntimeDescriptor runtime, final AuthorizationDescriptor authorization) throws ExpressionError { 
		final RuntimeExpressionArguments arguments = new RuntimeExpressionArguments();
		arguments.setInput(input);
		arguments.setContext(context);
		arguments.setSecrets(secrets);
		arguments.setTask(task);
		arguments.setWorkflow(workflow);
		arguments.setRuntime(runtime);
		arguments.setAuthorization(authorization);
		evaluate(transformedTaskInput, arguments);
	}

	@Override
	public Object evaluate(final String rawTaskOutput, final Map<String, Object> context, final Object input, final Map<String, Object> secrets, final TaskDescriptor task, final WorkflowDescriptor workflow, final RuntimeDescriptor runtime, final AuthorizationDescriptor authorization) throws ExpressionError {
		final RuntimeExpressionArguments arguments = new RuntimeExpressionArguments();
		arguments.setContext(context);
		arguments.setSecrets(secrets);
		arguments.setTask(task);
		arguments.setWorkflow(workflow);
		arguments.setRuntime(runtime);
		arguments.setAuthorization(authorization);
		evaluate(rawTaskOutput, arguments);
		return arguments.getOutput();
	}

	@Override
	public Map<String, Object> evaluate(final String transformedTaskOutput, final Map<String, Object> context, final Object input, final Object output, final Map<String, Object> secrets, final TaskDescriptor task, final WorkflowDescriptor workflow, final RuntimeDescriptor runtime, final AuthorizationDescriptor authorization) throws ExpressionError {
		final RuntimeExpressionArguments arguments = new RuntimeExpressionArguments();
		arguments.setContext(context);
		arguments.setInput(input);
		arguments.setOutput(output);
		arguments.setSecrets(secrets);
		arguments.setTask(task);
		arguments.setWorkflow(workflow);
		arguments.setRuntime(runtime);
		arguments.setAuthorization(authorization);
		evaluate(transformedTaskOutput, arguments);
		return arguments.getContext();
	}

	@Override
	public Object evaluate(final String lastTaskTransformedOutput, final Map<String, Object> context, final Map<String, Object> secrets, final WorkflowDescriptor workflow, final RuntimeDescriptor runtime) throws ExpressionError {
		final RuntimeExpressionArguments arguments = new RuntimeExpressionArguments();
		arguments.setContext(context);
		arguments.setSecrets(secrets);
		arguments.setWorkflow(workflow);
		arguments.setRuntime(runtime);
		evaluate(lastTaskTransformedOutput, arguments);
		return arguments.getOutput();
	}
}
