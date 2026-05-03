package epf.workflow.task.internal;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import epf.naming.Naming;
import epf.workflow.schema.EventProperties;
import epf.workflow.schema.RuntimeExpressionArguments;
import epf.workflow.task.EmitService;
import epf.workflow.schema.Emit;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmitServiceImpl implements EmitService {
	
	@Channel(Naming.Workflow.EVENTS)
	transient Emitter<EventProperties> emitter;

	@Override
	public Object emit(final RuntimeExpressionArguments arguments, final Emit task, final Object taskInput) throws Exception {
		final Message<EventProperties> msg = Message.of(task.getEmit().getEvent());
		emitter.send(msg);
		return taskInput;
	}
}
