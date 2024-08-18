package erp.schema.res;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.schema.ReportPaperFormat;
import erp.schema.ir.ui.View;
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
@Table(name = "res_company")
@Description("Companies")
public class Company {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Company Name")
	private String name;
	
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
	@DefaultValue("10")
	private Integer sequence = 10;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Company.class)
	private String parent_id;
	
	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity =  Company.class)
	@ElementCollection(targetClass = Company.class)
	@CollectionTable(name = "res_company")
	@Description("Branches")
	private List<String> child_ids;
	
	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity =  Company.class)
	@ElementCollection(targetClass = Company.class)
	@CollectionTable(name = "res_company")
	private List<String> all_child_ids;
	
	/**
	 * 
	 */
	@Column
	private String parent_path;
	
	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity =  Company.class)
	@ElementCollection(targetClass = Company.class)
	@CollectionTable(name = "res_company")
	private List<String> parent_ids;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Company.class)
	private String root_id;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@ManyToOne(targetEntity = Partner.class)
	@NotNull
	@Description("Partner")
	private String partner_id;
	
	/**
	 * 
	 */
	@Column
	@Description("Company Tagline")
	private String report_header;
	
	/**
	 * 
	 */
	@Column
	@Description("Report Footer")
	private String report_footer;
	
	/**
	 * 
	 */
	@Column
	@Description("Company Details")
	private String company_details;
	
	/**
	 * 
	 */
	@Column
	private Boolean is_company_details_empty;
	
	/**
	 * 
	 */
	@Column
	@Description("Company Logo")
	private byte[] logo;
	
	/**
	 * 
	 */
	@Column
	private byte[] logo_web;
	
	/**
	 * 
	 */
	@Column
	private Boolean uses_default_logo;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@ManyToOne(targetEntity = Currency.class)
	@NotNull
	private String currency_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = Users.class)
	@ElementCollection(targetClass = Users.class)
	@CollectionTable(name = "res_users")
	@Description("Accepted Users")
	private List<String> user_ids;
	
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
	@ManyToOne(targetEntity = CountryState.class)
	@Description("Fed. State")
	private String state_id;
	
	/**
	 * 
	 */
	@Column
	@OneToMany
	private List<String> bank_ids;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Country.class)
	private String country_id;
	
	/**
	 * 
	 */
	@Column
	private String email;
	
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
	private String website;
	
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
	@Description("Company ID")
	private String company_registry;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = ReportPaperFormat.class)
	@Description("Paper format")
	private String paperformat_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = View.class)
	@Description("Document Template")
	private String external_report_layout_id;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@DefaultValue("Lato")
	private String font = "Lato";
	
	/**
	 * 
	 */
	@Column
	private String primary_color;
	
	/**
	 * 
	 */
	@Column
	private String secondary_color;
	
	/**
	 * 
	 */
	@Column
	private Integer color;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@Description("Blank")
	private String layout_background;
	
	/**
	 * 
	 */
	@Column
	@Description("Background Image")
	private byte[] layout_background_image;
}
