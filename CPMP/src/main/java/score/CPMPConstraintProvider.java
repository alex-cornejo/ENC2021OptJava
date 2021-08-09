package score;

import domain.CPMPMedian;
import domain.CPMPVertex;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

public class CPMPConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory factory) {
        return new Constraint[]{
                // call constraint methods here

        };
    }

    private Constraint maxCapacity(ConstraintFactory factory) {
        // penalize hard here
        // penalize if the capacity of a median is exceeded
        return ...
    }

    private Constraint minDistance(ConstraintFactory factory) {
        // penalize soft here
        // penalize the distance from each vertex to its median
        return ...
    }
}