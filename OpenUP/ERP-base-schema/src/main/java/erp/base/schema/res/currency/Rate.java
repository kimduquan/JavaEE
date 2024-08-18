package erp.base.schema.res.currency;

import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.Company;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "res_currency_rate")
@Description("Currency Rate")
public class Rate {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Date")
	private String name;
	
	/**
	 * 
	 */
	@Column
	@Description("Technical Rate")
	private Float rate;
	
	/**
	 * 
	 */
	@Column
	@Description("The currency of rate 1 to the rate of the currency.")
	private Float company_rate;
	
	/**
	 * 
	 */
	@Column
	@Description("The rate of the currency to the currency of rate 1 ")
	private Float inverse_company_rate;
	
	/**
	 * 
	 */
	@Column(nullable = false, updatable = false)
	@ManyToOne(targetEntity = Currency.class)
	@NotNull
	@Description("Currency")
	private String currency_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Company.class)
	@Description("Company")
	private String company_id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
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

	public String getCurrency_id() {
		return currency_id;
	}

	public void setCurrency_id(String currency_id) {
		this.currency_id = currency_id;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
}
