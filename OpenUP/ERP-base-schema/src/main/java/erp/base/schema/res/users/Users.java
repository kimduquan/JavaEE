package erp.base.schema.res.users;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.actions.Actions;
import erp.base.schema.res.Company;
import erp.base.schema.res.groups.Groups;
import erp.base.schema.res.partner.Partner;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
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
public class Users {

	/**
	 * 
	 */
	@Column(nullable = false)
	@ManyToOne(targetEntity = Partner.class)
	@NotNull
	@Description("Related Partner")
	private String partner_id;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Used to log into the system")
	private String login;
	
	/**
	 * 
	 */
	@Column
	@Description("Keep empty if you don't want the user to be able to connect on the system.")
	private String password;
	
	/**
	 * 
	 */
	@Column
	@Description("Set Password")
	private String new_password;
	
	/**
	 * 
	 */
	@Column
	@Description("Email Signature")
	private String signature;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	private Boolean active = true;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Partner is Active")
	private Boolean active_partner;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Actions.class)
	@Description("Home Action")
	private String action_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = Groups.class)
	@Description("Groups")
	private List<String> groups_id;
	
	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity = Log.class)
	@ElementCollection(targetClass = Log.class)
	@CollectionTable(name = "res_users_log")
	@Description("User log entries")
	private List<String> log_ids;
	
	/**
	 * 
	 */
	@Column
	@Description("Latest authentication")
	private String login_date;
	
	/**
	 * 
	 */
	@Column
	@Description("Share User")
	private Boolean share;
	
	/**
	 * 
	 */
	@Column
	@Description("Number of Companies")
	private Integer companies_count;
	
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
	@OneToMany(targetEntity = Settings.class)
	@ElementCollection(targetClass = Settings.class)
	@CollectionTable(name = "res_users_settings")
	private List<String> res_users_settings_ids;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Settings.class)
	@Description("Settings")
	private String res_users_settings_id;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@ManyToOne(targetEntity = Company.class)
	@NotNull
	@Description("Company")
	private String company_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = Company.class)
	@ElementCollection(targetClass = Company.class)
	@CollectionTable(name = "res_company")
	@Description("Companies")
	private List<String> company_ids;
	
	/**
	 * 
	 */
	@Column
	private String name;
	
	/**
	 * 
	 */
	@Column
	private String email;
	
	/**
	 * 
	 */
	@Column
	@Description("# Access Rights")
	private Integer accesses_count;
	
	/**
	 * 
	 */
	@Column
	@Description("# Record Rules")
	private Integer rules_count;
	
	/**
	 * 
	 */
	@Column
	@Description("# Groups")
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
}
