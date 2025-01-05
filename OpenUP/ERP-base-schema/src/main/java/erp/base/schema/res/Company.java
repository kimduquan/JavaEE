package erp.base.schema.res;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.ui.View;
import erp.base.schema.report.PaperFormat;
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
@Table(name = "res_company")
@Description("Companies")
public class Company {
	
	public enum Font {
		@Description("Lato")
		Lato,
		@Description("Roboto")
		Roboto,
		@Description("Open Sans")
		Open_Sans,
		@Description("Montserrat")
		Montserrat,
		@Description("Oswald")
		Oswald,
		@Description("Raleway")
		Raleway,
		@Description("Tajawal")
		Tajawal,
		@Description("Fira Mono")
		Fira_Mono
	}
	
	public enum LayoutBackground {
		@Description("Blank")
		Blank,
		@Description("Demo logo")
		Geometric,
		@Description("Custom")
		Custom
	}
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
	@Description("Company Name")
	private String name;
	
	@Column
	@DefaultValue("true")
	private Boolean active = true;
	
	@Column
	@DefaultValue("10")
	private Integer sequence = 10;
	
	@Transient
	private Integer parent_id;
	
	@ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", referencedColumnName = "id")
	@Description("Parent Company")
	private Company parent;
	
	@Transient
	private List<Integer> child_ids;

	@OneToMany(targetEntity =  Company.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@Description("Branches")
	private List<Company> childs;
	
	@Transient
	private List<Integer> all_child_ids;

	@OneToMany(targetEntity =  Company.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private List<Company> all_childs;
	
	@Column
	private String parent_path;
	
	@Transient
	private List<Integer> parent_ids;
	
	@Transient
	private Integer root_id;
	
	@Transient
	private Integer partner_id;
	
	@ManyToOne(targetEntity = Partner.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "partner_id", nullable = false)
	@NotNull
	@Description("Partner")
	private Partner partner;
	
	@Column
	@Description("Company Tagline")
	private String report_header;
	
	@Column
	@Description("Report Footer")
	private String report_footer;
	
	@Column
	@Description("Company Details")
	private String company_details;
	
	@Transient
	private Boolean is_company_details_empty;
	
	@Transient
	@Description("Company Logo")
	private byte[] logo;
	
	@Column
	private byte[] logo_web;
	
	@Column
	private Boolean uses_default_logo;
	
	@Transient
	private Integer currency_id;
	
	@ManyToOne(targetEntity = Currency.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "currency_id", nullable = false)
	@NotNull
	@Description("Currency")
	private Currency currency;
	
	@Transient
	private List<Integer> user_ids;

	@ManyToMany(targetEntity = Users.class)
	@JoinTable(name = "res_company_users_rel", joinColumns = {@JoinColumn(name = "cid")}, inverseJoinColumns = {@JoinColumn(name = "user_id")})
	@Description("Accepted Users")
	private List<Users> users;
	
	@Transient
	private String street;
	
	@Transient
	private String street2;
	
	@Transient
	private String zip;
	
	@Transient
	private String city;
	
	@Transient
	@Description("Fed. State")
	private Integer state_id;
	
	@Transient
	private List<Integer> bank_ids;
	
	@Transient
	@Description("Country")
	private Integer country_id;
	
	@Transient
	private String country_code;
	
	@Column
	private String email;
	
	@Column
	private String phone;
	
	@Column
	private String mobile;
	
	@Transient
	private String website;
	
	@Transient
	@Description("Tax ID")
	private String vat;
	
	@Transient
	@Description("Company ID")
	private String company_registry;
	
	@Transient
	private Integer paperformat_id;

	@ManyToOne(targetEntity = PaperFormat.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "paperformat_id")
	@Description("Paper format")
	private PaperFormat paperformat;
	
	@Transient
	private Integer external_report_layout_id;

	@ManyToOne(targetEntity = View.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "external_report_layout_id")
	@Description("Document Template")
	private View external_report_layout;
	
	@Column
	@Enumerated(EnumType.STRING)
	@DefaultValue("Lato")
	private Font font = Font.Lato;
	
	@Column
	private String primary_color;
	
	@Column
	private String secondary_color;
	
	@Transient
	private Integer color;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("Blank")
	private LayoutBackground layout_background = LayoutBackground.Blank;
	
	@Transient
	@Description("Background Image")
	private byte[] layout_background_image;
	
	@Transient
	private List<Integer> uninstalled_l10n_module_ids;
}
