package Graph;


import java.util.Objects;

//! Class Edge
public class Edge {
    private Node src;
    private Node dest;
    private int duration;
    private int flow;
    private int capacity;
    private boolean visited;

    //! Constructor
    //!
    //! \param source node
    //! \param destination node
    //! \param capacity
    //! \param duration
    public Edge(Node src, Node dest, int capacity, int duration) {
        this.src = src;
        this.dest = dest;
        this.duration = duration;
        this.capacity = capacity;
        this.flow = 0;
        this.visited = false;
    }
    //! Get source node
    //!
    //! \return source node
    public Node getSrc() {
        return src;
    }
    //! Get destination node
    //!
    //! \return destination node
    public Node getDest() {
        return dest;
    }
    //! Get duration node
    //!
    //! \return duration node
    public int getDuration() {
        return duration;
    }
    //! Get capacity
    //!
    //! \return capacity
    public int getCapacity() {
        return capacity;
    }
    //! Get flow
    //!
    //! \return flow
    public int getFlow() {
        return flow;
    }
    //! Check if the edge has been visited
    //!
    //! \return corresponding bool
    public boolean isVisited() {
        return visited;
    }
    //! Set duration
    //!
    //! \param duration
    public void setDuration(int duration) {
        this.duration = duration;
    }
    //! Set capacity
    //!
    //! \param capacity
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    //! Set flow
    //!
    //! \param flow
    public void setFlow(int flow) {
        this.flow = flow;
    }
    //! Set source
    //!
    //! \param source
    public void setSrc(Node src) {
        this.src = src;
    }
    //! Set destination
    //!
    //! \param destination
    public void setDest(Node dest) {
        this.dest = dest;
    }
    //! Set edge as visited or not visited
    //!
    //! \param corresponding bool
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    //! Decrease flow
    //!
    //! \param flow
    public void decreaseFlow(int flow) {
        this.flow -= flow;
    }
    //! Increase flow
    //!
    //! \param flow
    public void increaseFlow(int flow) {
        this.flow += flow;
    }
    //! Add father node to the destination node
    //!
    //! \param father node
    public void addFatherNodeToDestNode(Node fatherNode) {
        this.dest.setFatherNode(fatherNode);
    }
    //! Compares two edge values
    //!
    //! \return corresponding bool
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return src == edge.src && dest == edge.dest;
    }
    //! Returns source and destination for the object
    //!
    //! \return corresponding integer
    @Override
    public int hashCode() {
        return Objects.hash(src, dest);
    }
}
