package epf.workflow.spi;

import epf.workflow.schema.EventProperties;

public interface EventPropertiesService {

	void persist(final EventProperties event) throws Error;
}
