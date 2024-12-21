package erp.base.schema.ir.cron;

import java.util.Date;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.Cron;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "ir_cron_trigger")
@Description("Triggered actions")
public class Trigger {
	
	@Id
	private int id;

	@Transient
	private Integer cron_id;
	
	@ManyToOne(targetEntity = Cron.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "cron_id")
	private Cron cron;
	
	@Column
	private Date call_at;
}
