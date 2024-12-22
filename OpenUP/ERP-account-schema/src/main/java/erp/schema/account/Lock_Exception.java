package erp.schema.account;

import java.util.Date;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.Company;
import erp.base.schema.res.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Description("Account Lock Exception")
public class Lock_Exception {
	
	public enum State {
		@Description("Active")
		active,
		@Description("Revoked")
		revoked,
		@Description("Expired")
		expired
	}
	
	public enum Lock {
		@Description("Global Lock Date")
		fiscalyear_lock_date,
		@Description("Tax Return Lock Date")
		tax_lock_date,
		@Description("Sales Lock Date")
		sale_lock_date,
		@Description("Purchase Lock Date")
		purchase_lock_date
	}

	@Column
	@DefaultValue("true")
	@Description("Active")
	private Boolean active = true;
	
	@Column
	@Enumerated(EnumType.STRING)
	@Description("State")
	private State state;
	
	@Transient
	private Integer company_id;
	
	@ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false, updatable = false)
	@NotNull
	@Description("Company")
	private Company company;
	
	@Transient
	private Integer user_id;
	
	@ManyToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@Description("User")
	private Users user;
	
	@Column
	@Description("Reason")
	private String reason;
	
	@Column
	@Description("End Date")
	private Date end_datetime;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@Description("Lock Date Field")
	private Lock lock_date_field;
	
	@Column
	@Description("Changed Lock Date")
	private Date lock_date;
	
	@Column
	@Description("Original Lock Date")
	private Date company_lock_date;
	
	@Column
	@Description("Global Lock Date")
	private Date fiscalyear_lock_date;
	
	@Column
	@Description("Tax Return Lock Date")
	private Date tax_lock_date;
	
	@Column
	@Description("Sales Lock Date")
	private Date sale_lock_date;
	
	@Column
	@Description("Purchase Lock Date")
	private Date purchase_lock_date;
}
