package epf.workflow;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import epf.naming.Naming;
import epf.workflow.schedule.Schedule;
import epf.workflow.schedule.ScheduleTrigger;
import epf.workflow.schedule.schema.ScheduleDefinition;
import epf.workflow.schedule.util.ScheduleUtil;
import epf.workflow.schema.StartDefinition;
import epf.workflow.schema.WorkflowDefinition;

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
	 */
	public void schedule(final WorkflowDefinition workflowDefinition) {
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
			final Schedule schedule = new Schedule(workflowRuntime, workflowDefinition);
			final ScheduleTrigger trigger = ScheduleUtil.parseInterval(interval);
			scheduleService.schedule(schedule, trigger);
		}
	}
}
