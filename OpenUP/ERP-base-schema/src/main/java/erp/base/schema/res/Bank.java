package erp.base.schema.res;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

import erp.base.schema.res.country.State;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "res_bank")
@Description("Bank")
public class Bank {
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
	private String name;
	
	@Column
	private String street;
	
	@Column
	private String street2;
	
	@Column
	private String zip;
	
	@Column
	private String city;
	
	@ManyToOne(targetEntity = State.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "state")
	@Description("Fed. State")
	private State state;
	
	@ManyToOne(targetEntity = Country.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "country")
	private Country country;
	
	@Column
	@Description("Country Code")
	private String country_code;
	
	@Column
	private String email;
	
	@Column
	private String phone;
	
	@Column
	@DefaultValue("true")
	private Boolean active = true;
	
	@Column
	@Description("Bank Identifier Code")
	private String bic;
}
