package epf.workflow.task.call.service;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

import epf.workflow.task.call.schema.AsyncAPI;
import epf.workflow.task.call.spi.AsyncAPICallService;
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
		return input;
	}
}
