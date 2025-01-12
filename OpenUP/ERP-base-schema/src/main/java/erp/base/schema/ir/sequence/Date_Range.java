package erp.base.schema.ir.sequence;

import java.util.Date;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.Sequence;
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
@Table(name = "ir_sequence_date_range")
@Description("Sequence Date Range")
public class Date_Range {
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
	@Description("From")
	private Date date_from;
	
	@Column(nullable = false)
	@NotNull
	@Description("To")
	private Date date_to;
	
	@Transient
	private Integer sequence_id;

	@ManyToOne(targetEntity = Sequence.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "sequence_id", nullable = false)
	@NotNull
	@Description("Main Sequence")
	private Sequence sequence;
	
	@Column(nullable = false)
	@NotNull
	@DefaultValue("1")
	private Integer number_next = 1;
	
	@Transient
	@Description("Actual Next Number")
	private Integer number_next_actual;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate_from() {
		return date_from;
	}

	public void setDate_from(Date date_from) {
		this.date_from = date_from;
	}

	public Date getDate_to() {
		return date_to;
	}

	public void setDate_to(Date date_to) {
		this.date_to = date_to;
	}

	public Integer getSequence_id() {
		return sequence_id;
	}

	public void setSequence_id(Integer sequence_id) {
		this.sequence_id = sequence_id;
	}

	public Sequence getSequence() {
		return sequence;
	}

	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}

	public Integer getNumber_next() {
		return number_next;
	}

	public void setNumber_next(Integer number_next) {
		this.number_next = number_next;
	}

	public Integer getNumber_next_actual() {
		return number_next_actual;
	}

	public void setNumber_next_actual(Integer number_next_actual) {
		this.number_next_actual = number_next_actual;
	}
}
