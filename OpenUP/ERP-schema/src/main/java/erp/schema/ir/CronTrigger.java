package erp.schema.ir;

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
public class CronTrigger {

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
}
