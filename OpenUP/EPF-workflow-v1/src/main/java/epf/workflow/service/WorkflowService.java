package epf.workflow.service;

import java.util.Date;
import java.util.Map;
import epf.workflow.schema.WorkflowDefinitionReference;

public interface WorkflowService {
	
	void start(final String name, final WorkflowDefinitionReference definition, final Date startedAt) throws Exception;
	void suspend(final String name, final Date suspendedAt) throws Exception;
	void resume(final String name, final Date resumedAt) throws Exception;
	void startCorrelation(final String name, final Date startedAt) throws Exception;
	void completeCorrelation(final String name, final Date completedAt) throws Exception;
	void completeCorrelation(final String name, final Date completedAt, final Map<String, String> correlationKeys) throws Exception;
	void cancel(final String name, final Date cancelledAt) throws Exception;
	void fault(final String name, final Date faultedAt, final Error error) throws Exception;
	void complete(final String name, final Date completedAt) throws Exception;
	void complete(final String name, final Date completedAt, final Map<String, String> output) throws Exception;
	void changeStatus(final String name, final Date updatedAt, final String status) throws Exception;
}
