package domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class CPMPVertex {

    private int id;

    @PlanningVariable(valueRangeProviderRefs = "mediansRange")
    private CPMPMedian median;

    public CPMPVertex() {
    }

    public CPMPVertex(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CPMPMedian getMedian() {
        return median;
    }

    public void setMedian(CPMPMedian median) {
        this.median = median;
    }

    public int getDistanceToMedian() {
        return CPMPSolution.getDistance(id, median.getId());
    }
}
