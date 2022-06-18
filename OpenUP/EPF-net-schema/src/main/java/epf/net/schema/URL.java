package epf.net.schema;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.schema.utility.EPFEntity;

/**
 * @author PC
 *
 */
@Type(Net.URL)
@Schema(name = Net.URL, title = "URL")
@Entity(name = Net.URL)
@Table(schema = Net.SCHEMA, name = "URL")
public class URL extends EPFEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "EPF_Net_URL", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EPF_Net_URL")
	private Integer id;
	
	/**
	 * 
	 */
	@Column(name = "AUTHORITY")
	private String authority;
	
	/**
	 * 
	 */
	@Column(name = "DEFAULT_PORT")
	private Integer defaultPort;
	
	/**
	 * 
	 */
	@Column(name = "FILE")
	private String file;
	
	/**
	 * 
	 */
	@Column(name = "HOST")
	private String host;

	/**
	 * 
	 */
	@Column(name = "PATH")
	private String path;

	/**
	 * 
	 */
	@Column(name = "PORT")
	private Integer port;

	/**
	 * 
	 */
	@Column(name = "PROTOCOL")
	private String protocol;

	/**
	 * 
	 */
	@Column(name = "QUERY")
	private String query;

	/**
	 * 
	 */
	@Column(name = "REF")
	private String ref;

	/**
	 * 
	 */
	@Column(name = "USER_INFO")
	private String userInfo;

	/**
	 * 
	 */
	@Column(name = "STRING", nullable = false)
	private String string;

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(final String authority) {
		this.authority = authority;
	}

	public Integer getDefaultPort() {
		return defaultPort;
	}

	public void setDefaultPort(final Integer defaultPort) {
		this.defaultPort = defaultPort;
	}

	public String getFile() {
		return file;
	}

	public void setFile(final String file) {
		this.file = file;
	}

	public String getHost() {
		return host;
	}

	public void setHost(final String host) {
		this.host = host;
	}

	public String getPath() {
		return path;
	}

	public void setPath(final String path) {
		this.path = path;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(final Integer port) {
		this.port = port;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(final String protocol) {
		this.protocol = protocol;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(final String query) {
		this.query = query;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(final String ref) {
		this.ref = ref;
	}

	public String getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(final String userInfo) {
		this.userInfo = userInfo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getString() {
		return string;
	}

	public void setString(final String string) {
		this.string = string;
	}
}
