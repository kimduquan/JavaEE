package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class ActionDataFilters {

	/**
	 * 
	 */
	private String fromStateData;
	/**
	 * 
	 */
	private boolean useResults = true;
	/**
	 * 
	 */
	private String results;
	/**
	 * 
	 */
	private String toStateData;
	
	public String getFromStateData() {
		return fromStateData;
	}
	public void setFromStateData(String fromStateData) {
		this.fromStateData = fromStateData;
	}
	public boolean isUseResults() {
		return useResults;
	}
	public void setUseResults(boolean useResults) {
		this.useResults = useResults;
	}
	public String getResults() {
		return results;
	}
	public void setResults(String results) {
		this.results = results;
	}
	public String getToStateData() {
		return toStateData;
	}
	public void setToStateData(String toStateData) {
		this.toStateData = toStateData;
	}
}
