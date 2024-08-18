package erp.schema.res;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.schema.Partner;
import erp.schema.ir.actions.Actions;
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
	@OneToMany(targetEntity = UsersLog.class)
	@ElementCollection(targetClass = UsersLog.class)
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
	@OneToMany(targetEntity = UsersSettings.class)
	@ElementCollection(targetClass = UsersSettings.class)
	@CollectionTable(name = "res_users_settings")
	private List<String> res_users_settings_ids;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = UsersSettings.class)
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
}
