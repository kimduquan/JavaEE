package epf.workflow.service;

import epf.workflow.schema.Export;

public interface ExportService {

	void export(final Export export) throws Exception;
}
