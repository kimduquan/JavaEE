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
	
	@Description("Actual Next Number")
	private Integer number_next_actual;
}
