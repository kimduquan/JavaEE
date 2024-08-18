package erp.base.schema.ir;

import java.util.List;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

import erp.base.schema.res.Company;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_sequence")
@Description("Sequence")
public class Sequence {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	private String name;
	
	/**
	 * 
	 */
	@Column
	@Description("Sequence Code")
	private String code;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("standard")
	@Description("Implementation")
	private String implementation = "standard";
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	private Boolean active = true;
	
	/**
	 * 
	 */
	@Column
	@Description("Prefix value of the record for the sequence")
	private String prefix;
	
	/**
	 * 
	 */
	@Column
	@Description("Suffix value of the record for the sequence")
	private String suffix;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("1")
	@Description("Next Number")
	private Integer number_next = 1;
	
	/**
	 * 
	 */
	@Column
	@Description("Actual Next Number")
	private Integer number_next_actual;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("1")
	@Description("Step")
	private Integer number_increment = 1;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("0")
	@Description("Sequence Size")
	private Integer padding = 0;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Company.class)
	@Description("Company")
	private String company_id;
	
	/**
	 * 
	 */
	@Column
	@Description("Use subsequences per date_range")
	private Boolean use_date_range;
	
	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity = SequenceDateRange.class)
	@ElementCollection(targetClass = SequenceDateRange.class)
	@CollectionTable(name = "ir_sequence_date_range")
	@Description("Subsequences")
	private List<String> date_range_ids;
}
