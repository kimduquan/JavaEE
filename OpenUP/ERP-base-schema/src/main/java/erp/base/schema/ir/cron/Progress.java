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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getCron_id() {
		return cron_id;
	}

	public void setCron_id(Integer cron_id) {
		this.cron_id = cron_id;
	}

	public Cron getCron() {
		return cron;
	}

	public void setCron(Cron cron) {
		this.cron = cron;
	}

	public Integer getRemaining() {
		return remaining;
	}

	public void setRemaining(Integer remaining) {
		this.remaining = remaining;
	}

	public Integer getDone() {
		return done;
	}

	public void setDone(Integer done) {
		this.done = done;
	}

	public boolean isDeactivate() {
		return deactivate;
	}

	public void setDeactivate(boolean deactivate) {
		this.deactivate = deactivate;
	}

	public Integer getTimed_out_counter() {
		return timed_out_counter;
	}

	public void setTimed_out_counter(Integer timed_out_counter) {
		this.timed_out_counter = timed_out_counter;
	}
}
