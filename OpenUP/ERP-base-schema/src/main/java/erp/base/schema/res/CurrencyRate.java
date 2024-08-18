package erp.base.schema.res;

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
@Table(name = "res_currency_rate")
@Description("Currency Rate")
public class CurrencyRate {

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
}
