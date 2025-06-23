package epf.workflow.spi;

import epf.workflow.schema.Export;

public interface ExportService {

	void export(final Export export) throws Exception;
}
