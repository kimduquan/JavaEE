package erp.base.schema.res.device;

import java.util.Date;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "res_device_log")
@Description("Device Log")
public class Log {
	
	public enum DeviceType {
		@Description("Computer")
		computer,
		@Description("Mobile")
		mobile
	}

	@Column(nullable = false)
	@NotNull
	@Description("Session Identifier")
	private String session_identifier;
	
	@Column
	@Description("Platform")
	private String platform;
	
	@Column
	@Description("Browser")
	private String browser;
	
	@Column
	@Description("IP Address")
	private String ip_address;
	
	@Column
	@Description("Country")
	private String country;
	
	@Column
	@Description("City")
	private String city;
	
	@Column
	@Enumerated(EnumType.STRING)
	@Description("Device Type")
	private DeviceType device_type;
	
	@Transient
	private Integer user_id;
	
	@ManyToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Users user;
	
	@Column
	@Description("First Activity")
	private Date first_activity;
	
	@Column
	@Description("Last Activity")
	private Date last_activity;
	
	@Column
	@Description("Revoked")
	private Boolean revoked;
	
	@Transient
	@Description("Current Device")
	private Boolean is_current;
	
	@Transient
	@Description("Linked IP address")
	private Boolean linked_ip_addresses;
}
