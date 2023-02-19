package epf.workflow.schema;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class ActionDataFilters {

	/**
	 * 
	 */
	@Column
	private String fromStateData;
	/**
	 * 
	 */
	@Column
	private boolean useResults = true;
	/**
	 * 
	 */
	@Column
	private String results;
	/**
	 * 
	 */
	@Column
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
