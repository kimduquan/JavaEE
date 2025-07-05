package erp.base.schema.ir;

import java.util.Date;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "ir_profile")
@Description("Profiling results")
public class Profile {
	
	@Id
	private int id;

	@Column
	@Description("Creation Date")
	private Date create_date;
	
	@Column
	@Description("Session")
	private String session;
	
	@Column
	@Description("Description")
	private String name;
	
	@Column
	@Description("Duration")
	private Float duration;
	
	@Column
	@Description("Initial stack trace")
	private String init_stack_trace;
	
	@Column
	@Description("Sql")
	private String sql;
	
	@Column
	@Description("Queries Count")
	private Integer sql_count;
	
	@Column
	@Description("Traces Async")
	private String traces_async;
	
	@Column
	@Description("Traces Sync")
	private String traces_sync;
	
	@Column
	@Description("Qweb")
	private String qweb;
	
	@Column
	@Description("Entry count")
	private Integer entry_count;
	
	@Transient
	@Description("Speedscope")
	private byte[] speedscope;
	
	@Transient
	@Description("Open")
	private String speedscope_url;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
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
}
