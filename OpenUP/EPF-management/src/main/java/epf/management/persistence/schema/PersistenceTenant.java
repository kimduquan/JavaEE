package epf.management.persistence.schema;

import java.util.List;

public class PersistenceTenant {

	private String db_database;
	private String db_host;
	private int db_port;
	private Boolean enforce_ssl;
	private String external_id;
	private String id;
	private String ip_version;
	private Boolean require_user;
	private String sni_hostname;
	private Boolean upstream_ssl;
	private Boolean upstream_verify;
	private List<PersistenceUser> users;
	
	public String getDb_database() {
		return db_database;
	}
	public void setDb_database(String db_database) {
		this.db_database = db_database;
	}
	public String getDb_host() {
		return db_host;
	}
	public void setDb_host(String db_host) {
		this.db_host = db_host;
	}
	public int getDb_port() {
		return db_port;
	}
	public void setDb_port(int db_port) {
		this.db_port = db_port;
	}
	public Boolean getEnforce_ssl() {
		return enforce_ssl;
	}
	public void setEnforce_ssl(Boolean enforce_ssl) {
		this.enforce_ssl = enforce_ssl;
	}
	public String getExternal_id() {
		return external_id;
	}
	public void setExternal_id(String external_id) {
		this.external_id = external_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIp_version() {
		return ip_version;
	}
	public void setIp_version(String ip_version) {
		this.ip_version = ip_version;
	}
	public Boolean getRequire_user() {
		return require_user;
	}
	public void setRequire_user(Boolean require_user) {
		this.require_user = require_user;
	}
	public String getSni_hostname() {
		return sni_hostname;
	}
	public void setSni_hostname(String sni_hostname) {
		this.sni_hostname = sni_hostname;
	}
	public Boolean getUpstream_ssl() {
		return upstream_ssl;
	}
	public void setUpstream_ssl(Boolean upstream_ssl) {
		this.upstream_ssl = upstream_ssl;
	}
	public Boolean getUpstream_verify() {
		return upstream_verify;
	}
	public void setUpstream_verify(Boolean upstream_verify) {
		this.upstream_verify = upstream_verify;
	}
	public List<PersistenceUser> getUsers() {
		return users;
	}
	public void setUsers(List<PersistenceUser> users) {
		this.users = users;
	}
}
