package epf.workflow.schema;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class EventDataFilters {

	/**
	 * 
	 */
	@Column
	private Boolean useData = true;
	/**
	 * 
	 */
	@Column
	private String data;
	/**
	 * 
	 */
	@Column
	private String toStateData;
	
	public Boolean isUseData() {
		return useData;
	}
	public void setUseData(Boolean useData) {
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
