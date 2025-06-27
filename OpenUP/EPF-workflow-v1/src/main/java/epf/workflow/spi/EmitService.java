package epf.workflow.spi;

import epf.workflow.task.schema.Emit;

public interface EmitService {

	void emit(final Emit emit) throws Exception;
}
