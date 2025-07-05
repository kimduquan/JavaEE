package erp.schema.account.journal;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.Company;
import erp.schema.account.Journal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Description("Account Journal Group")
public class Group {

	@Column(nullable = false)
	@NotNull
	@Description("Ledger group")
	private String name;
	
	@Transient
	private Integer company_id;
	
	@ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;
	
	@Transient
	private List<Integer> excluded_journal_ids;
	
	@ManyToMany(targetEntity = Journal.class, fetch = FetchType.LAZY)
	@Description("Excluded Journals")
	private List<Journal> excluded_journals;
	
	@Column
	@DefaultValue("10")
	private Integer sequence = 10;
}
