package epf.workflow.schema.action;

import jakarta.nosql.Column;
import java.io.Serializable;
import jakarta.nosql.Embeddable;

@Embeddable
public class Sleep implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column
	private String before;
	
	@Column
	private String after;
	
	public String getBefore() {
		return before;
	}
	public void setBefore(String before) {
		this.before = before;
	}
	public String getAfter() {
		return after;
	}
	public void setAfter(String after) {
		this.after = after;
	}
}
