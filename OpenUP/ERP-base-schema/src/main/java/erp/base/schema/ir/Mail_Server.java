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
}
