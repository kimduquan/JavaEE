package erp.base.schema.ir;

import java.util.Date;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
	
	@Column
	@Description("Speedscope")
	private byte[] speedscope;
	
	@Column
	@Description("Open")
	private String speedscope_url;
}
