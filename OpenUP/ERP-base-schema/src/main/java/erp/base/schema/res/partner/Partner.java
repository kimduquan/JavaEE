package erp.base.schema.res.partner;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.Bank;
import erp.base.schema.res.Company;
import erp.base.schema.res.country.Country;
import erp.base.schema.res.country.State;
import erp.base.schema.res.users.Users;
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

/**
 * 
 */
@Entity
@Table(name = "res_partner")
@Description("Contact")
public class Partner {

	/**
	 * 
	 */
	@Column
	private String name;
	
	/**
	 * 
	 */
	@Column
	private String complete_name;
	
	/**
	 * 
	 */
	@Column
	private String date;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Title.class)
	private String title;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Partner.class)
	@Description("Related Company")
	private String parent_id;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Parent name")
	private String parent_name;
	
	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity = Partner.class)
	@Description("Contact")
	private List<String> child_ids;
	
	/**
	 * 
	 */
	@Column
	@Description("Reference")
	private String ref;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@Description("Language")
	private String lang;
	
	/**
	 * 
	 */
	@Column
	private Integer active_lang_count;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@Description("Timezone")
	private String tz;
	
	/**
	 * 
	 */
	@Column
	@Description("Timezone offset")
	private String tz_offset;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Partner.class)
	@Description("Salesperson")
	private String user_id;
	
	/**
	 * 
	 */
	@Column
	@Description("Tax ID")
	private String vat;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Partner.class)
	@Description("Partner with same Tax ID")
	private String same_vat_partner_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Partner.class)
	@Description("Partner with same Company Registry")
	private String same_company_registry_partner_id;
	
	/**
	 * 
	 */
	@Column
	@Description("Company ID")
	private String company_registry;
	
	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity = Bank.class)
	@Description("Banks")
	private List<String> bank_ids;
	
	/**
	 * 
	 */
	@Column
	@Description("Website Link")
	private String website;
	
	/**
	 * 
	 */
	@Column
	@Description("Notes")
	private String comment;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = Category.class)
	@Description("Tags")
	private String category_id;
	
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
	@Description("Check this box if this contact is an Employee.")
	private Boolean employee;
	
	/**
	 * 
	 */
	@Column
	@Description("Job Position")
	private String function;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@DefaultValue("contact")
	@Description("Address Type")
	private String type = "contact";
	
	/**
	 * 
	 */
	@Column
	private String street;
	
	/**
	 * 
	 */
	@Column
	private String street2;
	
	/**
	 * 
	 */
	@Column
	private String zip;
	
	/**
	 * 
	 */
	@Column
	private String city;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = State.class)
	@Description("State")
	private String state_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Country.class)
	@Description("Country")
	private String country_id;
	
	/**
	 * 
	 */
	@Column
	@Description("Country Code")
	private String country_code;
	
	/**
	 * 
	 */
	@Column
	@Description("Geo Latitude")
	private Float partner_latitude;
	
	/**
	 * 
	 */
	@Column
	@Description("Geo Longitude")
	private Float partner_longitude;
	
	/**
	 * 
	 */
	@Column
	private String email;
	
	/**
	 * 
	 */
	@Column
	@Description("Formatted Email")
	private String email_formatted;
	
	/**
	 * 
	 */
	@Column
	private String phone;
	
	/**
	 * 
	 */
	@Column
	private String mobile;
	
	/**
	 * 
	 */
	@Column
	@Description("Is a Company")
	private Boolean is_company = false;
	
	/**
	 * 
	 */
	@Column
	private Boolean is_public;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Industry.class)
	@Description("Industry")
	private String industry_id;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@Description("Company Type")
	private String company_type;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Company.class)
	@Description("Company")
	private String company_id;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("0")
	@Description("Color Index")
	private Integer color = 0;
	
	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity = Users.class)
	@ElementCollection(targetClass = Users.class)
	@CollectionTable(name = "res_users")
	@Description("Users")
	private List<String> user_ids;
	
	/**
	 * 
	 */
	@Column
	@Description("Share Partner")
	private Boolean partner_share;
	
	/**
	 * 
	 */
	@Column
	@Description("Complete Address")
	private String contact_address;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Partner.class)
	@Description("Commercial Entity")
	private String commercial_partner_id;
	
	/**
	 * 
	 */
	@Column
	@Description("Company Name Entity")
	private String commercial_company_name;
	
	/**
	 * 
	 */
	@Column
	@Description("Company Name")
	private String company_name;
	
	/**
	 * 
	 */
	@Column
	@Description("Use a barcode to identify this contact.")
	private String barcode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComplete_name() {
		return complete_name;
	}

	public void setComplete_name(String complete_name) {
		this.complete_name = complete_name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getParent_name() {
		return parent_name;
	}

	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}

	public List<String> getChild_ids() {
		return child_ids;
	}

	public void setChild_ids(List<String> child_ids) {
		this.child_ids = child_ids;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public Integer getActive_lang_count() {
		return active_lang_count;
	}

	public void setActive_lang_count(Integer active_lang_count) {
		this.active_lang_count = active_lang_count;
	}

	public String getTz() {
		return tz;
	}

	public void setTz(String tz) {
		this.tz = tz;
	}

	public String getTz_offset() {
		return tz_offset;
	}

	public void setTz_offset(String tz_offset) {
		this.tz_offset = tz_offset;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getVat() {
		return vat;
	}

	public void setVat(String vat) {
		this.vat = vat;
	}

	public String getSame_vat_partner_id() {
		return same_vat_partner_id;
	}

	public void setSame_vat_partner_id(String same_vat_partner_id) {
		this.same_vat_partner_id = same_vat_partner_id;
	}

	public String getSame_company_registry_partner_id() {
		return same_company_registry_partner_id;
	}

	public void setSame_company_registry_partner_id(String same_company_registry_partner_id) {
		this.same_company_registry_partner_id = same_company_registry_partner_id;
	}

	public String getCompany_registry() {
		return company_registry;
	}

	public void setCompany_registry(String company_registry) {
		this.company_registry = company_registry;
	}

	public List<String> getBank_ids() {
		return bank_ids;
	}

	public void setBank_ids(List<String> bank_ids) {
		this.bank_ids = bank_ids;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getEmployee() {
		return employee;
	}

	public void setEmployee(Boolean employee) {
		this.employee = employee;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState_id() {
		return state_id;
	}

	public void setState_id(String state_id) {
		this.state_id = state_id;
	}

	public String getCountry_id() {
		return country_id;
	}

	public void setCountry_id(String country_id) {
		this.country_id = country_id;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public Float getPartner_latitude() {
		return partner_latitude;
	}

	public void setPartner_latitude(Float partner_latitude) {
		this.partner_latitude = partner_latitude;
	}

	public Float getPartner_longitude() {
		return partner_longitude;
	}

	public void setPartner_longitude(Float partner_longitude) {
		this.partner_longitude = partner_longitude;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail_formatted() {
		return email_formatted;
	}

	public void setEmail_formatted(String email_formatted) {
		this.email_formatted = email_formatted;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Boolean getIs_company() {
		return is_company;
	}

	public void setIs_company(Boolean is_company) {
		this.is_company = is_company;
	}

	public Boolean getIs_public() {
		return is_public;
	}

	public void setIs_public(Boolean is_public) {
		this.is_public = is_public;
	}

	public String getIndustry_id() {
		return industry_id;
	}

	public void setIndustry_id(String industry_id) {
		this.industry_id = industry_id;
	}

	public String getCompany_type() {
		return company_type;
	}

	public void setCompany_type(String company_type) {
		this.company_type = company_type;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public Integer getColor() {
		return color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	public List<String> getUser_ids() {
		return user_ids;
	}

	public void setUser_ids(List<String> user_ids) {
		this.user_ids = user_ids;
	}

	public Boolean getPartner_share() {
		return partner_share;
	}

	public void setPartner_share(Boolean partner_share) {
		this.partner_share = partner_share;
	}

	public String getContact_address() {
		return contact_address;
	}

	public void setContact_address(String contact_address) {
		this.contact_address = contact_address;
	}

	public String getCommercial_partner_id() {
		return commercial_partner_id;
	}

	public void setCommercial_partner_id(String commercial_partner_id) {
		this.commercial_partner_id = commercial_partner_id;
	}

	public String getCommercial_company_name() {
		return commercial_company_name;
	}

	public void setCommercial_company_name(String commercial_company_name) {
		this.commercial_company_name = commercial_company_name;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
}
