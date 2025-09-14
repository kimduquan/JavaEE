package epf.management.persistence.schema;

public class PersistenceUser {

	private String db_password;
	private String db_user;
	private String db_user_alias;
	private String id;
	private Boolean is_manager;
	private Integer max_clients;
	private String mode_type;
	private Integer pool_checkout_timeout;
	private int pool_size;
	private String tenant_external_id;
	
	public String getDb_password() {
		return db_password;
	}
	public void setDb_password(String db_password) {
		this.db_password = db_password;
	}
	public String getDb_user() {
		return db_user;
	}
	public void setDb_user(String db_user) {
		this.db_user = db_user;
	}
	public String getDb_user_alias() {
		return db_user_alias;
	}
	public void setDb_user_alias(String db_user_alias) {
		this.db_user_alias = db_user_alias;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Boolean getIs_manager() {
		return is_manager;
	}
	public void setIs_manager(Boolean is_manager) {
		this.is_manager = is_manager;
	}
	public Integer getMax_clients() {
		return max_clients;
	}
	public void setMax_clients(Integer max_clients) {
		this.max_clients = max_clients;
	}
	public String getMode_type() {
		return mode_type;
	}
	public void setMode_type(String mode_type) {
		this.mode_type = mode_type;
	}
	public Integer getPool_checkout_timeout() {
		return pool_checkout_timeout;
	}
	public void setPool_checkout_timeout(Integer pool_checkout_timeout) {
		this.pool_checkout_timeout = pool_checkout_timeout;
	}
	public int getPool_size() {
		return pool_size;
	}
	public void setPool_size(int pool_size) {
		this.pool_size = pool_size;
	}
	public String getTenant_external_id() {
		return tenant_external_id;
	}
	public void setTenant_external_id(String tenant_external_id) {
		this.tenant_external_id = tenant_external_id;
	}
}
