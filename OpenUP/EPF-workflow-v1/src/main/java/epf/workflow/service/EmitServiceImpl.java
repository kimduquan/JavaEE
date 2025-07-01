package epf.workflow.service;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import epf.naming.Naming;
import epf.workflow.schema.Error;
import epf.workflow.schema.EventProperties;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.spi.EmitService;
import epf.workflow.task.schema.EmitTask;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmitServiceImpl implements EmitService {
	
	@Channel(Naming.Workflow.EVENTS)
	transient Emitter<EventProperties> emitter;

	@Override
	public Object emit(final RuntimeExpressionArguments arguments, final EmitTask task, final Object taskInput) throws Error {
		final Message<EventProperties> msg = Message.of(task.getEmit().getEvent());
		emitter.send(msg);
		return taskInput;
	}
}
