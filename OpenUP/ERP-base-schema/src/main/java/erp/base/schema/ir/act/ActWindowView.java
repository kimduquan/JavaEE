package erp.base.schema.ir.act;

import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import erp.base.schema.ir.ui.View;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_act_window_view")
@Description("Action Window View")
@NodeEntity("Action Window View")
public class ActWindowView {

	/**
	 * 
	 */
	@Column
	@Property
	private Integer sequence;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = View.class)
	@Description("View")
	@Property
	@Relationship(type = "VIEW")
	private String view_id;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@Description("View Type")
	@Property
	private String view_mode;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = ActWindow.class)
	@Description("Action")
	@Property
	@Relationship(type = "ACTION")
	private String act_window_id;
	
	/**
	 * 
	 */
	@Column
	@Description("On Multiple Doc.")
	@Property
	private Boolean multi;

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getView_id() {
		return view_id;
	}

	public void setView_id(String view_id) {
		this.view_id = view_id;
	}

	public String getView_mode() {
		return view_mode;
	}

	public void setView_mode(String view_mode) {
		this.view_mode = view_mode;
	}

	public String getAct_window_id() {
		return act_window_id;
	}

	public void setAct_window_id(String act_window_id) {
		this.act_window_id = act_window_id;
	}

	public Boolean getMulti() {
		return multi;
	}

	public void setMulti(Boolean multi) {
		this.multi = multi;
	}
}
