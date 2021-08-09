package score;

import domain.CPMPMedian;
import domain.CPMPVertex;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

public class CPMPConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                // call constraint methods here

        };
    }

    private Constraint maxCapacity(ConstraintFactory factory) {
        return factory.from(CPMPMedian.class)
                .filter((median) -> median.getVertices().size() > median.getQ())
                .penalize("max capacity",
                        HardSoftScore.ONE_HARD, (median) -> median.getVertices().size() - median.getQ());
    }

    private Constraint minDistance(ConstraintFactory constraintFactory) {
        // penalize soft here
        // penalize the distance from each vertex to its median
        return ...
    }
}