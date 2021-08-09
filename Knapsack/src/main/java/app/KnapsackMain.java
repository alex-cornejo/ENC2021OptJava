package app;

import org.optaplanner.core.config.solver.SolverConfig;
import score.KnapsackConstraintProvider;
import domain.KnapsackSolution;
import domain.Box;
import domain.Knapsack;
import org.optaplanner.core.api.solver.SolverFactory;

import java.time.Duration;
import java.util.Arrays;

public class KnapsackMain {
    public static void main(String[] args) {
        // create Box objects
        var box1 = new Box(12, 4, "green");
        var box2 = new Box(2, 2, "blue");
        var box3 = new Box(1, 1, "red");
        var box4 = new Box(4, 10, "yellow");
        var box5 = new Box(1, 2, "gray");

        var boxes = Arrays.asList(box1, box2, box3, box4, box5);

        // create the knapsack
        var knapsack = new Knapsack();
        knapsack.setCapacity(15);

        var problem = new KnapsackSolution(boxes, knapsack);
//        SolverFactory<KnapsackSolution> solverFactory = SolverFactory.createFromXmlResource(
//                "knapsack.xml");
        SolverFactory<KnapsackSolution> solverFactory = SolverFactory.create(new
                SolverConfig()
                .withSolutionClass(KnapsackSolution.class)
                .withEntityClasses(Box.class)
                .withConstraintProviderClass(KnapsackConstraintProvider.class)
                .withTerminationSpentLimit(Duration.ofSeconds(10)));

        var solver = solverFactory.buildSolver();
        var solvedProblem = solver.solve(problem);
        System.out.println(solvedProblem.getScore());
        for (var ingot : solvedProblem.getBoxes()) {
            if (ingot.getSelected()) {
                System.out.println(ingot.getColor());
            }
        }
    }
}
