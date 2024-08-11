package erp.schema.ir;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.schema.ir.actions.Server;
import erp.schema.res.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_cron")
@Description("Scheduled Actions")
public class Cron {

	/**
	 * 
	 */
	@Column(nullable = false)
	@ManyToOne(targetEntity = Server.class)
	@NotNull
	@Description("Server action")
	private String ir_actions_server_id;
	
	/**
	 * 
	 */
	@Column
	@Description("Name")
	private String cron_name;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@ManyToOne(targetEntity = Users.class)
	@NotNull
	@Description("Scheduler User")
	private String user_id;
	
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
	@DefaultValue("1")
	@Description("Repeat every x.")
	private Integer interval_number = 1;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@Description("Interval Unit")
	@DefaultValue("months")
	private String interval_type = "months";
	
	/**
	 * 
	 */
	@Column
	@Description("Number of Calls")
	@DefaultValue("1")
	private Integer numbercall = 1;
	
	/**
	 * 
	 */
	@Column
	@Description("Repeat Missed")
	private Boolean doall;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Description("Next Execution Date")
	private String nextcall;
	
	/**
	 * 
	 */
	@Column
	@Description("Last Execution Date")
	private String lastcall;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("5")
	private Integer priority = 5;
}
