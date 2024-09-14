package erp.base.schema.ir;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import erp.base.schema.res.Company;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
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
@NodeEntity("Sequence")
public class Sequence {
	
	/**
	 * 
	 */
	public enum Implementation {
		/**
		 * 
		 */
		standard,
		/**
		 * 
		 */
		no_gap,
	}
	
	/**
	 * 
	 */
	@jakarta.persistence.Id
	@Id
	private int id;

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Property
	private String name;
	
	/**
	 * 
	 */
	@Column
	@Description("Sequence Code")
	@Property
	private String code;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("standard")
	@Description("Implementation")
	@Property
	private Implementation implementation = Implementation.standard;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Property
	private Boolean active = true;
	
	/**
	 * 
	 */
	@Column
	@Description("Prefix value of the record for the sequence")
	@Property
	private String prefix;
	
	/**
	 * 
	 */
	@Column
	@Description("Suffix value of the record for the sequence")
	@Property
	private String suffix;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("1")
	@Description("Next Number")
	@Property
	private Integer number_next = 1;
	
	/**
	 * 
	 */
	@Column
	@Description("Actual Next Number")
	@Property
	private Integer number_next_actual;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("1")
	@Description("Step")
	@Property
	private Integer number_increment = 1;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("0")
	@Description("Sequence Size")
	@Property
	private Integer padding = 0;
	
	/**
	 * 
	 */
	@Column(insertable = false, updatable = false)
	@Description("Company")
	@Transient
	private Integer company_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	@Relationship(type = "COMPANY")
	private Company company;
	
	/**
	 * 
	 */
	@Column
	@Description("Use subsequences per date_range")
	@Property
	private Boolean use_date_range;
	
	/**
	 * 
	 */
	@ElementCollection
	@CollectionTable(name = "ir_sequence_date_range", joinColumns = {
			@JoinColumn(name = "sequence_id")
	})
	@Description("Subsequences")
	@Transient
	private List<Integer> date_range_ids;

	/**
	 * 
	 */
	@OneToMany(targetEntity = DateRange.class, mappedBy = "sequence_id")
	@Relationship(type = "SUBSEQUENCES")
	private List<DateRange> date_ranges;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Implementation getImplementation() {
		return implementation;
	}

	public void setImplementation(Implementation implementation) {
		this.implementation = implementation;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
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

	public Integer getNumber_increment() {
		return number_increment;
	}

	public void setNumber_increment(Integer number_increment) {
		this.number_increment = number_increment;
	}

	public Integer getPadding() {
		return padding;
	}

	public void setPadding(Integer padding) {
		this.padding = padding;
	}

	public Integer getCompany_id() {
		return company_id;
	}

	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}

	public Boolean getUse_date_range() {
		return use_date_range;
	}

	public void setUse_date_range(Boolean use_date_range) {
		this.use_date_range = use_date_range;
	}

	public List<Integer> getDate_range_ids() {
		return date_range_ids;
	}

	public void setDate_range_ids(List<Integer> date_range_ids) {
		this.date_range_ids = date_range_ids;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<DateRange> getDate_ranges() {
		return date_ranges;
	}

	public void setDate_ranges(List<DateRange> date_ranges) {
		this.date_ranges = date_ranges;
	}
}
