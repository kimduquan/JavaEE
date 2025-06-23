package epf.workflow.spi;

import epf.workflow.schema.SwitchCase;

public interface SwitchCaseService {

	void switchCase(final SwitchCase switchCase) throws Exception;
}
