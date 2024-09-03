package erp.base.schema.ir.cron;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;

import erp.base.schema.ir.actions.Server;
import erp.base.schema.res.users.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_cron")
@Description("Scheduled Actions")
@NodeEntity("Scheduled Actions")
public class Cron {
	
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
	@Description("Server action")
	@Transient
	private String ir_actions_server_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = Server.class)
	@JoinColumn(name = "ir_actions_server_id", nullable = false)
	@NotNull
	@Relationship(type = "ACTIONS_SERVER")
	private Server ir_actions_server;
	
	/**
	 * 
	 */
	@Column
	@Description("Name")
	@Property
	private String cron_name;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Scheduler User")
	@Transient
	private String user_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = Users.class)
	@JoinColumn(name = "user_id", nullable = false)
	@NotNull
	@Relationship(type = "SCHEDULER_USER")
	private Users user;
	
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
	@Column
	@DefaultValue("1")
	@Description("Repeat every x.")
	@Property
	private Integer interval_number = 1;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@Description("Interval Unit")
	@DefaultValue("months")
	@Property
	private String interval_type = "months";
	
	/**
	 * 
	 */
	@Column
	@Description("Number of Calls")
	@DefaultValue("1")
	@Property
	private Integer numbercall = 1;
	
	/**
	 * 
	 */
	@Column
	@Description("Repeat Missed")
	@Property
	private Boolean doall;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Description("Next Execution Date")
	@Property
	private String nextcall;
	
	/**
	 * 
	 */
	@Column
	@Description("Last Execution Date")
	@Property
	private String lastcall;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("5")
	@Property
	private Integer priority = 5;

	public String getIr_actions_server_id() {
		return ir_actions_server_id;
	}

	public void setIr_actions_server_id(String ir_actions_server_id) {
		this.ir_actions_server_id = ir_actions_server_id;
	}

	public String getCron_name() {
		return cron_name;
	}

	public void setCron_name(String cron_name) {
		this.cron_name = cron_name;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getInterval_number() {
		return interval_number;
	}

	public void setInterval_number(Integer interval_number) {
		this.interval_number = interval_number;
	}

	public String getInterval_type() {
		return interval_type;
	}

	public void setInterval_type(String interval_type) {
		this.interval_type = interval_type;
	}

	public Integer getNumbercall() {
		return numbercall;
	}

	public void setNumbercall(Integer numbercall) {
		this.numbercall = numbercall;
	}

	public Boolean getDoall() {
		return doall;
	}

	public void setDoall(Boolean doall) {
		this.doall = doall;
	}

	public String getNextcall() {
		return nextcall;
	}

	public void setNextcall(String nextcall) {
		this.nextcall = nextcall;
	}

	public String getLastcall() {
		return lastcall;
	}

	public void setLastcall(String lastcall) {
		this.lastcall = lastcall;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Server getIr_actions_server() {
		return ir_actions_server;
	}

	public void setIr_actions_server(Server ir_actions_server) {
		this.ir_actions_server = ir_actions_server;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
}
