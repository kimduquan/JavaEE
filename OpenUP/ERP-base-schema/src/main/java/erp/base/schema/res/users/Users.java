package erp.base.schema.res.users;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import erp.base.schema.ir.actions.Actions;
import erp.base.schema.res.Company;
import erp.base.schema.res.groups.Groups;
import erp.base.schema.res.partner.Partner;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
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
@Table(name = "res_users")
@Description("User")
@NodeEntity("User")
public class Users {
	
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
	@Description("Related Partner")
	@Transient
	private String partner_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = Partner.class)
	@JoinColumn(name = "partner_id", nullable = false)
	@NotNull
	@Relationship(type = "RELATED_PARTNER")
	private Partner partner;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Used to log into the system")
	@Property
	private String login;
	
	/**
	 * 
	 */
	@Column
	@Description("Keep empty if you don't want the user to be able to connect on the system.")
	@Property
	private String password;
	
	/**
	 * 
	 */
	@Column
	@Description("Set Password")
	@Property
	private String new_password;
	
	/**
	 * 
	 */
	@Column
	@Description("Email Signature")
	@Property
	private String signature;
	
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
	@Column(updatable = false)
	@Description("Partner is Active")
	@Property
	private Boolean active_partner;
	
	/**
	 * 
	 */
	@Column
	@Description("Home Action")
	@Transient
	private String action_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = Actions.class)
	@JoinColumn(name = "action_id")
	@Relationship(type = "HOME_ACTION")
	private Actions action;
	
	/**
	 * 
	 */
	@ElementCollection(targetClass = Groups.class)
	@CollectionTable(name = "res_groups_users_rel", joinColumns = {
			@JoinColumn(name = "uid", referencedColumnName = "gid")
	})
	@Description("Groups")
	@Transient
	private List<String> groups_id;
	
	/**
	 * 
	 */
	@ManyToMany(targetEntity = Groups.class)
	@JoinTable(name = "res_groups_users_rel", joinColumns = {
			@JoinColumn(name = "uid", referencedColumnName = "gid")
	})
	@Relationship(type = "GROUPS")
	private List<Groups> groups;
	
	/**
	 * 
	 */
	@ElementCollection(targetClass = Log.class)
	@CollectionTable(name = "res_users_log", joinColumns = {
			@JoinColumn(name = "create_uid")
	})
	@Description("User log entries")
	@Transient
	private List<String> log_ids;
	
	/**
	 * 
	 */
	@OneToMany(targetEntity = Log.class, mappedBy = "create_uid")
	@Relationship(type = "USER_LOG_ENTRIES")
	private List<Log> logs;
	
	/**
	 * 
	 */
	@Column
	@Description("Latest authentication")
	@Property
	private String login_date;
	
	/**
	 * 
	 */
	@Column
	@Description("Share User")
	@Property
	private Boolean share;
	
	/**
	 * 
	 */
	@Column
	@Description("Number of Companies")
	@Property
	private Integer companies_count;
	
	/**
	 * 
	 */
	@Column
	@Description("Timezone offset")
	@Property
	private String tz_offset;
	
	/**
	 * 
	 */
	@ElementCollection(targetClass = Settings.class)
	@CollectionTable(name = "res_users_settings", joinColumns = {
			@JoinColumn(name = "user_id")
	})
	@Transient
	private List<String> res_users_settings_ids;
	
	/**
	 * 
	 */
	@OneToMany(targetEntity = Settings.class, mappedBy = "user_id")
	@Relationship(type = "USERS_SETTINGS")
	private List<Settings> res_users_settings;
	
	/**
	 * 
	 */
	@Column
	@Description("Settings")
	@Transient
	private String res_users_settings_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Settings.class)
	@JoinColumn(name = "res_users_settings_id")
	@Relationship(type = "SETTINGS")
	private Settings res_users_setting;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Company")
	@Transient
	private String company_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Company.class)
	@JoinColumn(name = "company_id", nullable = false)
	@NotNull
	@Relationship(type = "COMPANY")
	private Company company;
	
	/**
	 * 
	 */
	@ElementCollection(targetClass = Company.class)
	@CollectionTable(name = "res_company_users_rel", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "cid")
	})
	@Description("Companies")
	@Transient
	private List<String> company_ids;
	
	/**
	 * 
	 */
	@ManyToMany(targetEntity = Company.class)
	@JoinTable(name = "res_company_users_rel", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "cid")
	})
	@Relationship(type = "COMPANIES")
	private List<Company> companies;
	
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
	private String email;
	
	/**
	 * 
	 */
	@Column
	@Description("# Access Rights")
	@Property
	private Integer accesses_count;
	
	/**
	 * 
	 */
	@Column
	@Description("# Record Rules")
	@Property
	private Integer rules_count;
	
	/**
	 * 
	 */
	@Column
	@Description("# Groups")
	@Property
	private Integer groups_count;

	public String getPartner_id() {
		return partner_id;
	}

	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
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

	public String getAction_id() {
		return action_id;
	}

	public void setAction_id(String action_id) {
		this.action_id = action_id;
	}

	public List<String> getGroups_id() {
		return groups_id;
	}

	public void setGroups_id(List<String> groups_id) {
		this.groups_id = groups_id;
	}

	public List<String> getLog_ids() {
		return log_ids;
	}

	public void setLog_ids(List<String> log_ids) {
		this.log_ids = log_ids;
	}

	public String getLogin_date() {
		return login_date;
	}

	public void setLogin_date(String login_date) {
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

	public List<String> getRes_users_settings_ids() {
		return res_users_settings_ids;
	}

	public void setRes_users_settings_ids(List<String> res_users_settings_ids) {
		this.res_users_settings_ids = res_users_settings_ids;
	}

	public String getRes_users_settings_id() {
		return res_users_settings_id;
	}

	public void setRes_users_settings_id(String res_users_settings_id) {
		this.res_users_settings_id = res_users_settings_id;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public List<String> getCompany_ids() {
		return company_ids;
	}

	public void setCompany_ids(List<String> company_ids) {
		this.company_ids = company_ids;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public Actions getAction() {
		return action;
	}

	public void setAction(Actions action) {
		this.action = action;
	}

	public List<Groups> getGroups() {
		return groups;
	}

	public void setGroups(List<Groups> groups) {
		this.groups = groups;
	}

	public List<Log> getLogs() {
		return logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}

	public List<Settings> getRes_users_settings() {
		return res_users_settings;
	}

	public void setRes_users_settings(List<Settings> res_users_settings) {
		this.res_users_settings = res_users_settings;
	}

	public Settings getRes_users_setting() {
		return res_users_setting;
	}

	public void setRes_users_setting(Settings res_users_setting) {
		this.res_users_setting = res_users_setting;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}
}
