package erp.base.schema.res.partner;

import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "res_partner_title")
@Description("Partner Title")
public class Title {
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
	@Description("Title")
	private String name;
	
	@Column
	@Description("Abbreviation")
	private String shortcut;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortcut() {
		return shortcut;
	}

	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}
}
