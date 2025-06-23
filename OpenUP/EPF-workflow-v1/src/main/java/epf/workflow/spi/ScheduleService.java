package epf.workflow.spi;

import epf.workflow.schema.Schedule;

public interface ScheduleService {

	void schedule(final Schedule schedule) throws Exception;
}
