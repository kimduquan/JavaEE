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
	
	@OneToMany(targetEntity = Log.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "create_uid")
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
	
	@OneToMany(targetEntity = Settings.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private List<Settings> res_users_settings;
	
	@Transient
	@Description("Settings")
	private Integer res_users_settings_id;
	
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getPartner_id() {
		return partner_id;
	}

	public void setPartner_id(Integer partner_id) {
		this.partner_id = partner_id;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNew_password() {
		return new_password;
	}

	public void setNew_password(String new_password) {
		this.new_password = new_password;
	}

	public List<Integer> getApi_key_ids() {
		return api_key_ids;
	}

	public void setApi_key_ids(List<Integer> api_key_ids) {
		this.api_key_ids = api_key_ids;
	}

	public List<APIKeys> getApi_keys() {
		return api_keys;
	}

	public void setApi_keys(List<APIKeys> api_keys) {
		this.api_keys = api_keys;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getActive_partner() {
		return active_partner;
	}

	public void setActive_partner(Boolean active_partner) {
		this.active_partner = active_partner;
	}

	public Integer getAction_id() {
		return action_id;
	}

	public void setAction_id(Integer action_id) {
		this.action_id = action_id;
	}

	public Actions getAction() {
		return action;
	}

	public void setAction(Actions action) {
		this.action = action;
	}

	public List<Integer> getGroups_id() {
		return groups_id;
	}

	public void setGroups_id(List<Integer> groups_id) {
		this.groups_id = groups_id;
	}

	public List<Groups> getGroups() {
		return groups;
	}

	public void setGroups(List<Groups> groups) {
		this.groups = groups;
	}

	public List<Integer> getLog_ids() {
		return log_ids;
	}

	public void setLog_ids(List<Integer> log_ids) {
		this.log_ids = log_ids;
	}

	public List<Log> getLogs() {
		return logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}

	public List<Integer> getDevice_ids() {
		return device_ids;
	}

	public void setDevice_ids(List<Integer> device_ids) {
		this.device_ids = device_ids;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public Date getLogin_date() {
		return login_date;
	}

	public void setLogin_date(Date login_date) {
		this.login_date = login_date;
	}

	public Boolean getShare() {
		return share;
	}

	public void setShare(Boolean share) {
		this.share = share;
	}

	public Integer getCompanies_count() {
		return companies_count;
	}

	public void setCompanies_count(Integer companies_count) {
		this.companies_count = companies_count;
	}

	public String getTz_offset() {
		return tz_offset;
	}

	public void setTz_offset(String tz_offset) {
		this.tz_offset = tz_offset;
	}

	public List<Integer> getRes_users_settings_ids() {
		return res_users_settings_ids;
	}

	public void setRes_users_settings_ids(List<Integer> res_users_settings_ids) {
		this.res_users_settings_ids = res_users_settings_ids;
	}

	public List<Settings> getRes_users_settings() {
		return res_users_settings;
	}

	public void setRes_users_settings(List<Settings> res_users_settings) {
		this.res_users_settings = res_users_settings;
	}

	public Integer getRes_users_settings_id() {
		return res_users_settings_id;
	}

	public void setRes_users_settings_id(Integer res_users_settings_id) {
		this.res_users_settings_id = res_users_settings_id;
	}

	public Integer getCompany_id() {
		return company_id;
	}

	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Integer> getCompany_ids() {
		return company_ids;
	}

	public void setCompany_ids(List<Integer> company_ids) {
		this.company_ids = company_ids;
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getAccesses_count() {
		return accesses_count;
	}

	public void setAccesses_count(Integer accesses_count) {
		this.accesses_count = accesses_count;
	}

	public Integer getRules_count() {
		return rules_count;
	}

	public void setRules_count(Integer rules_count) {
		this.rules_count = rules_count;
	}

	public Integer getGroups_count() {
		return groups_count;
	}

	public void setGroups_count(Integer groups_count) {
		this.groups_count = groups_count;
	}
}
