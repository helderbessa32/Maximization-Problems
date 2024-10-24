package Utils;

import Graph.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

//! Class Utils
public class Utils {

    //! Breaks a given string into an array of substrings. Spaces are the delimiter
    //!
    //! \param line
    //! \return parsed line
    public static String[] parseLine(String line) {
        return line.split(" ");
    }

    //! Read file information
    //!
    //! \param graph
    public static void readFromFile(Graph graph) {

        try {
            File myObj = new File("../dataset/Tests/test.txt");
            Scanner myReader = new Scanner(myObj);
            String line = myReader.nextLine();

            String[] values = Utils.parseLine(line);
            int numNodes = Integer.parseInt(values[1]);

            for(int i=0; i<numNodes; i++) {

                line = myReader.nextLine();
                values = Utils.parseLine(line);

                Node source;
                Node destination;

                if((source = graph.searchNode(Integer.parseInt(values[0]))) == null) {
                    source = new Node(Integer.parseInt(values[0]));
                }
                if((destination = graph.searchNode(Integer.parseInt(values[1]))) == null) {
                    destination = new Node(Integer.parseInt(values[1]));
                }

                graph.addEdge(source, destination, Integer.parseInt(values[2]), Integer.parseInt(values[3]), false);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        graph.addNodesToList();
    }

    //! Calculate max flow path
    //!
    //! \param graph
    //! \param path
    //! \return flow
    public static int calculateMaxFlowPath(Graph graph, ArrayList<Node> path) {

        Node currentNode = graph.getNodes().get(graph.getNodes().size()-1);
        Node lastNode;
        path.add(currentNode);
        Node finalNode = graph.getNodes().get(1);

        int maxPeople = Integer.MAX_VALUE;


        while(!currentNode.equals(finalNode)) {
            lastNode = currentNode;
            currentNode = currentNode.getFatherNode();
            if(currentNode == null) {
                break;
            }
            path.add(currentNode);
            for(Edge edge : currentNode.getOutgoingEdges()) {
                if(edge.getDest().equals(lastNode)
                        && maxPeople > edge.getCapacity()) {
                    maxPeople = edge.getCapacity();
                }
            }
        }
        Collections.reverse(path);

        return maxPeople;
    }

    //! Maximum capacity path
    //!
    //! \param graph
    //! \param path
    //! \return flow
    public static int CaminhosCapacidadeMaxima(Graph graph, ArrayList<Node> path) {

        PriorityQueue<Node> maxQueue = new PriorityQueue<>();

        for(Node node : graph.getNodes()) {
            node.setCapacity(0);
        }

        maxQueue.add(graph.getNodes().get(0));
        graph.getNodes().get(0).setCapacity(Integer.MAX_VALUE);

        while(!maxQueue.isEmpty()) {
            Node currentNode = maxQueue.poll();
            for(Edge edge : currentNode.getOutgoingEdges()) {
                if(Math.min(currentNode.getCapacity(), edge.getCapacity()) > edge.getDest().getCapacity()) {
                    edge.getDest().setCapacity(Math.min(currentNode.getCapacity(), edge.getCapacity()));
                    edge.addFatherNodeToDestNode(currentNode);
                    maxQueue.add(edge.getDest());
                }
            }
        }

        return calculateMaxFlowPath(graph, path);
    }

    //! Set visited edges
    //!
    //! \param graph
    //! \param path
    public static void setVisitedEdges(Graph graph, ArrayList<Node> path) {

        Node lastNode = path.get(0);
        for (int i = 1; i < path.size(); i++) {
            Edge edge = graph.getEdge(lastNode, path.get(i));
            edge.setVisited(true);
            lastNode = path.get(i);
        }
    }

    public int comparator(Node n1, Node n2) {
        return n1.getValue() - n2.getValue();
    }
}
