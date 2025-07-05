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
import jakarta.persistence.JoinColumn;
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
	
	@OneToMany(targetEntity = Rate.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "currency_id")
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIso_numeric() {
		return iso_numeric;
	}

	public void setIso_numeric(Integer iso_numeric) {
		this.iso_numeric = iso_numeric;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public Float getInverse_rate() {
		return inverse_rate;
	}

	public void setInverse_rate(Float inverse_rate) {
		this.inverse_rate = inverse_rate;
	}

	public String getRate_string() {
		return rate_string;
	}

	public void setRate_string(String rate_string) {
		this.rate_string = rate_string;
	}

	public List<Integer> getRate_ids() {
		return rate_ids;
	}

	public void setRate_ids(List<Integer> rate_ids) {
		this.rate_ids = rate_ids;
	}

	public List<Rate> getRates() {
		return rates;
	}

	public void setRates(List<Rate> rates) {
		this.rates = rates;
	}

	public Float getRounding() {
		return rounding;
	}

	public void setRounding(Float rounding) {
		this.rounding = rounding;
	}

	public Integer getDecimal_places() {
		return decimal_places;
	}

	public void setDecimal_places(Integer decimal_places) {
		this.decimal_places = decimal_places;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public SymbolPosition getPosition() {
		return position;
	}

	public void setPosition(SymbolPosition position) {
		this.position = position;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCurrency_unit_label() {
		return currency_unit_label;
	}

	public void setCurrency_unit_label(String currency_unit_label) {
		this.currency_unit_label = currency_unit_label;
	}

	public String getCurrency_subunit_label() {
		return currency_subunit_label;
	}

	public void setCurrency_subunit_label(String currency_subunit_label) {
		this.currency_subunit_label = currency_subunit_label;
	}

	public Boolean getIs_current_company_currency() {
		return is_current_company_currency;
	}

	public void setIs_current_company_currency(Boolean is_current_company_currency) {
		this.is_current_company_currency = is_current_company_currency;
	}
}
