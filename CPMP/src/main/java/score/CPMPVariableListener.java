package score;

import domain.CPMPMedian;
import domain.CPMPSolution;
import org.optaplanner.core.api.domain.variable.VariableListener;
import org.optaplanner.core.api.score.director.ScoreDirector;

public class CPMPVariableListener implements VariableListener<CPMPSolution, CPMPMedian> {
    @Override
    public void beforeEntityAdded(ScoreDirector<CPMPSolution> scoreDirector, CPMPMedian median) {
        // TODO
    }

    @Override
    public void afterEntityAdded(ScoreDirector<CPMPSolution> scoreDirector, CPMPMedian median) {
        updateMedianId(scoreDirector, median);
    }

    @Override
    public void beforeVariableChanged(ScoreDirector<CPMPSolution> scoreDirector, CPMPMedian median) {
        // TODO
    }

    @Override
    public void afterVariableChanged(ScoreDirector<CPMPSolution> scoreDirector, CPMPMedian median) {
        updateMedianId(scoreDirector, median);
    }

    @Override
    public void beforeEntityRemoved(ScoreDirector<CPMPSolution> scoreDirector, CPMPMedian median) {
        // TODO
    }

    @Override
    public void afterEntityRemoved(ScoreDirector<CPMPSolution> scoreDirector, CPMPMedian median) {
        // TODO
    }

    private void updateMedianId(ScoreDirector<CPMPSolution> scoreDirector,
                                CPMPMedian sourceMedian) {
        if (!sourceMedian.getVertices().isEmpty()) {
            int minDistance = Integer.MAX_VALUE;
            int id = -1;
            for (var v : sourceMedian.getVertices()) {
                int d = 0;
                for (var u : sourceMedian.getVertices()) {
                    d += CPMPSolution.getDistance(u.getId(), v.getId());
                }
                if (minDistance > d) {
                    minDistance = d;
                    id = v.getId();
                }
            }
            CPMPMedian shadowMedian = sourceMedian;
            scoreDirector.beforeVariableChanged(sourceMedian, "id");
            shadowMedian.setId(id);
            scoreDirector.afterVariableChanged(shadowMedian, "id");
        }
    }
}
