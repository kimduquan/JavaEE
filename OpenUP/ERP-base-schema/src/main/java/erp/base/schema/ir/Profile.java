package erp.base.schema.ir;

import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "ir_profile")
@Description("Profiling results")
@NodeEntity("Profiling results")
public class Profile {
	
	/**
	 * 
	 */
	@jakarta.persistence.Id
	@Id
	private int id;

	/**
	 * 
	 */
	@Column
	@Description("Creation Date")
	@Property
	private String create_date;
	
	/**
	 * 
	 */
	@Column
	@Description("Session")
	@Property
	private String session;
	
	/**
	 * 
	 */
	@Column
	@Description("Description")
	@Property
	private String name;
	
	/**
	 * 
	 */
	@Column
	@Description("Duration")
	@Property
	private Float duration;
	
	/**
	 * 
	 */
	@Column
	@Description("Initial stack trace")
	@Property
	private String init_stack_trace;
	
	/**
	 * 
	 */
	@Column
	@Description("Sql")
	@Property
	private String sql;
	
	/**
	 * 
	 */
	@Column
	@Description("Queries Count")
	@Property
	private Integer sql_count;
	
	/**
	 * 
	 */
	@Column
	@Description("Traces Async")
	@Property
	private String traces_async;
	
	/**
	 * 
	 */
	@Column
	@Description("Traces Sync")
	@Property
	private String traces_sync;
	
	/**
	 * 
	 */
	@Column
	@Description("Qweb")
	@Property
	private String qweb;
	
	/**
	 * 
	 */
	@Column
	@Description("Entry count")
	@Property
	private Integer entry_count;
	
	/**
	 * 
	 */
	@Column
	@Description("Speedscope")
	@Property
	private byte[] speedscope;
	
	/**
	 * 
	 */
	@Column
	@Description("Open")
	@Property
	private String speedscope_url;

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getDuration() {
		return duration;
	}

	public void setDuration(Float duration) {
		this.duration = duration;
	}

	public String getInit_stack_trace() {
		return init_stack_trace;
	}

	public void setInit_stack_trace(String init_stack_trace) {
		this.init_stack_trace = init_stack_trace;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Integer getSql_count() {
		return sql_count;
	}

	public void setSql_count(Integer sql_count) {
		this.sql_count = sql_count;
	}

	public String getTraces_async() {
		return traces_async;
	}

	public void setTraces_async(String traces_async) {
		this.traces_async = traces_async;
	}

	public String getTraces_sync() {
		return traces_sync;
	}

	public void setTraces_sync(String traces_sync) {
		this.traces_sync = traces_sync;
	}

	public String getQweb() {
		return qweb;
	}

	public void setQweb(String qweb) {
		this.qweb = qweb;
	}

	public Integer getEntry_count() {
		return entry_count;
	}

	public void setEntry_count(Integer entry_count) {
		this.entry_count = entry_count;
	}

	public byte[] getSpeedscope() {
		return speedscope;
	}

	public void setSpeedscope(byte[] speedscope) {
		this.speedscope = speedscope;
	}

	public String getSpeedscope_url() {
		return speedscope_url;
	}

	public void setSpeedscope_url(String speedscope_url) {
		this.speedscope_url = speedscope_url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
