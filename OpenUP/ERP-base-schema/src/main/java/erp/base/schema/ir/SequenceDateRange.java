package erp.base.schema.ir;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_sequence_date_range")
@Description("Sequence Date Range")
public class SequenceDateRange {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("From")
	private String date_from;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("To")
	private String date_to;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@ManyToOne(targetEntity = Sequence.class)
	@NotNull
	@Description("Main Sequence")
	private String sequence_id;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("1")
	private Integer number_next = 1;
	
	/**
	 * 
	 */
	@Column
	@Description("Actual Next Number")
	private Integer number_next_actual;
}
