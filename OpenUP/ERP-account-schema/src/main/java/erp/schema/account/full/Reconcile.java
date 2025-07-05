package erp.schema.account.full;

import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import erp.schema.account.Move;
import erp.schema.account.move.Line;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity
@Description("Full Reconcile")
public class Reconcile {

	@Transient
	private List<Integer> partial_reconcile_ids;
	
	@OneToMany(targetEntity = erp.schema.account.partial.Reconcile.class, fetch = FetchType.LAZY, mappedBy = "full_reconcile_id")
	@Description("Reconciliation Parts")
	private List<erp.schema.account.partial.Reconcile>  partial_reconciles;
	
	@Transient
	private List<Integer> reconciled_line_ids;
	
	@OneToMany(targetEntity = Line.class, fetch = FetchType.LAZY, mappedBy = "full_reconcile_id")
	@Description("Matched Journal Items")
	private List<Line> reconciled_lines;
	
	@Transient
	private Integer exchange_move_id;
	
	@ManyToOne(targetEntity = Move.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "exchange_move_id")
	private Move exchange_move;
}
