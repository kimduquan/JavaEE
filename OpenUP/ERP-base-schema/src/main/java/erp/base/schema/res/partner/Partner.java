package erp.base.schema.res.partner;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
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
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "res_partner")
@Description("Contact")
@NodeEntity("Contact")
public class Partner {
	
	/**
	 * 
	 */
	public enum AddressType {
		/**
		 * 
		 */
		contact,
        /**
         * 
         */
        invoice,
        /**
         * 
         */
        delivery,
        /**
         * 
         */
        other
	}
	
	/**
	 * 
	 */
	public enum CompanyType {
		/**
		 * 
		 */
		person,
		/**
		 * 
		 */
		company
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
	@Column
	@Property
	private String name;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String complete_name;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String date;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Title.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "title")
	@Relationship(type = "TTILE")
	private Title title;
	
	/**
	 * 
	 */
	@Column(insertable = false, updatable = false)
	@Description("Related Company")
	@Transient
	private Integer parent_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = Partner.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@Relationship(type = "RELATED_COMPANY")
	private Partner parent;
	
	/**
	 * 
	 */
	@jakarta.persistence.Transient
	@Description("Parent name")
	@Transient
	private String parent_name;
	
	/**
	 * 
	 */
	@ElementCollection
	@CollectionTable(name = "res_partner", joinColumns = {
			@JoinColumn(name = "parent_id", referencedColumnName = "id")
	})
	@Description("Contact")
	@Transient
	private List<Integer> child_ids;

	/**
	 * 
	 */
	@OneToMany(targetEntity = Partner.class, mappedBy = "parent_id")
	@Relationship(type = "CONTACT")
	private List<Partner> childs;
	
	/**
	 * 
	 */
	@Column
	@Description("Reference")
	@Property
	private String ref;
	
	/**
	 * 
	 */
	@Column
	@Description("Language")
	@Property
	private String lang;
	
	/**
	 * 
	 */
	@jakarta.persistence.Transient
	@Transient
	private Integer active_lang_count;
	
	/**
	 * 
	 */
	@Column
	@Description("Timezone")
	@Property
	private String tz;
	
	/**
	 * 
	 */
	@jakarta.persistence.Transient
	@Description("Timezone offset")
	@Transient
	private String tz_offset;
	
	/**
	 * 
	 */
	@Column(insertable = false, updatable = false)
	@Description("Salesperson")
	@Transient
	private Integer user_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Partner.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@Relationship(type = "SALESPERSON")
	private Partner user;
	
	/**
	 * 
	 */
	@Column
	@Description("Tax ID")
	@Property
	private String vat;
	
	/**
	 * 
	 */
	@jakarta.persistence.Transient
	@Description("Partner with same Tax ID")
	@Transient
	private Integer same_vat_partner_id;
	
	/**
	 * 
	 */
	@jakarta.persistence.Transient
	@Description("Partner with same Company Registry")
	@Transient
	private Integer same_company_registry_partner_id;
	
	/**
	 * 
	 */
	@Column
	@Description("Company ID")
	@Property
	private String company_registry;
	
	/**
	 * 
	 */
	@ElementCollection
	@CollectionTable(name = "res_partner_bank", joinColumns = {
			@JoinColumn(name = "partner_id")
	})
	@Description("Banks")
	@Transient
	private List<Integer> bank_ids;

	/**
	 * 
	 */
	@OneToMany(targetEntity = Bank.class, mappedBy = "partner_id")
	@Relationship(type = "BANKS")
	private List<Bank> banks;
	
	/**
	 * 
	 */
	@Column
	@Description("Website Link")
	@Property
	private String website;
	
	/**
	 * 
	 */
	@Column
	@Description("Notes")
	@Property
	private String comment;
	
	/**
	 * 
	 */
	@ElementCollection
	@CollectionTable(name = "res_partner_category", joinColumns = {@JoinColumn(name = "partner_id")})
	@Column(name = "category_id")
	@Description("Tags")
	@Transient
	private List<Integer> category_id;
	
	/**
	 * 
	 */
	@ManyToMany(targetEntity = Category.class)
	@JoinTable(name = "res_partner_category", joinColumns = {@JoinColumn(name = "partner_id")}, inverseJoinColumns = {@JoinColumn(name = "category_id")})
	@Relationship(type = "TAGS")
	private List<Category> category;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Property
	private Boolean active = true;
	
	/**
	 * 
	 */
	@Column
	@Description("Check this box if this contact is an Employee.")
	@Property
	private Boolean employee;
	
	/**
	 * 
	 */
	@Column
	@Description("Job Position")
	@Property
	private String function;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@DefaultValue("contact")
	@Description("Address Type")
	@Property
	private AddressType type = AddressType.contact;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String street;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String street2;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String zip;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String city;
	
	/**
	 * 
	 */
	@Column(insertable = false, updatable = false)
	@Description("State")
	@Transient
	private Integer state_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = State.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "state_id")
	@Relationship(type = "STATE")
	private State state;
	
	/**
	 * 
	 */
	@Column(insertable = false, updatable = false)
	@Description("Country")
	@Transient
	private Integer country_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = Country.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id")
	@Relationship(type = "COUNTRY")
	private Country country;
	
	/**
	 * 
	 */
	@jakarta.persistence.Transient
	@Description("Country Code")
	@Transient
	private String country_code;
	
	/**
	 * 
	 */
	@Column
	@Description("Geo Latitude")
	@Property
	private Float partner_latitude;
	
	/**
	 * 
	 */
	@Column
	@Description("Geo Longitude")
	@Property
	private Float partner_longitude;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String email;
	
	/**
	 * 
	 */
	@jakarta.persistence.Transient
	@Description("Formatted Email")
	@Transient
	private String email_formatted;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String phone;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String mobile;
	
	/**
	 * 
	 */
	@Column
	@Description("Is a Company")
	@Property
	private Boolean is_company = false;
	
	/**
	 * 
	 */
	@jakarta.persistence.Transient
	@Transient
	private Boolean is_public;
	
	/**
	 * 
	 */
	@Column(insertable = false, updatable = false)
	@Description("Industry")
	@Transient
	private Integer industry_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Industry.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "industry_id")
	@Relationship(type = "INDUSTRY")
	private Industry industry;
	
	/**
	 * 
	 */
	@jakarta.persistence.Transient
	@Description("Company Type")
	@Transient
	private CompanyType company_type;
	
	/**
	 * 
	 */
	@Column(insertable = false, updatable = false)
	@Description("Company")
	@Transient
	private Integer company_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	@Relationship(type = "COMPANY")
	private Company company;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("0")
	@Description("Color Index")
	@Property
	private Integer color = 0;
	
	/**
	 * 
	 */
	@ElementCollection
	@CollectionTable(name = "res_users", joinColumns = {
			@JoinColumn(name = "partner_id")
	})
	@Description("Users")
	@Transient
	private List<Integer> user_ids;
	
	/**
	 * 
	 */
	@OneToMany(targetEntity = Users.class, mappedBy = "partner_id")
	@Relationship(type = "USERS")
	private List<Users> users;
	
	/**
	 * 
	 */
	@Column
	@Description("Share Partner")
	@Property
	private Boolean partner_share;
	
	/**
	 * 
	 */
	@jakarta.persistence.Transient
	@Description("Complete Address")
	@Transient
	private String contact_address;
	
	/**
	 * 
	 */
	@Column(insertable = false, updatable = false)
	@Description("Commercial Entity")
	@Transient
	private Integer commercial_partner_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Partner.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "commercial_partner_id")
	@Relationship(type = "COMMERCIAL_ENTITY")
	private Partner commercial_partner;
	
	/**
	 * 
	 */
	@Column
	@Description("Company Name Entity")
	@Property
	private String commercial_company_name;
	
	/**
	 * 
	 */
	@Column
	@Description("Company Name")
	@Property
	private String company_name;
	
	/**
	 * 
	 */
	@jakarta.persistence.Transient
	@Description("Use a barcode to identify this contact.")
	@Transient
	private String barcode;
	
	/**
	 * 
	 */
	@jakarta.persistence.Transient
	@Transient
	private Integer self;

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

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public Integer getParent_id() {
		return parent_id;
	}

	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}

	public String getParent_name() {
		return parent_name;
	}

	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}

	public List<Integer> getChild_ids() {
		return child_ids;
	}

	public void setChild_ids(List<Integer> child_ids) {
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

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getVat() {
		return vat;
	}

	public void setVat(String vat) {
		this.vat = vat;
	}

	public Integer getSame_vat_partner_id() {
		return same_vat_partner_id;
	}

	public void setSame_vat_partner_id(Integer same_vat_partner_id) {
		this.same_vat_partner_id = same_vat_partner_id;
	}

	public Integer getSame_company_registry_partner_id() {
		return same_company_registry_partner_id;
	}

	public void setSame_company_registry_partner_id(Integer same_company_registry_partner_id) {
		this.same_company_registry_partner_id = same_company_registry_partner_id;
	}

	public String getCompany_registry() {
		return company_registry;
	}

	public void setCompany_registry(String company_registry) {
		this.company_registry = company_registry;
	}

	public List<Integer> getBank_ids() {
		return bank_ids;
	}

	public void setBank_ids(List<Integer> bank_ids) {
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

	public List<Integer> getCategory_id() {
		return category_id;
	}

	public void setCategory_id(List<Integer> category_id) {
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

	public AddressType getType() {
		return type;
	}

	public void setType(AddressType type) {
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

	public Integer getState_id() {
		return state_id;
	}

	public void setState_id(Integer state_id) {
		this.state_id = state_id;
	}

	public Integer getCountry_id() {
		return country_id;
	}

	public void setCountry_id(Integer country_id) {
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

	public Integer getIndustry_id() {
		return industry_id;
	}

	public void setIndustry_id(Integer industry_id) {
		this.industry_id = industry_id;
	}

	public CompanyType getCompany_type() {
		return company_type;
	}

	public void setCompany_type(CompanyType company_type) {
		this.company_type = company_type;
	}

	public Integer getCompany_id() {
		return company_id;
	}

	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}

	public Integer getColor() {
		return color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	public List<Integer> getUser_ids() {
		return user_ids;
	}

	public void setUser_ids(List<Integer> user_ids) {
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

	public Integer getCommercial_partner_id() {
		return commercial_partner_id;
	}

	public void setCommercial_partner_id(Integer commercial_partner_id) {
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Partner getParent() {
		return parent;
	}

	public void setParent(Partner parent) {
		this.parent = parent;
	}

	public List<Partner> getChilds() {
		return childs;
	}

	public void setChilds(List<Partner> childs) {
		this.childs = childs;
	}

	public Partner getUser() {
		return user;
	}

	public void setUser(Partner user) {
		this.user = user;
	}

	public List<Bank> getBanks() {
		return banks;
	}

	public void setBanks(List<Bank> banks) {
		this.banks = banks;
	}

	public List<Category> getCategory() {
		return category;
	}

	public void setCategory(List<Category> category) {
		this.category = category;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Industry getIndustry() {
		return industry;
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}

	public Partner getCommercial_partner() {
		return commercial_partner;
	}

	public void setCommercial_partner(Partner commercial_partner) {
		this.commercial_partner = commercial_partner;
	}

	public Integer getSelf() {
		return self;
	}

	public void setSelf(Integer self) {
		this.self = self;
	}
}
