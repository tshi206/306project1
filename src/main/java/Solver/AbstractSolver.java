package Solver;

import CommonInterface.ISearchState;
import CommonInterface.ISolver;
import GUI.IUpdatableState;
import Graph.Exceptions.GraphException;
import SolverOld.SearchState;
import lombok.Data;
import lombok.Getter;
//import org.graphstream.graph.Graph;

import java.util.Arrays;

import Graph.EdgeWithCost;
import Graph.Graph;
import Graph.Vertex;

@Data
abstract public class AbstractSolver implements ISolver {
//    @Getter
//    protected final Graph graph;
//    @Getter
//    protected final int processorCount;
//    protected GUIUpdater updater;
//
//    void scheduleVertices(SearchState s) {
//        final int[] processors = Arrays.stream(s.getProcessors()).map(x -> x+1).toArray();
//        final int[] startTimes = s.getStartTimes();
//        graph.getNodeSet().forEach(v -> {
//            int id = v.getIndex();
//            v.addAttribute("Processor", processors[id]);
//            v.addAttribute("ST", startTimes[id]);
//        });
//    }
//    protected static class GUIUpdater {
//        IUpdatableState ui;
//        public GUIUpdater(IUpdatableState ui) { this.ui = ui; }
//        public void update(ISearchState searchState) {
//            ui.updateWithState(searchState);
//        }
//    }
//
//    @Override
//    public void associateUI(IUpdatableState ui) {
//        this.updater = new GUIUpdater(ui);
//    }
//}
    @Getter
    protected final Graph<Vertex, EdgeWithCost<Vertex>> graph;
    @Getter
    protected final int processorCount;
    protected Solver.AbstractSolver.GUIUpdater updater;

    protected void scheduleVertices(SearchState s) {
        final int[] processors = Arrays.stream(s.getProcessors()).map(x -> x+1).toArray();
        final int[] startTimes = s.getStartTimes();
        graph.getVertices().forEach(v -> {
            int id = v.getAssignedId();
            try {
                graph.scheduleVertex(v ,processors[id], startTimes[id]);
            } catch (GraphException e) {
                e.printStackTrace();
            }
        });
    }

    protected static class GUIUpdater {
        IUpdatableState ui;
        public GUIUpdater(IUpdatableState ui) { this.ui = ui; }
        public void update(ISearchState searchState) {
            ui.updateWithState(searchState);
        }
    }

    public void associateUI(IUpdatableState ui) {
        this.updater = new Solver.AbstractSolver.GUIUpdater(ui);
    }
}