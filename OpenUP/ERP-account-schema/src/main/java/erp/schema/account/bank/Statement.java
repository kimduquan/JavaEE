package erp.schema.account.bank;

import java.util.Date;
import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.Attachment;
import erp.base.schema.res.Company;
import erp.base.schema.res.Currency;
import erp.schema.account.Journal;
import erp.schema.account.bank.statement.Line;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Description("Bank Statement")
public class Statement {

	@Column
	@Description("Reference")
	private String name;
	
	@Column
	@Description("External Reference")
	private String reference;
	
	@Column
	private Date date;
	
	@Column
	private String first_line_index;
	
	@Column
	@Description("Starting Balance")
	private String balance_start;
	
	@Column
	@Description("Computed Balance")
	private String balance_end;
	
	@Column
	@Description("Ending Balance")
	private String balance_end_real;
	
	@Transient
	private Integer company_id;
	
	@ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;
	
	@Transient
	private Integer currency_id;
	
	@ManyToOne(targetEntity = Currency.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "currency_id")
	private Currency currency;
	
	@Transient
	private Integer journal_id;
	
	@ManyToOne(targetEntity = Journal.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "journal_id")
	private Journal journal;
	
	@Transient
	private List<Integer> line_ids;
	
	@OneToMany(targetEntity = Line.class, fetch = FetchType.LAZY)
	@NotNull
	@Description("Statement lines")
	private List<Line> lines;
	
	@Column
	private Boolean is_complete;
	
	@Column
	private Boolean is_valid;
	
	@Column
	private String problem_description;
	
	@Transient
	private List<Integer> attachment_ids;
	
	@ManyToMany(targetEntity = Attachment.class, fetch = FetchType.LAZY)
	@Description("Attachments")
	private List<Attachment> attachments;
}
