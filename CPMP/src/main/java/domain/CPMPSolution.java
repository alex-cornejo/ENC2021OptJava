package domain;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.List;

@PlanningSolution
public class CPMPSolution {

    private static int p;
    private static int[][] D;

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "mediansRange")
    private List<CPMPMedian> medians;

    @PlanningEntityCollectionProperty
    private List<CPMPVertex> vertices;

    @PlanningScore
    private HardSoftScore score;

    public CPMPSolution() {
    }

    public CPMPSolution(List<CPMPMedian> medians, List<CPMPVertex> vertices, int p, int[][] D) {
        this.medians = medians;
        this.vertices = vertices;
        CPMPSolution.p = p;
        CPMPSolution.D = D;
    }

    public static int getDistance(int i, int j) {
        return D[i][j];
    }

    public static int getP() {
        return p;
    }

    public static void setP(int p) {
        CPMPSolution.p = p;
    }

    public static int[][] getD() {
        return D;
    }

    public static void setD(int[][] d) {
        D = d;
    }

    public List<CPMPMedian> getMedians() {
        return medians;
    }

    public void setMedians(List<CPMPMedian> medians) {
        this.medians = medians;
    }

    public List<CPMPVertex> getVertices() {
        return vertices;
    }

    public void setVertices(List<CPMPVertex> vertices) {
        this.vertices = vertices;
    }

    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }
}
