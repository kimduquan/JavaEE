package epf.workflow;

import java.net.URI;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import epf.naming.Naming;
import epf.workflow.schedule.Schedule;
import epf.workflow.schedule.ScheduleTrigger;
import epf.workflow.schedule.util.ScheduleUtil;
import epf.workflow.schema.StartDefinition;
import epf.workflow.schema.WorkflowDefinition;
import epf.workflow.schema.schedule.ScheduleDefinition;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class WorkflowSchedule {
	
	/**
	 * 
	 */
	@Inject
	transient WorkflowRuntime workflowRuntime;
	
	/**
	 * 
	 */
	@Resource(lookup = Naming.Schedule.EXECUTOR_SERVICE)
	transient ManagedScheduledExecutorService scheduleService;

	/**
	 * @param workflowDefinition
	 * @param uri
	 */
	public void schedule(final WorkflowDefinition workflowDefinition, final URI uri) {
		final StartDefinition startDefinition = (StartDefinition) workflowDefinition.getStart();
		String interval = null;
		ScheduleDefinition scheduleDefinition = null;
		if(startDefinition.getSchedule() instanceof String) {
			interval = (String) startDefinition.getSchedule();
		}
		else if(startDefinition.getSchedule() instanceof ScheduleDefinition) {
			scheduleDefinition = (ScheduleDefinition) startDefinition.getSchedule();
		}
		if(scheduleDefinition != null) {
			interval = scheduleDefinition.getInterval();
		}
		if(interval != null) {
			final Schedule schedule = new Schedule(workflowRuntime, workflowDefinition, uri);
			final ScheduleTrigger trigger = ScheduleUtil.parseInterval(interval);
			scheduleService.schedule(schedule, trigger);
		}
	}
}
