package epf.planning;

import java.util.Arrays;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;
import epf.planning.schema.Activity;
import epf.planning.schema.Iteration;
import epf.planning.schema.Milestone;
import epf.planning.schema.Phase;

/**
 * @author PC
 *
 */
public class Constraints implements ConstraintProvider {

	@Override
	public Constraint[] defineConstraints(final ConstraintFactory constraintFactory) {
		return Arrays
				.asList(
						constraintFactory
						.from(Activity.class)
						.join(Activity.class, 
								Joiners.equal(Activity::getParentActivities)
								)
						.penalize("Activity.parentActivities", HardSoftScore.ONE_HARD),
						constraintFactory
						.from(Iteration.class)
						.join(Iteration.class, 
								Joiners.equal(Iteration::getParentActivities)
								)
						.penalize("Iteration.parentActivities", HardSoftScore.ONE_HARD),
						constraintFactory
						.from(Milestone.class)
						.join(Milestone.class, 
								Joiners.equal(Milestone::getPredecessor)
								)
						.penalize("Milestone.predecessor", HardSoftScore.ONE_HARD),
						constraintFactory
						.from(Phase.class)
						.join(Phase.class, 
								Joiners.equal(Phase::getParentActivities)
								)
						.penalize("Phase.parentActivities", HardSoftScore.ONE_HARD)
						)
				.toArray(new Constraint[0]);
	}

}
