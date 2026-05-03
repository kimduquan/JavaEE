package epf.workflow.task.call.internal;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import epf.workflow.schema.AsyncAPICall;
import epf.workflow.task.call.AsyncAPICallService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AsyncAPICallServiceImpl implements AsyncAPICallService {
	
	@Inject
	transient CamelContext camel;
	
	@Inject
    transient ProducerTemplate producer;

	@Override
	public Object call(final AsyncAPICall asyncAPI, final Object input) throws Exception {
		return input;
	}
}
