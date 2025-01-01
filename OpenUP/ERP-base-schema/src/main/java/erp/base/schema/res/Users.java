package erp.base.schema.res;

import java.util.Date;
import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.actions.Actions;
import erp.base.schema.res.users.APIKeys;
import erp.base.schema.res.users.Log;
import erp.base.schema.res.users.Settings;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@PrimaryKeyJoinColumn(name = "partner_id")
@Table(name = "res_users")
@Description("User")
public class Users extends Partner {
	
	@Id
	private int id;

	@Transient
	private Integer partner_id;

	@ManyToOne(targetEntity = Partner.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "partner_id", nullable = false, insertable = false, updatable = false)
	@NotNull
	@Description("Related Partner")
	private Partner partner;
	
	@Column(nullable = false)
	@NotNull
	@Description("Used to log into the system")
	private String login;
	
	@Column
	@Description("Keep empty if you don't want the user to be able to connect on the system.")
	private String password;
	
	@Transient
	@Description("Set Password")
	private String new_password;
	
	@Transient
	private List<Integer> api_key_ids;
	
	@OneToMany(targetEntity = APIKeys.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@Description("API Keys")
	private List<APIKeys> api_keys;
	
	@Column
	@Description("Email Signature")
	private String signature;
	
	@Column
	@DefaultValue("true")
	private Boolean active = true;
	
	@Transient
	@Description("Partner is Active")
	private Boolean active_partner;
	
	@Transient
	private Integer action_id;

	@ManyToOne(targetEntity = Actions.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "action_id")
	@Description("Home Action")
	private Actions action;
	
	@Transient
	private List<Integer> groups_id;
	
	@ManyToMany(targetEntity = Groups.class, fetch = FetchType.LAZY)
	@JoinTable(name = "res_groups_users_rel", joinColumns = {@JoinColumn(name = "uid")}, inverseJoinColumns = {@JoinColumn(name = "gid")})
	@Description("Groups")
	private List<Groups> groups;
	
	@Transient
	private List<Integer> log_ids;
	
	@OneToMany(targetEntity = Log.class, fetch = FetchType.LAZY, mappedBy = "create_uid")
	@Description("User log entries")
	private List<Log> logs;
	
	@Transient
	private List<Integer> device_ids;
	
	@OneToMany(targetEntity = Device.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@Description("User devices")
	private List<Device> devices;
	
	@Transient
	@Description("Latest authentication")
	private Date login_date;
	
	@Column
	@Description("Share User")
	private Boolean share;
	
	@Transient
	@Description("Number of Companies")
	private Integer companies_count;
	
	@Transient
	@Description("Timezone offset")
	private String tz_offset;
	
	@Transient
	private List<Integer> res_users_settings_ids;
	
	@OneToMany(targetEntity = Settings.class, fetch = FetchType.LAZY, mappedBy = "user_id")
	private List<Settings> res_users_settings;
	
	@Transient
	private Integer res_users_settings_id;
	
	@ManyToOne(targetEntity = Settings.class, fetch = FetchType.LAZY)
	@Description("Settings")
	private Settings res_users_settings_;
	
	@Transient
	private Integer company_id;
	
	@ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	@NotNull
	@Description("Company")
	private Company company;
	
	@Transient
	private List<Integer> company_ids;
	
	@ManyToMany(targetEntity = Company.class, fetch = FetchType.LAZY)
	@JoinTable(name = "res_company_users_rel", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "cid")})
	@Description("Companies")
	private List<Company> companies;
	
	@Transient
	private String name;
	
	@Transient
	private String email;
	
	@Transient
	@Description("# Access Rights")
	private Integer accesses_count;
	
	@Transient
	@Description("# Record Rules")
	private Integer rules_count;
	
	@Transient
	@Description("# Groups")
	private Integer groups_count;
}
