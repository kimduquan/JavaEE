package erp.base.schema.ir;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

import erp.base.schema.ir.sequence.Date_Range;
import erp.base.schema.res.Company;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ir_sequence")
@Description("Sequence")
public class Sequence {
	
	public enum Implementation {
		@Description("Standard")
		standard,
		@Description("No gap")
		no_gap,
	}
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
	private String name;
	
	@Column
	@Description("Sequence Code")
	private String code;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("standard")
	@Description("Implementation")
	private Implementation implementation = Implementation.standard;
	
	@Column
	@DefaultValue("true")
	private Boolean active = true;
	
	@Column
	@Description("Prefix value of the record for the sequence")
	private String prefix;
	
	@Column
	@Description("Suffix value of the record for the sequence")
	private String suffix;
	
	@Column(nullable = false)
	@NotNull
	@DefaultValue("1")
	@Description("Next Number")
	private Integer number_next = 1;
	
	@Column
	@Description("Actual Next Number")
	private Integer number_next_actual;
	
	@Column(nullable = false)
	@NotNull
	@DefaultValue("1")
	@Description("Step")
	private Integer number_increment = 1;
	
	@Column(nullable = false)
	@NotNull
	@DefaultValue("0")
	@Description("Sequence Size")
	private Integer padding = 0;
	
	@Transient
	private Integer company_id;

	@ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	@Description("Company")
	private Company company;
	
	@Column
	@Description("Use subsequences per date_range")
	private Boolean use_date_range;
	
	@Transient
	private List<Integer> date_range_ids;

	@OneToMany(targetEntity = Date_Range.class, mappedBy = "sequence_id")
	@Description("Subsequences")
	private List<Date_Range> date_ranges;
}
