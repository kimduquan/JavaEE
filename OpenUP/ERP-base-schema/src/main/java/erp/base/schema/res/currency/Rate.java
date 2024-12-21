package erp.base.schema.res.currency;

import java.util.Date;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.Company;
import erp.base.schema.res.Currency;
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
@Table(name = "res_currency_rate")
@Description("Currency Rate")
public class Rate {
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
	@Description("Date")
	private Date name;
	
	@Column
	@Description("Technical Rate")
	private Float rate;
	
	@Column
	@Description("The currency of rate 1 to the rate of the currency.")
	private Float company_rate;
	
	@Description("The rate of the currency to the currency of rate 1 ")
	private Float inverse_company_rate;
	
	@Transient
	private Integer currency_id;

	@ManyToOne(targetEntity = Currency.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "currency_id", nullable = false, updatable = false)
	@NotNull
	@Description("Currency")
	private Currency currency;
	
	@Transient
	private Integer company_id;
	
	@ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	@Description("Company")
	private Company company;
}
