package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class WorkflowState {
	
	/**
	 * 
	 */
	public static final String Event = "Event";
	/**
	 * 
	 */
	public static final String Operation = "Operation";
	/**
	 * 
	 */
	public static final String Switch = "Switch";
	/**
	 * 
	 */
	public static final String Sleep = "Sleep";
	/**
	 * 
	 */
	public static final String Parallel = "Parallel";
	/**
	 * 
	 */
	public static final String Inject = "Inject";
	/**
	 * 
	 */
	public static final String ForEach = "ForEach";
	/**
	 * 
	 */
	public static final String Callback = "Callback";

	/**
	 * 
	 */
	private String name;
	
	/**
	 * 
	 */
	private String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
