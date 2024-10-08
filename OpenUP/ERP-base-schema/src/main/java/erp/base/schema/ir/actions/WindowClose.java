package erp.base.schema.ir.actions;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "ir_actions")
@Description("Action Window Close")
public class WindowClose extends Actions {

	/**
	 * 
	 */
	@Column
	@DefaultValue("ir.actions.act_window_close")
	private String type = "ir.actions.act_window_close";

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
