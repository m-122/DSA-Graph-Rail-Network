public class TableRow {
    //This class is used when finding the best path.
    //It keeps track of each vertex, each vertices previous vertex, and the edge traversed
    // to get to the vertex (the edge between vertex and previous.

    private final Vertex vertex;
    private Vertex previous;
    private Edge edgeTraversed;


    public TableRow(Vertex vertex, Vertex previous, Edge edgeTraversed) {
        this.vertex = vertex;
        this.previous = previous;
        this.edgeTraversed = edgeTraversed;
    }

    //Getter methods
    public Vertex getVertex() {
        return vertex;
    }

    public Vertex getPrevious() {
        return previous;
    }

    public Edge getEdgeTraversed(){
        return edgeTraversed;
    }

    //These values may change during execution
    public void setPrevious(Vertex previous){
        this.previous = previous;
    }

    public void setEdgeTraversed(Edge edgeTraversed){
        this.edgeTraversed = edgeTraversed;
    }
}
