package epf.workflow.schema.state;

public enum Type {
	event,
	operation,
	switch_,
	sleep,
	parallel,
	inject,
	foreach,
	callback;

	public static final String EVENT = "event";
	public static final String OPERATION = "operation";
	public static final String SWITCH = "switch";
	public static final String SLEEP = "sleep";
	public static final String PARALLEL = "parallel";
	public static final String INJECT = "inject";
	public static final String FOREACH = "foreach";
	public static final String CALLBACK = "callback";
}
