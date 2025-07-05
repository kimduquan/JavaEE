package erp.base.schema.ir;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ir_mail_server")
@Description("Mail Server")
public class Mail_Server {
	
	public enum AuthenticateWith {
		@Description("Username")
		login,
		@Description("SSL Certificate")
        certificate,
        @Description("Command Line Interface")
        cli
	}
	
	public enum Encryption {
		@Description("None")
		none,
		@Description("TLS (STARTTLS), encryption and validation")
		starttls_strict,
		@Description("TLS (STARTTLS), encryption only")
		starttls,
		@Description("SSL/TLS, encryption and validation")
		ssl_strict,
		@Description("SSL/TLS, encryption only")
        ssl
	}
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
	@Description("Name")
	private String name;
	
	@Column
	@Description("FROM Filtering")
	private String from_filter;
	
	@Column
	@Description("SMTP Server")
	private String smtp_host;
	
	@Column
	@Description("SMTP Port")
	@DefaultValue("25")
	private Integer smtp_port = 25;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("login")
	@Description("Authenticate with")
	private AuthenticateWith smtp_authentication = AuthenticateWith.login;
	
	@Transient
	@Description("Authentication Info")
	private String smtp_authentication_info;
	
	@Column
	@Description("Username")
	private String smtp_user;
	
	@Column
	@Description("Password")
	private String smtp_pass;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("none")
	@Description("Connection Encryption")
	private Encryption smtp_encryption = Encryption.none;
	
	@Column
	@Description("SSL Certificate")
	private byte[] smtp_ssl_certificate;
	
	@Column
	@Description("SSL Private Key")
	private byte[] smtp_ssl_private_key;
	
	@Column
	@Description("Debugging")
	private Boolean smtp_debug;
	
	@Column
	@Description("Max Email Size")
	private Float max_email_size;
	
	@Column
	@DefaultValue("10")
	@Description("Priority")
	private Integer sequence = 10;
	
	@Column
	@DefaultValue("true")
	private Boolean active = true;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFrom_filter() {
		return from_filter;
	}

	public void setFrom_filter(String from_filter) {
		this.from_filter = from_filter;
	}

	public String getSmtp_host() {
		return smtp_host;
	}

	public void setSmtp_host(String smtp_host) {
		this.smtp_host = smtp_host;
	}

	public Integer getSmtp_port() {
		return smtp_port;
	}

	public void setSmtp_port(Integer smtp_port) {
		this.smtp_port = smtp_port;
	}

	public AuthenticateWith getSmtp_authentication() {
		return smtp_authentication;
	}

	public void setSmtp_authentication(AuthenticateWith smtp_authentication) {
		this.smtp_authentication = smtp_authentication;
	}

	public String getSmtp_authentication_info() {
		return smtp_authentication_info;
	}

	public void setSmtp_authentication_info(String smtp_authentication_info) {
		this.smtp_authentication_info = smtp_authentication_info;
	}

	public String getSmtp_user() {
		return smtp_user;
	}

	public void setSmtp_user(String smtp_user) {
		this.smtp_user = smtp_user;
	}

	public String getSmtp_pass() {
		return smtp_pass;
	}

	public void setSmtp_pass(String smtp_pass) {
		this.smtp_pass = smtp_pass;
	}

	public Encryption getSmtp_encryption() {
		return smtp_encryption;
	}

	public void setSmtp_encryption(Encryption smtp_encryption) {
		this.smtp_encryption = smtp_encryption;
	}

	public byte[] getSmtp_ssl_certificate() {
		return smtp_ssl_certificate;
	}

	public void setSmtp_ssl_certificate(byte[] smtp_ssl_certificate) {
		this.smtp_ssl_certificate = smtp_ssl_certificate;
	}

	public byte[] getSmtp_ssl_private_key() {
		return smtp_ssl_private_key;
	}

	public void setSmtp_ssl_private_key(byte[] smtp_ssl_private_key) {
		this.smtp_ssl_private_key = smtp_ssl_private_key;
	}

	public Boolean getSmtp_debug() {
		return smtp_debug;
	}

	public void setSmtp_debug(Boolean smtp_debug) {
		this.smtp_debug = smtp_debug;
	}

	public Float getMax_email_size() {
		return max_email_size;
	}

	public void setMax_email_size(Float max_email_size) {
		this.max_email_size = max_email_size;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
