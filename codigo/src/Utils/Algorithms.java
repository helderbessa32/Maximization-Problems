package Utils;

import Graph.*;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import static Utils.Utils.calculateMaxFlowPath;
import static Utils.Utils.setVisitedEdges;

//! Class Algorithms
public class Algorithms {

    public static ArrayList<Node> getPathForSecondScenery(Graph graph) {

        Queue<Node> queue = new LinkedList<>();
        Node dest = graph.getNodes().get(graph.getNodes().size()-1);
        Node source = graph.getNodes().get(0);
        ArrayList<Node> path = new ArrayList<>();

        queue.add(source);

        while(!queue.isEmpty()) {
            Node curr = queue.poll();
            path.add(curr);
            if(curr.equals(dest)) {
                return path;
            }
            for(Edge edge : curr.getOutgoingEdges()) {
                edge.setVisited(true);
                if(edge.getFlow() > 0
                        && !edge.isVisited()) {
                    queue.add(edge.getDest());
                }
            }
        }

        return null;
    }

    //! Breadth-first search (second scenery)
    //!
    //! \param graph
    //! \return pair
    public static Pair<ArrayList<Node>, Integer> BFS(Graph graph) {

        Queue<Node> queue = new LinkedList<>();
        Node dest = graph.getNodes().get(graph.getNodes().size()-1);
        Node source = graph.getNodes().get(0);
        ArrayList<Node> path = new ArrayList<>();

        for(Node node : graph.getNodes()) {
            node.setFatherNode(null);
        }

        queue.add(source);

        while(!queue.isEmpty()) {
            Node curr = queue.poll();

            for(Node child : graph.getGraph().get(curr)) {
                Edge edge = graph.getEdge(curr, child);
                if(child.getFatherNode() == null
                        && edge.getCapacity() - edge.getFlow() > 0) {
                    child.setFatherNode(curr);
                    if(child.equals(dest)) {
                        int maxF = calculateMaxFlowPath(graph, path);
                        path.add(0, source);
                        return new Pair<>(path, maxF);
                    }
                    queue.add(child);
               }
            }
        }

        return new Pair<ArrayList<Node>, Integer>(new ArrayList<>(), 0);
    }
    //! Breadth-first search (first scenery)
    //!
    //! \param graph
    //! \return pair
    public static Pair<ArrayList<Node>, Integer> BFS_N(Graph graph) {

        ArrayList<Node> path = new ArrayList<>();
        LinkedList<Node> queue = new LinkedList<>();

        for(Node node : graph.getNodes()) {
            node.setVisited(false);
            node.setFatherNode(null);
        }

        queue.add(graph.getNodes().get(0));
        graph.getNodes().get(0).setVisited(true);
        while(!queue.isEmpty()) {
            Node currentNode = queue.poll();
            for(Node child : graph.getGraph().get(currentNode)) {
                if(graph.getEdge(currentNode, child).isVisited()) {
                    continue;
                }
                if(!child.isVisited()) {
                    child.setFatherNode(currentNode);
                    child.setVisited(true);
                    queue.add(child);
                }
            }
        }

        int maxP = calculateMaxFlowPath(graph, path);
        setVisitedEdges(graph, path);

        return new Pair<>(path, maxP);
    }

    //! Edmonds Karp algorithm
    //!
    //! \param residual graph
    //! \return max flow
    public static int Edmonds_Karp(Graph rGraph) {

        int maxFlow = 0;

        while(true) {

            Pair<ArrayList<Node>, Integer> pathSolution = BFS(rGraph);
            ArrayList<Node> path = pathSolution.getV1();
            Integer flowPath = pathSolution.getV2();

            if(flowPath == 0) {
                break;
            }
            maxFlow += flowPath;

            Node currentNode = path.get(0);
            for(int i=1; i<path.size(); i++) {
                Node prev = currentNode;
                currentNode = path.get(i);
                Edge prevCurr = rGraph.getEdge(prev, currentNode);
                prevCurr.increaseFlow(flowPath);
                Edge currPrev = rGraph.getEdge(currentNode, prev);
                currPrev.decreaseFlow(flowPath);
            }
        }

        return maxFlow;
    }
    //! Maximum capacity paths
    //!
    //! \param graph
    //! \param path
    //! \return maximum capacity
    public static int CaminhosCapacidadeMaxima(Graph graph, ArrayList<Node> path) {

        PriorityQueue<Node> maxQueue = new PriorityQueue<>();

        for (Node node : graph.getNodes()) {
            node.setCapacity(0);
        }

        maxQueue.add(graph.getNodes().get(0));
        graph.getNodes().get(0).setCapacity(Integer.MAX_VALUE);

        while (!maxQueue.isEmpty()) {
            Node currentNode = maxQueue.poll();
            for (Edge edge : currentNode.getOutgoingEdges()) {
                if (Math.min(currentNode.getCapacity(), edge.getCapacity()) > edge.getDest().getCapacity()) {
                    edge.getDest().setCapacity(Math.min(currentNode.getCapacity(), edge.getCapacity()));
                    edge.addFatherNodeToDestNode(currentNode);
                    maxQueue.add(edge.getDest());
                }
            }
        }

        return calculateMaxFlowPath(graph, path);
    }
    //! Critical path method
    //!
    //! \param graph
    //! \return minimum duration
    public static int criticalPathMethod(Graph graph) {

        for (Node node : graph.getNodes()) {
            for (Edge edge : node.getOutgoingEdges()) {
                edge.getDest().incrementDegree();
            }
        }

        Queue<Node> queue = new LinkedList<>();
        int durMin = -1;
        Node nf = null;

        for (Node _node : graph.getNodes()) {
            if (_node.getDegree() == 0) {
                queue.add(_node);
            }
        }

        while (!queue.isEmpty()) {
            Node cur = queue.poll();

            if (durMin < cur.getES()) {
                durMin = cur.getES();
                nf = cur;
            }
            for (Edge edge : cur.getOutgoingEdges()) {
                Node dest = edge.getDest();
                if (dest.getES() < cur.getES() + edge.getDuration()) {
                    dest.setES(cur.getES() + edge.getDuration());
                    dest.setPrec(cur);
                }
                dest.decrementDegree();
                if (dest.getDegree() == 0) {
                    queue.add(dest);
                }

            }
        }

        return durMin;
    }

}
