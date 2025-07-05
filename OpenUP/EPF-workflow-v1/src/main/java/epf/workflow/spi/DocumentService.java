package epf.workflow.spi;

import epf.workflow.schema.Document;

public interface DocumentService {

	Document getDocument(final String dsl, final String namespace, final String name, final String version) throws Exception;
}
