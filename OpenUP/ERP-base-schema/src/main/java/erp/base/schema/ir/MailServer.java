package erp.base.schema.ir;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Transient;
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
@NodeEntity("Mail Server")
public class MailServer {
	
	/**
	 * 
	 */
	public enum AuthenticateWith {
		/**
		 * 
		 */
		login,
        /**
         * 
         */
        certificate,
        /**
         * 
         */
        cli
	}
	
	/**
	 * 
	 */
	public enum ConnectionEncryption {
		/**
		 * 
		 */
		none,
        /**
         * 
         */
        starttls,
        /**
         * 
         */
        ssl
	}
	
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
	@Description("Name")
	@Property
	private String name;
	
	/**
	 * 
	 */
	@Column
	@Description("FROM Filtering")
	@Property
	private String from_filter;
	
	/**
	 * 
	 */
	@Column
	@Description("SMTP Server")
	@Property
	private String smtp_host;
	
	/**
	 * 
	 */
	@Column
	@Description("SMTP Port")
	@DefaultValue("25")
	@Property
	private Integer smtp_port = 25;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("login")
	@Description("Authenticate with")
	@Property
	private AuthenticateWith smtp_authentication = AuthenticateWith.login;
	
	/**
	 * 
	 */
	@jakarta.persistence.Transient
	@Description("Authentication Info")
	@Transient
	private String smtp_authentication_info;
	
	/**
	 * 
	 */
	@Column
	@Description("Username")
	@Property
	private String smtp_user;
	
	/**
	 * 
	 */
	@Column
	@Description("Password")
	@Property
	private String smtp_pass;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("none")
	@Description("Connection Encryption")
	@Property
	private ConnectionEncryption smtp_encryption = ConnectionEncryption.none;
	
	/**
	 * 
	 */
	@Column
	@Description("SSL Certificate")
	@Property
	private byte[] smtp_ssl_certificate;
	
	/**
	 * 
	 */
	@Column
	@Description("SSL Private Key")
	@Property
	private byte[] smtp_ssl_private_key;
	
	/**
	 * 
	 */
	@Column
	@Description("Debugging")
	@Property
	private Boolean smtp_debug;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("10")
	@Description("Priority")
	@Property
	private Integer sequence = 10;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Property
	private Boolean active = true;

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

	public ConnectionEncryption getSmtp_encryption() {
		return smtp_encryption;
	}

	public void setSmtp_encryption(ConnectionEncryption smtp_encryption) {
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
