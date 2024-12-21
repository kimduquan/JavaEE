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
}
