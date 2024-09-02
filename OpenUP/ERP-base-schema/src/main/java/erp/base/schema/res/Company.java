package erp.base.schema.res;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Relationship.Direction;
import org.neo4j.ogm.annotation.Transient;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import erp.base.schema.ir.ui.View;
import erp.base.schema.report.PaperFormat;
import erp.base.schema.res.country.Country;
import erp.base.schema.res.country.State;
import erp.base.schema.res.currency.Currency;
import erp.base.schema.res.partner.Partner;
import erp.base.schema.res.users.Users;
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
@Table(name = "res_company")
@Description("Companies")
@NodeEntity("Companies")
public class Company {
	
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
	@Description("Company Name")
	@Property
	@Convert(NameAttributeConverter.class)
	private String name;
	
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
	@DefaultValue("10")
	@Property
	private Integer sequence = 10;
	
	/**
	 * 
	 */
	@Column
	@Transient
	private String parent_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Company.class)
	@JoinColumn(name = "parent_id")
	@Relationship(type = "PARENT")
	private Company parent;
	
	/**
	 * 
	 */
	@ElementCollection(targetClass = Company.class)
	@CollectionTable(name = "res_company", joinColumns = {
			@JoinColumn(name = "parent_id")
	})
	@Description("Branches")
	@Transient
	private List<String> child_ids;

	/**
	 * 
	 */
	@OneToMany(targetEntity =  Company.class, mappedBy = "parent_id")
	@Relationship(type = "BRANCHES")
	private List<Company> childs;
	
	/**
	 * 
	 */
	@ElementCollection(targetClass = Company.class)
	@CollectionTable(name = "res_company")
	@Transient
	private List<String> all_child_ids;

	/**
	 * 
	 */
	@OneToMany(targetEntity =  Company.class, mappedBy = "parent_id")
	@Relationship(type = "ALL_CHILDS", direction = Direction.INCOMING)
	private List<Company> all_childs;
	
	/**
	 * 
	 */
	@Column
	private String parent_path;
	
	/**
	 * 
	 */
	@ElementCollection(targetClass = Company.class)
	@CollectionTable(name = "res_company")
	@Transient
	private List<String> parent_ids;
	
	/**
	 * 
	 */
	@OneToMany(targetEntity =  Company.class)
	@Relationship(type = "PARENTS")
	private List<Company> parents;
	
	/**
	 * 
	 */
	@Column
	@Transient
	private String root_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Company.class)
	@JoinColumn(name = "root_id")
	@Relationship(type = "ROOT")
	private Company root;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Partner")
	@Transient
	private String partner_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Partner.class)
	@JoinColumn(name = "partner_id", nullable = false)
	@NotNull
	@Relationship(type = "PARTNER")
	private Partner partner;
	
	/**
	 * 
	 */
	@Column
	@Description("Company Tagline")
	@Property
	private String report_header;
	
	/**
	 * 
	 */
	@Column
	@Description("Report Footer")
	@Property
	private String report_footer;
	
	/**
	 * 
	 */
	@Column
	@Description("Company Details")
	@Property
	private String company_details;
	
	/**
	 * 
	 */
	@Column
	@Property
	private Boolean is_company_details_empty;
	
	/**
	 * 
	 */
	@Column
	@Description("Company Logo")
	@Property
	private byte[] logo;
	
	/**
	 * 
	 */
	@Column
	@Property
	private byte[] logo_web;
	
	/**
	 * 
	 */
	@Column
	@Property
	private Boolean uses_default_logo;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Transient
	private String currency_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Currency.class)
	@JoinColumn(name = "currency_id", nullable = false)
	@NotNull
	@Relationship(type = "CURRENCY")
	private Currency currency;
	
	/**
	 * 
	 */
	@ElementCollection(targetClass = Users.class)
	@CollectionTable(name = "res_company_users_rel", joinColumns = {
			@JoinColumn(name = "cid", referencedColumnName = "user_id")
	})
	@Description("Accepted Users")
	@Transient
	private List<String> user_ids;

	/**
	 * 
	 */
	@ManyToMany(targetEntity = Users.class)
	@JoinTable(name = "res_company_users_rel", joinColumns = {
			@JoinColumn(name = "cid", referencedColumnName = "user_id")
	})
	@Relationship(type = "ACCEPTED_USERS")
	private List<Users> users;
	
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
	@Description("Fed. State")
	@Transient
	private String state_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = State.class)
	@JoinColumn(name = "state_id")
	@Relationship(type = "FED_STATE")
	private State state;
	
	/**
	 * 
	 */
	@Column
	@Transient
	private List<String> bank_ids;
	
	/**
	 * 
	 */
	@OneToMany(targetEntity = Bank.class)
	@Relationship(type = "BANKS")
	private List<Bank> banks;
	
	/**
	 * 
	 */
	@Column
	@Transient
	private String country_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Country.class)
	@JoinColumn(name = "country_id")
	@Relationship(type = "COUNTRY")
	private Country country;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String email;
	
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
	@Property
	private String website;
	
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
	@Column
	@Description("Company ID")
	@Property
	private String company_registry;
	
	/**
	 * 
	 */
	@Column
	@Description("Paper format")
	@Transient
	private String paperformat_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = PaperFormat.class)
	@JoinColumn(name = "paperformat_id")
	@Relationship(type = "PAPER_FORMAT")
	private PaperFormat paperformat;
	
	/**
	 * 
	 */
	@Column
	@Description("Document Template")
	@Transient
	private String external_report_layout_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = View.class)
	@JoinColumn(name = "external_report_layout_id")
	@Relationship(type = "DOCUMENT_TEMPLATE")
	private View external_report_layout;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@DefaultValue("Lato")
	@Property
	private String font = "Lato";
	
	/**
	 * 
	 */
	@Column
	@Property
	private String primary_color;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String secondary_color;
	
	/**
	 * 
	 */
	@Column
	@Property
	private Integer color;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@Description("Blank")
	@Property
	private String layout_background;
	
	/**
	 * 
	 */
	@Column
	@Description("Background Image")
	@Property
	private byte[] layout_background_image;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public List<String> getChild_ids() {
		return child_ids;
	}

	public void setChild_ids(List<String> child_ids) {
		this.child_ids = child_ids;
	}

	public List<String> getAll_child_ids() {
		return all_child_ids;
	}

	public void setAll_child_ids(List<String> all_child_ids) {
		this.all_child_ids = all_child_ids;
	}

	public String getParent_path() {
		return parent_path;
	}

	public void setParent_path(String parent_path) {
		this.parent_path = parent_path;
	}

	public List<String> getParent_ids() {
		return parent_ids;
	}

	public void setParent_ids(List<String> parent_ids) {
		this.parent_ids = parent_ids;
	}

	public String getRoot_id() {
		return root_id;
	}

	public void setRoot_id(String root_id) {
		this.root_id = root_id;
	}

	public String getPartner_id() {
		return partner_id;
	}

	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
	}

	public String getReport_header() {
		return report_header;
	}

	public void setReport_header(String report_header) {
		this.report_header = report_header;
	}

	public String getReport_footer() {
		return report_footer;
	}

	public void setReport_footer(String report_footer) {
		this.report_footer = report_footer;
	}

	public String getCompany_details() {
		return company_details;
	}

	public void setCompany_details(String company_details) {
		this.company_details = company_details;
	}

	public Boolean getIs_company_details_empty() {
		return is_company_details_empty;
	}

	public void setIs_company_details_empty(Boolean is_company_details_empty) {
		this.is_company_details_empty = is_company_details_empty;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	public byte[] getLogo_web() {
		return logo_web;
	}

	public void setLogo_web(byte[] logo_web) {
		this.logo_web = logo_web;
	}

	public Boolean getUses_default_logo() {
		return uses_default_logo;
	}

	public void setUses_default_logo(Boolean uses_default_logo) {
		this.uses_default_logo = uses_default_logo;
	}

	public String getCurrency_id() {
		return currency_id;
	}

	public void setCurrency_id(String currency_id) {
		this.currency_id = currency_id;
	}

	public List<String> getUser_ids() {
		return user_ids;
	}

	public void setUser_ids(List<String> user_ids) {
		this.user_ids = user_ids;
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

	public String getState_id() {
		return state_id;
	}

	public void setState_id(String state_id) {
		this.state_id = state_id;
	}

	public List<String> getBank_ids() {
		return bank_ids;
	}

	public void setBank_ids(List<String> bank_ids) {
		this.bank_ids = bank_ids;
	}

	public String getCountry_id() {
		return country_id;
	}

	public void setCountry_id(String country_id) {
		this.country_id = country_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getVat() {
		return vat;
	}

	public void setVat(String vat) {
		this.vat = vat;
	}

	public String getCompany_registry() {
		return company_registry;
	}

	public void setCompany_registry(String company_registry) {
		this.company_registry = company_registry;
	}

	public String getPaperformat_id() {
		return paperformat_id;
	}

	public void setPaperformat_id(String paperformat_id) {
		this.paperformat_id = paperformat_id;
	}

	public String getExternal_report_layout_id() {
		return external_report_layout_id;
	}

	public void setExternal_report_layout_id(String external_report_layout_id) {
		this.external_report_layout_id = external_report_layout_id;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public String getPrimary_color() {
		return primary_color;
	}

	public void setPrimary_color(String primary_color) {
		this.primary_color = primary_color;
	}

	public String getSecondary_color() {
		return secondary_color;
	}

	public void setSecondary_color(String secondary_color) {
		this.secondary_color = secondary_color;
	}

	public Integer getColor() {
		return color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	public String getLayout_background() {
		return layout_background;
	}

	public void setLayout_background(String layout_background) {
		this.layout_background = layout_background;
	}

	public byte[] getLayout_background_image() {
		return layout_background_image;
	}

	public void setLayout_background_image(byte[] layout_background_image) {
		this.layout_background_image = layout_background_image;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Company getParent() {
		return parent;
	}

	public void setParent(Company parent) {
		this.parent = parent;
	}

	public List<Company> getChilds() {
		return childs;
	}

	public void setChilds(List<Company> childs) {
		this.childs = childs;
	}

	public List<Company> getAll_childs() {
		return all_childs;
	}

	public void setAll_childs(List<Company> all_childs) {
		this.all_childs = all_childs;
	}

	public List<Company> getParents() {
		return parents;
	}

	public void setParents(List<Company> parents) {
		this.parents = parents;
	}

	public Company getRoot() {
		return root;
	}

	public void setRoot(Company root) {
		this.root = root;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<Bank> getBanks() {
		return banks;
	}

	public void setBanks(List<Bank> banks) {
		this.banks = banks;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public PaperFormat getPaperformat() {
		return paperformat;
	}

	public void setPaperformat(PaperFormat paperformat) {
		this.paperformat = paperformat;
	}

	public View getExternal_report_layout() {
		return external_report_layout;
	}

	public void setExternal_report_layout(View external_report_layout) {
		this.external_report_layout = external_report_layout;
	}
}
