package erp.base.schema.report;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import erp.base.schema.ir.ui.View;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "report_layout")
@Description("Report Layout")
@NodeEntity("Report Layout")
public class Layout {
	
	/**
	 * 
	 */
	@jakarta.persistence.Id
	@Id
	private int id;

	/**
	 * 
	 */
	@Column(nullable = false)
	@ManyToOne(targetEntity = View.class)
	@NotNull
	@Description("Document Template")
	@Property
	@Relationship(type = "VIEW")
	private String view_id;
	
	/**
	 * 
	 */
	@Column
	@Description("Preview image src")
	@Property
	private String image;
	
	/**
	 * 
	 */
	@Column
	@Description("Preview pdf src")
	@Property
	private String pdf;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("50")
	@Property
	private Integer sequence = 50;
	
	@Column
	@Property
	private String name;

	public String getView_id() {
		return view_id;
	}

	public void setView_id(String view_id) {
		this.view_id = view_id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
