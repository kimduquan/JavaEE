package epf.workflow.spi;

import epf.workflow.schema.Evaluate;

public interface EvaluateService {

	void evaluate(final Evaluate evaluate) throws Exception;
}
