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
}
