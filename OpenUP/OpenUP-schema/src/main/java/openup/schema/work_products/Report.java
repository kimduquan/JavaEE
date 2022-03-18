package openup.schema.work_products;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import openup.schema.OpenUP;

/**
 * @author PC
 *
 */
@Type(OpenUP.REPORT)
@Schema(name = OpenUP.REPORT, title = "Report")
@Entity(name = OpenUP.REPORT)
@Table(schema = OpenUP.SCHEMA, name = "OPENUP_REPORT")
public class Report implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 
     */
    @ManyToOne
    @JoinColumn(name = "REPORT")
    private epf.work_products.schema.Report report;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public epf.work_products.schema.Report getReport() {
		return report;
	}

	public void setReport(final epf.work_products.schema.Report report) {
		this.report = report;
	}
}
