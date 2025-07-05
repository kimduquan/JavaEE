package erp.base.schema.ir;

import java.time.Instant;
import java.util.Date;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.actions.Server;
import erp.base.schema.res.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ir_cron")
@Description("Scheduled Actions")
public class Cron {
	
	public enum IntervalUnit {
		@Description("Minutes")
		minutes,
		@Description("Hours")
        hours,
        @Description("Days")
        days,
        @Description("Weeks")
        weeks,
        @Description("Months")
        months
	}
	
	@Id
	private int id;

	@Transient
	private Integer ir_actions_server_id;

	@ManyToOne(targetEntity = Server.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "ir_actions_server_id", nullable = false)
	@NotNull
	@Description("Server action")
	private Server ir_actions_server;
	
	@Column
	@Description("Name")
	private String cron_name;
	
	@Transient
	private Integer user_id;

	@ManyToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@NotNull
	@Description("Scheduler User")
	private Users user;
	
	@Column
	@DefaultValue("true")
	private Boolean active = true;
	
	@Column(nullable = false)
	@NotNull
	@DefaultValue("1")
	private Integer interval_number = 1;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@Description("Interval Unit")
	@DefaultValue("months")
	private IntervalUnit interval_type = IntervalUnit.months;
	
	@Column(nullable = false)
	@NotNull
	@Description("Next Execution Date")
	private Date nextcall = Date.from(Instant.now());
	
	@Column
	@Description("Last Execution Date")
	private Date lastcall;
	
	@Column
	@DefaultValue("5")
	private Integer priority = 5;
	
	@Column
	@DefaultValue("0")
	private Integer failure_count = 0;
	
	@Column
	@Description("First Failure Date")
	private Date first_failure_date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getIr_actions_server_id() {
		return ir_actions_server_id;
	}

	public void setIr_actions_server_id(Integer ir_actions_server_id) {
		this.ir_actions_server_id = ir_actions_server_id;
	}

	public Server getIr_actions_server() {
		return ir_actions_server;
	}

	public void setIr_actions_server(Server ir_actions_server) {
		this.ir_actions_server = ir_actions_server;
	}

	public String getCron_name() {
		return cron_name;
	}

	public void setCron_name(String cron_name) {
		this.cron_name = cron_name;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
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

	public IntervalUnit getInterval_type() {
		return interval_type;
	}

	public void setInterval_type(IntervalUnit interval_type) {
		this.interval_type = interval_type;
	}

	public Date getNextcall() {
		return nextcall;
	}

	public void setNextcall(Date nextcall) {
		this.nextcall = nextcall;
	}

	public Date getLastcall() {
		return lastcall;
	}

	public void setLastcall(Date lastcall) {
		this.lastcall = lastcall;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getFailure_count() {
		return failure_count;
	}

	public void setFailure_count(Integer failure_count) {
		this.failure_count = failure_count;
	}

	public Date getFirst_failure_date() {
		return first_failure_date;
	}

	public void setFirst_failure_date(Date first_failure_date) {
		this.first_failure_date = first_failure_date;
	}
}
