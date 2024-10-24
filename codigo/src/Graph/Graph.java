package Graph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

//! Class Graph
public class Graph {

    private final HashMap<Node, List<Node>> graph = new HashMap<>();
    private final ArrayList<Node> nodes = new ArrayList<>();

    //! Add edge
    //!
    //! \param source node
    //! \param destination node
    //! \param capacity
    //! \param duration
    //! \param bidirectional corresponding bool
    public void addEdge(Node source, Node destination, int capacity, int duration, boolean biDirectional) {

        Edge edge = new Edge(source, destination, capacity, duration);
        source.addOutgoingEdge(edge);

        if (!graph.containsKey(source)) {
            addNode(source);
        }

        if (!graph.containsKey(destination)) {
            addNode(destination);
        }

        graph.get(source).add(destination);
        if(biDirectional) {
            graph.get(destination).add(source);
        }
    }
    //! Add nodes to list
    //!
    public void addNodesToList() {
        this.nodes.addAll(this.graph.keySet());
    }
    //! Verifies if the graph contains a vertex or not
    //!
    //! \param vertex
    public void hasVertex(Node vertex) {
        if(graph.containsKey(vertex)) {
            System.out.println("The Graph contains " + vertex + " as a vertex");
        }else {
            System.out.println("The Graph does not contain " + vertex + " as a vertex");
        }
    }
    //! Verifies if the graph contains an edge or not
    //!
    //! \param source node
    //! \param destination node
    public void hasEdge(Node source, Node destination) {
        if(graph.get(source).contains(destination)) {
            System.out.println("The Graph has an edge between " + source + " and " + destination);
        }else {
            System.out.println("The Graph has no edge between " + source + " and " + destination);
        }
    }
    //! Print graph
    //!
    //! \return string
    public String printGraph() {

        System.out.println(this.graph.size());

        StringBuilder builder = new StringBuilder();

        for(Node node : graph.keySet()) {
            builder.append(node.getValue() + ": ");
            for(Node _node: graph.get(node)) {
                builder.append(_node.getValue() + " ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
    //! Add node
    //!
    //! \param vertex
    private void addNode(Node vertex) {
        graph.put(vertex, new LinkedList<>());
    }
    //! Print edges
    //!
    public void printEdges() {

        for(Node node : this.graph.keySet()) {
            System.out.println(">>" + node.getValue());
            for(int i=0; i<node.getOutgoingEdges().size(); i++) {
                System.out.println(node.getOutgoingEdges().get(i).getDest().getValue());
            }
            System.out.println("=============");
        }
    }
    //! Search node
    //!
    //! \param node value
    //! \return node
    public Node searchNode(int val) {

        for(Node node: this.graph.keySet()) {
            if(node.getValue() == val) {
                return node;
            }
        }
        return null;
    }
    //! Get graph
    //!
    //! \return graph
    public Map<Node, List<Node>> getGraph() {
        return graph;
    }
    //! Get nodes
    //!
    //! \return nodes
    public ArrayList<Node> getNodes() {
        return nodes;
    }
    //! Add node to array
    //!
    //! \param node
    public void addNodeToArray(Node node) {
        this.nodes.add(node);
    }
    //! Get edge
    //!
    //! \return edge
    public Edge getEdge(Node source, Node dest) {

        for(Edge edge : source.getOutgoingEdges()) {
            if(edge.getDest().equals(dest)) {
                return edge;
            }
        }
        return null;
    }
    //! Create residual graph
    //!
    //! \param graph
    public void createResidualGraph(Graph residualGraph) {

        for(Node source: this.getGraph().keySet()) {
            for(Node dest : this.getGraph().get(source)) {
                Edge edge = this.getEdge(source, dest);
                if(edge != null) {

                    Node rSource;
                    Node rDest;

                    if((rDest = residualGraph.searchNode(source.getValue())) == null) {
                        rDest = new Node(source.getValue());
                    }
                    if((rSource = residualGraph.searchNode(dest.getValue())) == null) {
                        rSource = new Node(dest.getValue());
                    }

                    residualGraph.addEdge(rSource, rDest, edge.getCapacity(), edge.getDuration(), false);
                    residualGraph.addEdge(rDest, rSource, edge.getCapacity(), edge.getDuration(), false);

                    Edge prevCurr = getEdge(rDest, rSource);
                    prevCurr.setFlow(0);

                    Edge currPrev = getEdge(rSource, rDest);
                    currPrev.setFlow(edge.getCapacity());
                }
            }
        }

        residualGraph.getNodes().addAll(residualGraph.getGraph().keySet());
    }
}