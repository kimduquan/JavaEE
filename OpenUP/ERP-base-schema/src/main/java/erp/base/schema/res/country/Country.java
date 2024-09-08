package erp.base.schema.res.country;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import erp.base.schema.ir.ui.View;
import erp.base.schema.res.currency.Currency;
import erp.schema.util.NameAttributeConverter;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "res_country")
@Description("Country")
@NodeEntity("Country")
public class Country {
	
	/**
	 * 
	 */
	public enum CustomerNamePosition {
		/**
		 * 
		 */
		before,
        /**
         * 
         */
        after
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
	@Description("Country Name")
	@Property
	@Convert(NameAttributeConverter.class)
	private String name;
	
	/**
	 * 
	 */
	@Column(nullable = false, length = 2)
	@NotNull
	@Description("Country Code")
	@Property
	private String code;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("%(street)s\\n%(street2)s\\n%(city)s %(state_code)s %(zip)s\\n%(country_name)s")
	@Description("Layout in Reports")
	@Property
	private String address_format = "%(street)s\\n%(street2)s\\n%(city)s %(state_code)s %(zip)s\\n%(country_name)s";
	
	/**
	 * 
	 */
	@Column
	@Description("Input View")
	@Transient
	private Integer address_view_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = View.class)
	@JoinColumn(name = "address_view_id")
	@Relationship(type = "INPUT_VIEW")
	private View address_view;
	
	/**
	 * 
	 */
	@Column
	@Description("Currency")
	@Transient
	private Integer currency_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Currency.class)
	@JoinColumn(name = "currency_id")
	@Relationship(type = "CURRENCY")
	private Currency currency;
	
	/**
	 * 
	 */
	@Column
	@Description("Flag")
	@Property
	private String image_url;
	
	/**
	 * 
	 */
	@Column
	@Description("Country Calling Code")
	@Property
	private Integer phone_code;
	
	/**
	 * 
	 */
	@ElementCollection
	@CollectionTable(name = "res_country_res_country_group_rel", joinColumns = {@JoinColumn(name = "res_country_id")})
	@Column(name = "res_country_group_id")
	@Description("Country Groups")
	@Transient
	private List<Integer> country_group_ids;
	
	/**
	 * 
	 */
	@ManyToMany(targetEntity = Group.class)
	@JoinTable(name = "res_country_res_country_group_rel", joinColumns = {@JoinColumn(name = "res_country_id")}, inverseJoinColumns = {@JoinColumn(name = "res_country_group_id")})
	@Relationship(type = "COUNTRY_GROUPS")
	private List<Group> country_groups;
	
	/**
	 * 
	 */
	@ElementCollection
	@CollectionTable(name = "res_country_state", joinColumns = {
			@JoinColumn(name = "country_id")
	})
	@Description("States")
	@Transient
	private List<Integer> state_ids;

	/**
	 * 
	 */
	@OneToMany(targetEntity = State.class, mappedBy = "country_id")
	@Relationship(type = "STATES")
	private List<State> states;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@DefaultValue("before")
	@Description("Customer Name Position")
	@Property
	private CustomerNamePosition name_position = CustomerNamePosition.before;
	
	/**
	 * 
	 */
	@Column
	@Description("Vat Label")
	@Property
	private String vat_label;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("false")
	@Property
	private Boolean state_required = false;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Property
	private Boolean zip_required = true;

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

	public Integer getCurrency_id() {
		return currency_id;
	}

	public void setCurrency_id(Integer currency_id) {
		this.currency_id = currency_id;
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

	public List<Integer> getState_ids() {
		return state_ids;
	}

	public void setState_ids(List<Integer> state_ids) {
		this.state_ids = state_ids;
	}

	public CustomerNamePosition getName_position() {
		return name_position;
	}

	public void setName_position(CustomerNamePosition name_position) {
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public View getAddress_view() {
		return address_view;
	}

	public void setAddress_view(View address_view) {
		this.address_view = address_view;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public List<Group> getCountry_groups() {
		return country_groups;
	}

	public void setCountry_groups(List<Group> country_groups) {
		this.country_groups = country_groups;
	}

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}
}
