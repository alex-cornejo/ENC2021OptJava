package app;

import domain.CPMPMedian;
import domain.CPMPSolution;
import domain.CPMPVertex;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.graphicGraph.stylesheet.Color;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.view.Viewer;
import org.optaplanner.core.api.solver.SolverFactory;
import util.FileUtil;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CPMPMain {
    static final Color[] colors = {Color.GREEN, Color.BLUE, Color.CYAN, Color.ORANGE,
            Color.BLACK, Color.MAGENTA, Color.PINK, Color.RED, Color.LIGHT_GRAY};
    static List<double[]> coordinates;

    static void printGraph(Map<Integer, List<Integer>> solution, Graph graph){
        graph.clear();
        int idx = 0;
        for (var median : solution.entrySet()) {
            var color = colors[idx++ % colors.length];
            var sMedian = String.valueOf(median.getKey());
            if(graph.getNode(sMedian)==null)
                graph.addNode(sMedian);
            var node1 = graph.getNode(sMedian);
            node1.setAttribute("ui.style", String.format("fill-color: rgb(%d,%d,%d);", color.getRed(), color.getGreen(), color.getBlue()));
            node1.setAttribute("x", coordinates.get(median.getKey())[0]);
            node1.setAttribute("y", coordinates.get(median.getKey())[1]);
            for (var vertex : median.getValue()) {
                var sVertex = String.valueOf(vertex);
                if(graph.getNode(sVertex)==null)
                    graph.addNode(sVertex);
                var node2 = graph.getNode(sVertex);
                node2.setAttribute("x", coordinates.get(vertex)[0]);
                node2.setAttribute("y", coordinates.get(vertex)[1]);
                node2.setAttribute("ui.style", String.format("fill-color: rgb(%d,%d,%d);",
                        color.getRed(), color.getGreen(), color.getBlue()));

                var edgeName = String.format("(%d,%d)", median.getKey(), vertex);
                graph.addEdge(edgeName, sMedian, sVertex);
                graph.getEdge(edgeName).setAttribute("ui.style", String.format("fill-color: rgb(%d,%d,%d);",
                        color.getRed(), color.getGreen(), color.getBlue()));
            }
        }
    }

    public static void main(String[] args) throws IOException {

        // read solver configuration
        SolverFactory<CPMPSolution> solverFactory = SolverFactory.createFromXmlResource(
                "cpmp.xml");

        // read vertices from file
        String instance = "CPMP\\dataset\\URDI-300.tsp";
        coordinates = FileUtil.readNodes(instance);
        int[][] D = FileUtil.getAdjacencyMatrix(coordinates);

        // configuration of parameters
        int n = D.length;
        int p = 10;
        int Q = 30;

        // create graph to be printed
        Graph graph = new MultiGraph("p-median");
        for (int idx = 0; idx < n; idx++) {
            var sMedian = String.valueOf(idx);
            graph.addNode(sMedian);
            graph.getNode(sMedian).setAttribute("x", coordinates.get(idx)[0]);
            graph.getNode(sMedian).setAttribute("y", coordinates.get(idx)[1]);
        }
        System.setProperty("org.graphstream.ui", "swing");
        Viewer viewer = new SwingViewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        var view = viewer.addDefaultView(false);
        viewer.disableAutoLayout();

        // create window to print graph
        JFrame frame = new JFrame("capacitated p-median problem");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add((Component) view);
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // create p-median java objects
        List<CPMPMedian> mediansRange = new ArrayList<>();
        for (int i = 0; i < p; i++) {
            mediansRange.add(new CPMPMedian(Q));
        }
        // create vertices to be assigned to p-medians
        List<CPMPVertex> vertices = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            vertices.add(new CPMPVertex(i));
        }

        var cpmp = new CPMPSolution(mediansRange, vertices, p, D);
        var solver = solverFactory.buildSolver();
        solver.addEventListener(event -> {
            System.out.println(event.getNewBestSolution().getScore());
            var bestKnownSol = event.getNewBestSolution();
            var solution = new HashMap<Integer, List<Integer>>();
            for (var node : bestKnownSol.getVertices()) {
                if (!solution.containsKey(node.getMedian().getId())) {
                    solution.put(node.getMedian().getId(), new ArrayList<>());
                }
                solution.get(node.getMedian().getId()).add(node.getId());
            }

            // print graph again
            printGraph(solution, graph);

        });

        // solves the p-median problem
        var solvedProblem = solver.solve(cpmp);

        // print feasibility of solution and its score
        System.out.println(solvedProblem.getScore().isFeasible());
        System.out.println(solvedProblem.getScore());

        // improves quality of printed graph
        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");
    }
}
