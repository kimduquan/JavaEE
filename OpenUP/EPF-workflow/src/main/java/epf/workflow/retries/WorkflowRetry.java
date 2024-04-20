package epf.workflow.retries;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

import epf.nosql.schema.EitherUtil;
import epf.workflow.error.WorkflowException;
import epf.workflow.schema.RetryDefinition;
import epf.workflow.schema.WorkflowDefinition;
import epf.workflow.schema.WorkflowError;
import epf.workflow.schema.action.ActionDefinition;

/**
 * @author PC
 *
 */
public class WorkflowRetry<T> implements Callable<T> {

	/**
	 * 
	 */
	private final Callable<T> callable;
	
	/**
	 * 
	 */
	private final ActionDefinition actionDefinition;
	
	/**
	 * 
	 */
	private final WorkflowDefinition workflowDefinition;

	/**
	 * @param callable
	 * @param workflowDefinition
	 * @param actionDefinition
	 */
	public WorkflowRetry(Callable<T> callable, WorkflowDefinition workflowDefinition, ActionDefinition actionDefinition) {
		this.callable = callable;
		this.actionDefinition = actionDefinition;
		this.workflowDefinition = workflowDefinition;
	}
	
	@Override
	public T call() throws Exception {
		final RetryDefinition retryDefinition = getRetryDefinition(workflowDefinition, actionDefinition.getRetryRef());
		Duration delay = null;
		if(retryDefinition.getDelay() != null) {
			delay = Duration.parse(retryDefinition.getDelay());
		}
		Integer maxAttempts = null;
		if(retryDefinition.getMaxAttempts() instanceof String) {
			maxAttempts = Integer.parseInt((String)retryDefinition.getMaxAttempts());
		}
		else if(retryDefinition.getMaxAttempts() instanceof Integer) {
			maxAttempts = (Integer) retryDefinition.getMaxAttempts();
		}
		Duration increment = null;
		if(retryDefinition.getIncrement() != null) {
			increment = Duration.parse(retryDefinition.getIncrement());
		}
		Float multiplier = null;
		if(retryDefinition.getMultiplier() instanceof String) {
			multiplier = Float.valueOf((String)retryDefinition.getMultiplier());
		}
		else if(retryDefinition.getMultiplier() instanceof Float) {
			multiplier = (Float) retryDefinition.getMultiplier();
		}
		Duration maxDelay = null;
		if(retryDefinition.getMaxDelay() != null) {
			maxDelay = Duration.parse(retryDefinition.getMaxDelay());
		}
		int attempts = 0;
		boolean retry = true;
		while(retry && attempts < maxAttempts) {
			try {
				attempts++;
				return callable.call();
			}
			catch(WorkflowException ex) {
				retry = false;
				if(workflowDefinition.getAutoRetries() && actionDefinition.getNonRetryableErrors() != null) {
					final Optional<WorkflowError> nonRetryableError = actionDefinition.getNonRetryableErrors().stream().filter(err -> err.equals(ex.getWorkflowError())).findFirst();
					retry = !nonRetryableError.isPresent();
				}
				else if(!workflowDefinition.getAutoRetries() && actionDefinition.getRetryableErrors() != null) {
					final Optional<WorkflowError> retryableErrors = actionDefinition.getRetryableErrors().stream().filter(err -> err.equals(ex.getWorkflowError())).findFirst();
					retry = retryableErrors.isPresent();
				}
				if(!retry) {
					throw ex;
				}
				if(retry && delay != null) {
					Thread.sleep(delay.toMillis());
					Duration newDelay = delay;
					if(increment != null) {
						newDelay = newDelay.plus(increment);
					}
					if(multiplier != null) {
						newDelay = Duration.ofMillis((long)(newDelay.toMillis() * multiplier));
					}
					if(maxDelay != null && newDelay.compareTo(maxDelay) > 0) {
						newDelay = maxDelay;
					}
					delay = newDelay;
				}
			}
		}
		return null;
	}
	
	private RetryDefinition getRetryDefinition(final WorkflowDefinition workflowDefinition, final String retryRef) {
		final List<RetryDefinition> retries = EitherUtil.getArray(workflowDefinition.getRetries());
		return retries.stream().filter(retry -> retry.getName().equals(retryRef)).findFirst().orElse(null);
	}
}
