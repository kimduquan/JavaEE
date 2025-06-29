package epf.workflow.service;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import epf.workflow.schema.AsyncAPI;
import epf.workflow.spi.AsyncAPICallService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AsyncAPICallServiceImpl implements AsyncAPICallService {
	
	@Inject
	transient CamelContext camel;
	
	@Inject
    transient ProducerTemplate producer;

	@Override
	public Object call(final AsyncAPI asyncAPI, final Object input) throws Error {
		return null;
	}
}
