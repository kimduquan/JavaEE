package epf.workflow.action.schema;

import jakarta.nosql.Column;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class Sleep {

	/**
	 * 
	 */
	@Column
	private String before;
	/**
	 * 
	 */
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
