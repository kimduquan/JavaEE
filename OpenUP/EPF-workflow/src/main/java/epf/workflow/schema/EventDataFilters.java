package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class EventDataFilters {

	/**
	 * 
	 */
	private boolean useData = true;
	/**
	 * 
	 */
	private String data;
	/**
	 * 
	 */
	private String toStateData;
	
	public boolean isUseData() {
		return useData;
	}
	public void setUseData(boolean useData) {
		this.useData = useData;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getToStateData() {
		return toStateData;
	}
	public void setToStateData(String toStateData) {
		this.toStateData = toStateData;
	}
}
