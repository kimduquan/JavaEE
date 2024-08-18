package erp.base.schema.res.partner;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "res_partner_industry")
@Description("Industry")
public class Industry {

	/**
	 * 
	 */
	@Column
	@Description("Name")
	private String name;
	
	/**
	 * 
	 */
	@Column
	@Description("Full Name")
	private String full_name;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Description("Active")
	private Boolean active = true;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
