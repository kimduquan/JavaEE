package epf.workflow.schema.action;

import jakarta.nosql.Column;
import java.io.Serializable;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class ActionDataFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@Column
	private String fromStateData;
	/**
	 * 
	 */
	@Column
	private Boolean useResults = true;
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
	public Boolean isUseResults() {
		return useResults;
	}
	public void setUseResults(Boolean useResults) {
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
