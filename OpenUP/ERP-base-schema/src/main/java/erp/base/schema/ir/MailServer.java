package erp.base.schema.ir;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_mail_server")
@Description("Mail Server")
public class MailServer {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Name")
	private String name;
	
	/**
	 * 
	 */
	@Column
	@Description("FROM Filtering")
	private String from_filter;
	
	/**
	 * 
	 */
	@Column
	@Description("SMTP Server")
	private String smtp_host;
	
	/**
	 * 
	 */
	@Column
	@Description("SMTP Port")
	@DefaultValue("25")
	private Integer smtp_port = 25;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("login")
	@Description("Authenticate with")
	private String smtp_authentication = "login";
	
	/**
	 * 
	 */
	@Column
	@Description("Authentication Info")
	private String smtp_authentication_info;
	
	/**
	 * 
	 */
	@Column
	@Description("Username")
	private String smtp_user;
	
	/**
	 * 
	 */
	@Column
	@Description("Password")
	private String smtp_pass;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("none")
	@Description("Connection Encryption")
	private String smtp_encryption = "none";
	
	/**
	 * 
	 */
	@Column
	@Description("SSL Certificate")
	private byte[] smtp_ssl_certificate;
	
	/**
	 * 
	 */
	@Column
	@Description("SSL Private Key")
	private byte[] smtp_ssl_private_key;
	
	/**
	 * 
	 */
	@Column
	@Description("Debugging")
	private Boolean smtp_debug;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("10")
	@Description("Priority")
	private Integer sequence = 10;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	private Boolean active = true;
}
