package erp.base.schema.res.currency;

import java.util.Date;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import erp.base.schema.res.Company;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "res_currency_rate")
@Description("Currency Rate")
@NodeEntity("Currency Rate")
public class Rate {
	
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
	@Description("Date")
	@Property
	private Date name;
	
	/**
	 * 
	 */
	@Column
	@Description("Technical Rate")
	@Property
	private Float rate;
	
	/**
	 * 
	 */
	@Column
	@Description("The currency of rate 1 to the rate of the currency.")
	@Property
	private Float company_rate;
	
	/**
	 * 
	 */
	@Column
	@Description("The rate of the currency to the currency of rate 1 ")
	@Property
	private Float inverse_company_rate;
	
	/**
	 * 
	 */
	@Column(nullable = false, insertable = false, updatable = false)
	@NotNull
	@Description("Currency")
	@Transient
	private Integer currency_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = Currency.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "currency_id", nullable = false, updatable = false)
	@NotNull
	@Relationship(type = "CURRENCY")
	private Currency currency;
	
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

	public Date getName() {
		return name;
	}

	public void setName(Date name) {
		this.name = name;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public Float getCompany_rate() {
		return company_rate;
	}

	public void setCompany_rate(Float company_rate) {
		this.company_rate = company_rate;
	}

	public Float getInverse_company_rate() {
		return inverse_company_rate;
	}

	public void setInverse_company_rate(Float inverse_company_rate) {
		this.inverse_company_rate = inverse_company_rate;
	}

	public Integer getCurrency_id() {
		return currency_id;
	}

	public void setCurrency_id(Integer currency_id) {
		this.currency_id = currency_id;
	}

	public Integer getCompany_id() {
		return company_id;
	}

	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
