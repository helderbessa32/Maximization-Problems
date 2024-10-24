package Graph;

import java.util.ArrayList;
import java.util.Objects;

//! Class Node
public class Node implements Comparable<Node> {
    private int value;
    private int capacity;
    private ArrayList<Edge> outgoingEdges = new ArrayList<>();
    private Node fatherNode;
    private boolean visited = false;

    private int ES = 0;
    private Node prec;
    private int degree  = 0;

    //! Constructor
    //!
    //! \param node value
    public Node(int value) {
        this.value = value;
        this.fatherNode = null;
    }

    //! Add outgoing edge
    //!
    //! \param edge
    public void addOutgoingEdge(Edge edge)  {
        this.outgoingEdges.add(edge);
    }
    //! Get node value
    //!
    //! \param node value
    public int getValue() {
        return value;
    }
    //! Get outgoing edge
    //!
    //! \return arraylist with outgoing edges
    public ArrayList<Edge> getOutgoingEdges() {
        return outgoingEdges;
    }

    public int getES() {
        return ES;
    }

    //! Get capacity
    //!
    //! \param capacity
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    //! Set father node
    //!
    //! \param father node
    public void setFatherNode(Node fatherNode) {
        this.fatherNode = fatherNode;
    }

    public void setES(int ES) {
        this.ES = ES;
    }

    public void setPrec(Node prec) {
        this.prec = prec;
    }

    //! Get capacity
    //!
    //! \return capacity
    public int getCapacity() {
        return capacity;
    }
    //! Get father node
    //!
    //! \return father node
    public Node getFatherNode() {
        return fatherNode;
    }

    public int getDegree() {
        return degree;
    }

    public Node getPrec() {
        return prec;
    }

    //! Check if the node has been visited
    //!
    //! \return corresponding bool
    public boolean isVisited() {
        return visited;
    }
    //! Set node as visited or not visited
    //!
    //! \param corresponding bool
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    //! Node to string
    //!
    //! \return string


    public void setDegree(int degree) {
        this.degree = degree;
    }

    @Override
    public String toString() {
        return "Node(" + value + ")";
    }
    //! Compares two node values
    //!
    //! \return corresponding bool
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return value == node.value;
    }
    //! Returns value for the object
    //!
    //! \return corresponding integer
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
    //! Compares two nodes
    //!
    //! \return 0 if the capacities of the nodes are equal, -1 if it is less than the capacity of the other node and 1 if it is greater
    @Override
    public int compareTo(Node node) {
        if(this.getCapacity() < node.getCapacity()) {
            return 1;
        } else if (this.getCapacity() > node.getCapacity()) {
            return -1;
        } else {
            return 0;
        }
    }

    public void incrementDegree() {
        this.degree++;
    }

    public void decrementDegree() {
        this.degree--;
    }
}
