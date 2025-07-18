package erp.base.schema.res;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.ui.View;
import erp.base.schema.res.country.Group;
import erp.base.schema.res.country.State;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "res_country")
@Description("Country")
public class Country {
	
	public enum NamePosition {
		@Description("Before Address")
		before,
		@Description("After Address")
        after
	}
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
	@Description("Country Name")
	private String name;
	
	@Column(nullable = false, length = 2)
	@NotNull
	@Description("Country Code")
	private String code;
	
	@Column
	@DefaultValue("%(street)s\\n%(street2)s\\n%(city)s %(state_code)s %(zip)s\\n%(country_name)s")
	@Description("Layout in Reports")
	private String address_format = "%(street)s\\n%(street2)s\\n%(city)s %(state_code)s %(zip)s\\n%(country_name)s";
	
	@Transient
	private Integer address_view_id;

	@ManyToOne(targetEntity = View.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "address_view_id")
	@Description("Input View")
	private View address_view;
	
	@Transient
	private Integer currency_id;
	
	@ManyToOne(targetEntity = Currency.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "currency_id")
	@Description("Currency")
	private Currency currency;
	
	@Transient
	@Description("Flag")
	private String image_url;
	
	@Column
	@Description("Country Calling Code")
	private Integer phone_code;
	
	@Transient
	private List<Integer> country_group_ids;
	
	@ManyToMany(targetEntity = Group.class, fetch = FetchType.LAZY)
	@JoinTable(name = "res_country_res_country_group_rel", joinColumns = {@JoinColumn(name = "res_country_id")}, inverseJoinColumns = {@JoinColumn(name = "res_country_group_id")})
	@Description("Country Groups")
	private List<Group> country_groups;
	
	@Transient
	private List<Integer> state_ids;

	@OneToMany(targetEntity = State.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id")
	@Description("States")
	private List<State> states;
	
	@Column
	@Enumerated(EnumType.STRING)
	@DefaultValue("before")
	@Description("Customer Name Position")
	private NamePosition name_position = NamePosition.before;
	
	@Column
	@Description("Vat Label")
	private String vat_label;
	
	@Column
	@DefaultValue("false")
	private Boolean state_required = false;
	
	@Column
	@DefaultValue("true")
	private Boolean zip_required = true;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAddress_format() {
		return address_format;
	}

	public void setAddress_format(String address_format) {
		this.address_format = address_format;
	}

	public Integer getAddress_view_id() {
		return address_view_id;
	}

	public void setAddress_view_id(Integer address_view_id) {
		this.address_view_id = address_view_id;
	}

	public View getAddress_view() {
		return address_view;
	}

	public void setAddress_view(View address_view) {
		this.address_view = address_view;
	}

	public Integer getCurrency_id() {
		return currency_id;
	}

	public void setCurrency_id(Integer currency_id) {
		this.currency_id = currency_id;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public Integer getPhone_code() {
		return phone_code;
	}

	public void setPhone_code(Integer phone_code) {
		this.phone_code = phone_code;
	}

	public List<Integer> getCountry_group_ids() {
		return country_group_ids;
	}

	public void setCountry_group_ids(List<Integer> country_group_ids) {
		this.country_group_ids = country_group_ids;
	}

	public List<Group> getCountry_groups() {
		return country_groups;
	}

	public void setCountry_groups(List<Group> country_groups) {
		this.country_groups = country_groups;
	}

	public List<Integer> getState_ids() {
		return state_ids;
	}

	public void setState_ids(List<Integer> state_ids) {
		this.state_ids = state_ids;
	}

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}

	public NamePosition getName_position() {
		return name_position;
	}

	public void setName_position(NamePosition name_position) {
		this.name_position = name_position;
	}

	public String getVat_label() {
		return vat_label;
	}

	public void setVat_label(String vat_label) {
		this.vat_label = vat_label;
	}

	public Boolean getState_required() {
		return state_required;
	}

	public void setState_required(Boolean state_required) {
		this.state_required = state_required;
	}

	public Boolean getZip_required() {
		return zip_required;
	}

	public void setZip_required(Boolean zip_required) {
		this.zip_required = zip_required;
	}
}
