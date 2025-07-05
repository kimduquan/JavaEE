package erp.base.schema.res.device;

import java.util.Date;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity(name = "DeviceLog")
@Table(name = "res_device_log")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Description("Device Log")
public class Log {
	
	public enum DeviceType {
		@Description("Computer")
		computer,
		@Description("Mobile")
		mobile
	}
	
	@Id
	private int id;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSession_identifier() {
		return session_identifier;
	}

	public void setSession_identifier(String session_identifier) {
		this.session_identifier = session_identifier;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public DeviceType getDevice_type() {
		return device_type;
	}

	public void setDevice_type(DeviceType device_type) {
		this.device_type = device_type;
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

	public Date getFirst_activity() {
		return first_activity;
	}

	public void setFirst_activity(Date first_activity) {
		this.first_activity = first_activity;
	}

	public Date getLast_activity() {
		return last_activity;
	}

	public void setLast_activity(Date last_activity) {
		this.last_activity = last_activity;
	}

	public Boolean getRevoked() {
		return revoked;
	}

	public void setRevoked(Boolean revoked) {
		this.revoked = revoked;
	}

	public Boolean getIs_current() {
		return is_current;
	}

	public void setIs_current(Boolean is_current) {
		this.is_current = is_current;
	}

	public Boolean getLinked_ip_addresses() {
		return linked_ip_addresses;
	}

	public void setLinked_ip_addresses(Boolean linked_ip_addresses) {
		this.linked_ip_addresses = linked_ip_addresses;
	}
}
