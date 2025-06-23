package epf.workflow.service;

import java.util.Date;
import java.util.Map;

public interface TaskService {

	void create(final String workflow, final String task, final Date createdAt) throws Exception;
	void start(final String workflow, final String task, final Date startedAt) throws Exception;
	void suspend(final String workflow, final String task, final Date suspendedAt) throws Exception;
	void resume(final String workflow, final String task, final Date resumedAt) throws Exception;
	void retry(final String workflow, final String task, final Date retriedAt) throws Exception;
	void cancel(final String workflow, final String task, final Date cancelledAt) throws Exception;
	void fault(final String workflow, final String task, final Date faultedAt, final Error error) throws Exception;
	void complete(final String workflow, final String task, final Date completedAt) throws Exception;
	void complete(final String workflow, final String task, final Date completedAt, final Map<String, String> output) throws Exception;
	void changeStatus(final String workflow, final String task, final Date updatedAt, final String status) throws Exception;
}
