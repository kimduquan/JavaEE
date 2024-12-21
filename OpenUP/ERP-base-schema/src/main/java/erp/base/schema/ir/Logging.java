package erp.base.schema.ir;

import java.util.Date;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ir_logging")
@Description("Logging")
public class Logging {
	
	public enum Type {
		@Description("Client")
		client,
		@Description("Server")
		server
	}
	
	@Id
	private int id;

	@Column(updatable = false)
	@Description("Created by")
	private Integer create_uid;
	
	@Column(updatable = false)
	@Description("Created on")
	private Date create_date;
	
	@Column(updatable = false)
	@Description("Last Updated by")
	private Integer write_uid;
	
	@Column(updatable = false)
	@Description("Last Updated on")
	private Date write_date;
	
	@Column(nullable = false)
	@NotNull
	private String name;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	private Type type;
	
	@Column
	@Description("Database Name")
	private String dbname;
	
	@Column
	private String level;
	
	@Column(nullable = false)
	@NotNull
	private String message;
	
	@Column(nullable = false)
	@NotNull
	private String path;
	
	@Column(nullable = false)
	@NotNull
	@Description("Function")
	private String func;
	
	@Column(nullable = false)
	@NotNull
	private String line;
}
