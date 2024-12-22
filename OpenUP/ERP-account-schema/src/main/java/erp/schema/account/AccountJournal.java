package erp.schema.account;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.schema.account.bank.Statement;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class AccountJournal extends Journal {

	@Column
	private String kanban_dashboard;
	
	@Column
	private String kanban_dashboard_graph;
	
	@Column
	private Object json_activity_data;
	
	@Column
	@DefaultValue("true")
	@Description("Show journal on dashboard")
	private Boolean show_on_dashboard = true;
	
	@Column
	@DefaultValue("0")
	@Description("Color Index")
	private Integer color = 0;
	
	@Column
	private String current_statement_balance;
	
	@Column
	private Boolean has_statement_lines;
	
	@Column
	private Integer entries_count;
	
	@Column
	private Boolean has_posted_entries;
	
	@Column
	private Boolean has_entries;
	
	@Column
	private Boolean has_sequence_holes;
	
	@Column
	@Description("Unhashed Entries")
	private Boolean has_unhashed_entries;
	
	@Transient
	private Integer last_statement_id;
	
	@ManyToOne(targetEntity = Statement.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "last_statement_id")
	private Statement last_statement;
}
