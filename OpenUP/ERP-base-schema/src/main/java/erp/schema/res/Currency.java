package erp.schema.res;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "res_currency")
@Description("Currency")
public class Currency {

	/**
	 * 
	 */
	@Column(nullable = false, length = 3)
	@NotNull
	@Description("Currency")
	private String name;
	
	/**
	 * 
	 */
	@Column
	@Description("Name")
	private String full_name;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	private String symbol;
	
	/**
	 * 
	 */
	@Column
	@Description("Current Rate")
	private Float rate;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	private Float inverse_rate;
	
	/**
	 * 
	 */
	@Column
	private String rate_string;
	
	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity = CurrencyRate.class)
	@ElementCollection(targetClass = CurrencyRate.class)
	@CollectionTable(name = "res_currency_rate")
	@Description("Rates")
	private List<String> rate_ids;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("0.01")
	@Description("Rounding Factor")
	private Float rounding = (float)0.01;
	
	/**
	 * 
	 */
	@Column
	private Integer decimal_places;
	
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
	@Enumerated(EnumType.STRING)
	@DefaultValue("after")
	@Description("Symbol Position")
	private String position = "after";
	
	/**
	 * 
	 */
	@Column
	private String date;
	
	/**
	 * 
	 */
	@Column
	@Description("Currency Unit")
	private String currency_unit_label;
	
	/**
	 * 
	 */
	@Column
	@Description("Currency Subunit")
	private String currency_subunit_label;
	
	/**
	 * 
	 */
	@Column
	private Boolean is_current_company_currency;
}
