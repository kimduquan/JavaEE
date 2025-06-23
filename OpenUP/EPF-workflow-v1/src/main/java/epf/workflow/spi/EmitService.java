package epf.workflow.spi;

import epf.workflow.schema.Emit;

public interface EmitService {

	void emit(final Emit emit) throws Exception;
}
