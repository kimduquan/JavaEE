package erp.base.schema.ir.cron;

import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "ir_cron_trigger")
@Description("Triggered actions")
public class Trigger {

	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Cron.class)
	private String cron_id;
	
	/**
	 * 
	 */
	@Column
	private String call_at;

	public String getCron_id() {
		return cron_id;
	}

	public void setCron_id(String cron_id) {
		this.cron_id = cron_id;
	}

	public String getCall_at() {
		return call_at;
	}

	public void setCall_at(String call_at) {
		this.call_at = call_at;
	}
}
