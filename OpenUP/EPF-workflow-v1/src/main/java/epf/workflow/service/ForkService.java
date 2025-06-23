package epf.workflow.service;

import epf.workflow.schema.Fork;

public interface ForkService {

	void fork(final Fork fork) throws Exception;
}
