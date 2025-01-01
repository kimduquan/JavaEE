package erp.base.schema.res;

import java.util.Date;
import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.currency.Rate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "res_currency")
@Description("Currency")
public class Currency {
	
	public enum SymbolPosition {
		@Description("After Amount")
		after,
		@Description("Before Amount")
		before
	}
	
	@Id
	private int id;

	@Column(nullable = false, length = 3)
	@NotNull
	@Description("Currency")
	private String name;
	
	@Column
	@Description("Currency numeric code.")
	private Integer iso_numeric;
	
	@Column
	@Description("Name")
	private String full_name;
	
	@Column(nullable = false)
	@NotNull
	private String symbol;
	
	@Transient
	@Description("Current Rate")
	private Float rate;
	
	@Transient
	private Float inverse_rate;
	
	@Transient
	private String rate_string;
	
	@Transient
	private List<Integer> rate_ids;
	
	@OneToMany(targetEntity = Rate.class, fetch = FetchType.LAZY, mappedBy = "currency_id")
	@Description("Rates")
	private List<Rate> rates;
	
	@Column
	@DefaultValue("0.01")
	@Description("Rounding Factor")
	private Float rounding = (float)0.01;
	
	@Column
	private Integer decimal_places;
	
	@Column
	@DefaultValue("true")
	private Boolean active = true;
	
	@Column
	@Enumerated(EnumType.STRING)
	@DefaultValue("after")
	@Description("Symbol Position")
	private SymbolPosition position = SymbolPosition.after;
	
	@Transient
	private Date date;
	
	@Column
	@Description("Currency Unit")
	private String currency_unit_label;
	
	@Column
	@Description("Currency Subunit")
	private String currency_subunit_label;
	
	@Transient
	private Boolean is_current_company_currency;
}
