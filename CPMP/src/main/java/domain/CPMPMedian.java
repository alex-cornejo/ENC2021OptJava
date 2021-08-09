package domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.CustomShadowVariable;
import org.optaplanner.core.api.domain.variable.InverseRelationShadowVariable;
import org.optaplanner.core.api.domain.variable.PlanningVariableReference;
import score.CPMPVariableListener;

import java.util.ArrayList;
import java.util.List;

@PlanningEntity
public class CPMPMedian {

    private Integer id;

    private int Q;

    @InverseRelationShadowVariable(sourceVariableName = "median")
    private List<CPMPVertex> vertices = new ArrayList<>();

    public CPMPMedian() {
    }

    public CPMPMedian(int Q) {
        this.Q = Q;
    }

    @CustomShadowVariable(variableListenerClass = CPMPVariableListener.class,
            sources = {@PlanningVariableReference(variableName = "vertices")})
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<CPMPVertex> getVertices() {
        return vertices;
    }

    public void setVertices(List<CPMPVertex> vertices) {
        this.vertices = vertices;
    }

    public int getQ() {
        return Q;
    }

    public void setQ(int q) {
        Q = q;
    }

}
