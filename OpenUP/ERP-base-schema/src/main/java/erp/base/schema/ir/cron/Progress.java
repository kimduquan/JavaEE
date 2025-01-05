package erp.base.schema.ir.cron;

import org.eclipse.microprofile.graphql.DefaultValue;
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
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ir_cron_progress")
@Description("Progress of Scheduled Actions")
public class Progress {
	
	@Id
	private int id;

	@Transient
	private Integer cron_id;
	
	@ManyToOne(targetEntity = Cron.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "cron_id", nullable = false)
	@NotNull
	private Cron cron;
	
	@Column
	@DefaultValue("0")
	private Integer remaining = 0;
	
	@Column
	@DefaultValue("0")
	private Integer done = 0;
	
	@Column
	private boolean deactivate;
	
	@Column
	@DefaultValue("0")
	private Integer timed_out_counter = 0;
}
