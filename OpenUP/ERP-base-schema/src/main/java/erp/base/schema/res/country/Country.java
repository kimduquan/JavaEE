package erp.base.schema.res.country;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
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
	@ManyToOne(targetEntity = View.class)
	@Description("Input View")
	@Property
	@Relationship(type = "ADDRESS_VIEW")
	private String address_view_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Currency.class)
	@Description("Currency")
	@Property
	@Relationship(type = "CURRENCY")
	private String currency_id;
	
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
	@Column
	@ManyToMany(targetEntity = Group.class)
	@ElementCollection(targetClass = Group.class)
	@CollectionTable(name = "res_country_group")
	@Description("Country Groups")
	@Property
	@Relationship(type = "COUNTRY_GROUPS")
	private List<String> country_group_ids;
	
	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity = State.class)
	@ElementCollection(targetClass = State.class)
	@CollectionTable(name = "res_country_state")
	@Description("States")
	@Property
	@Relationship(type = "STATES")
	private List<String> state_ids;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@DefaultValue("before")
	@Description("Customer Name Position")
	@Property
	private String name_position = "before";
	
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

	public String getAddress_view_id() {
		return address_view_id;
	}

	public void setAddress_view_id(String address_view_id) {
		this.address_view_id = address_view_id;
	}

	public String getCurrency_id() {
		return currency_id;
	}

	public void setCurrency_id(String currency_id) {
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

	public List<String> getCountry_group_ids() {
		return country_group_ids;
	}

	public void setCountry_group_ids(List<String> country_group_ids) {
		this.country_group_ids = country_group_ids;
	}

	public List<String> getState_ids() {
		return state_ids;
	}

	public void setState_ids(List<String> state_ids) {
		this.state_ids = state_ids;
	}

	public String getName_position() {
		return name_position;
	}

	public void setName_position(String name_position) {
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
}
