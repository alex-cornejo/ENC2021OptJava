package score;

import domain.Box;
import domain.Knapsack;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

import static org.optaplanner.core.api.score.stream.ConstraintCollectors.sum;

public class KnapsackConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                // call constraint functions here

        };
    }

    private Constraint maxWeight(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Box.class)
                .filter(Box::getSelected)
                .groupBy(sum(Box::getWeight)).join(Knapsack.class)
                .filter((weightSum, knapsack) -> weightSum > knapsack.getCapacity())
                // add penalization here
                .penalize(...);
    }

    private Constraint maxProfit(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Box.class)
                .filter(Box::getSelected)
                // add reward here
                .reward();
    }
}