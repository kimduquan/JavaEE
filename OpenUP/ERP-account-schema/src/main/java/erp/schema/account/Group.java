package erp.schema.account;

import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.Company;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "account_group")
@Description("Account Group")
public class Group {
	
	@Transient
	private Integer parent_id;
	
	@ManyToOne(targetEntity = Group.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", updatable = false)
	private Group parent;
	
	@Column(nullable = false)
	@NotNull
	private String name;
	
	@Column
	private String code_prefix_start;
	
	@Column
	private String code_prefix_end;
	
	@Transient
	private Integer company_id;
	
	@ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	@NotNull
	private Company company;
}
