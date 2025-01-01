package erp.base.schema.res;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.country.State;
import erp.base.schema.res.partner.Bank;
import erp.base.schema.res.partner.Category;
import erp.base.schema.res.partner.Industry;
import erp.base.schema.res.partner.Title;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "res_partner")
@Description("Contact")
public class Partner {
	
	public enum AddressType {
		@Description("Contact")
		contact,
		@Description("Invoice Address")
        invoice,
        @Description("Delivery Address")
        delivery,
        @Description("Other Address")
        other
	}
	
	public enum CompanyType {
		@Description("Individual")
		person,
		@Description("Company")
		company
	}
	
	@Id
	private int id;

	@Column
	private String name;
	
	@Column
	private String complete_name;
	
	@ManyToOne(targetEntity = Title.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "title")
	private Title title;
	
	@Transient
	private Integer parent_id;

	@ManyToOne(targetEntity = Partner.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@Description("Related Company")
	private Partner parent;
	
	@Transient
	@Description("Parent name")
	private String parent_name;
	
	@Transient
	private List<Integer> child_ids;

	@OneToMany(targetEntity = Partner.class, mappedBy = "parent_id")
	@Description("Contact")
	private List<Partner> childs;
	
	@Column
	@Description("Reference")
	private String ref;
	
	@Column
	@Description("Language")
	private String lang;
	
	@Transient
	private Integer active_lang_count;
	
	@Column
	@Description("Timezone")
	private String tz;
	
	@Transient
	@Description("Timezone offset")
	private String tz_offset;
	
	@Transient
	private Integer user_id;
	
	@ManyToOne(targetEntity = Partner.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@Description("Salesperson")
	private Partner user;
	
	@Column
	@Description("Tax ID")
	private String vat;
	
	@Transient
	@Description("Partner with same Tax ID")
	private Integer same_vat_partner_id;
	
	@Transient
	@Description("Partner with same Company Registry")
	private Integer same_company_registry_partner_id;
	
	@Column
	@Description("Company ID")
	private String company_registry;
	
	@Transient
	private List<Integer> bank_ids;

	@OneToMany(targetEntity = Bank.class, fetch = FetchType.LAZY, mappedBy = "partner_id")
	@Description("Banks")
	private List<Bank> banks;
	
	@Column
	@Description("Website Link")
	private String website;
	
	@Column
	@Description("Notes")
	private String comment;
	
	@Transient
	private List<Integer> category_id;
	
	@ManyToMany(targetEntity = Category.class, fetch = FetchType.LAZY)
	@JoinTable(name = "res_partner_category", joinColumns = {@JoinColumn(name = "partner_id")}, inverseJoinColumns = {@JoinColumn(name = "category_id")})
	@Description("Tags")
	private List<Category> category;
	
	@Column
	@DefaultValue("true")
	private Boolean active = true;
	
	@Column
	@Description("Check this box if this contact is an Employee.")
	private Boolean employee;
	
	@Column
	@Description("Job Position")
	private String function;
	
	@Column
	@Enumerated(EnumType.STRING)
	@DefaultValue("contact")
	@Description("Address Type")
	private AddressType type = AddressType.contact;
	
	@Column
	private String street;
	
	@Column
	private String street2;
	
	@Column
	private String zip;
	
	@Column
	private String city;
	
	@Transient
	private Integer state_id;
	
	@ManyToOne(targetEntity = State.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "state_id")
	@Description("State")
	private State state;
	
	@Transient
	private Integer country_id;

	@ManyToOne(targetEntity = Country.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id")
	@Description("Country")
	private Country country;
	
	@Transient
	@Description("Country Code")
	private String country_code;
	
	@Column
	@Description("Geo Latitude")
	private Float partner_latitude;
	
	@Column
	@Description("Geo Longitude")
	private Float partner_longitude;
	
	@Column
	private String email;
	
	@Transient
	@Description("Formatted Email")
	private String email_formatted;
	
	@Column
	private String phone;
	
	@Column
	private String mobile;
	
	@Column
	@Description("Is a Company")
	private Boolean is_company = false;
	
	@Column
	private Boolean is_public;
	
	@Transient
	private Integer industry_id;
	
	@ManyToOne(targetEntity = Industry.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "industry_id")
	@Description("Industry")
	private Industry industry;
	
	@Transient
	@Description("Company Type")
	private CompanyType company_type;
	
	@Transient
	private Integer company_id;
	
	@ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	@Description("Company")
	private Company company;
	
	@Column
	@DefaultValue("0")
	@Description("Color Index")
	private Integer color = 0;
	
	@Transient
	private List<Integer> user_ids;
	
	@OneToMany(targetEntity = Users.class, fetch = FetchType.LAZY, mappedBy = "partner_id")
	@Description("Users")
	private List<Users> users;
	
	@Column
	@Description("Share Partner")
	private Boolean partner_share;
	
	@Transient
	@Description("Complete Address")
	private String contact_address;
	
	@Transient
	private Integer commercial_partner_id;
	
	@ManyToOne(targetEntity = Partner.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "commercial_partner_id")
	@Description("Commercial Entity")
	private Partner commercial_partner;
	
	@Column
	@Description("Company Name Entity")
	private String commercial_company_name;
	
	@Column
	@Description("Company Name")
	private String company_name;
	
	@Description("Use a barcode to identify this contact.")
	private String barcode;
}
