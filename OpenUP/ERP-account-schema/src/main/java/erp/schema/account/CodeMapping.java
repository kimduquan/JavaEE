package erp.schema.account;

import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.Company;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
@Description("Mapping of account codes per company")
public class CodeMapping {

	@Transient
	private Integer account_id;
	
	@ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	@Description("Account")
	private Account account;
	
	@Transient
	private Integer company_id;
	
	@ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	@Description("Company")
	private Company company;
	
	@Column
	@Description("code")
	private String code;
}
