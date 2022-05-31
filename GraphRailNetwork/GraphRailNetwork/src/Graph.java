import java.util.ArrayList;

/**
 * Class Graph provides an implementation of the GraphADT interface class.
 * [insert your candidate number and date here if desired]
 */
public class Graph implements GraphADT {
    private final ArrayList<Vertex> verticesList;
    private final ArrayList<Edge> edgesList;

    /**
     * Constructs a new graph object. The constructor takes a collection of vertices and
     * a collection of edges and initialises the graph. In particular, it instantiates the incidence
     * sequences associated with the vertices.
     *
     * @param vertices a list of vertices
     * @param edges    a list of edges
     */
    public Graph(ArrayList<Vertex> vertices, ArrayList<Edge> edges) {
        // INSERT YOUR CODE HERE
        edgesList = edges;
        verticesList = vertices;
    }

    /**
     * Constructs a new "blank" graph object.
     * This constructor should just initialise the internal storage of edges and vertices
     * for the Graph. (Note that creating a blank Graph object might be useful
     * if there is no vertices or edges to add to it yet).
     */
    public Graph() {
        edgesList = new ArrayList<Edge>();
        verticesList = new ArrayList<Vertex>();
    }

    /**
     * Insert a vertex with name n into the graph.
     *
     * @param n String
     * @return v Vertex that was added
     */
    public Vertex insertVertex(String n) {
        Vertex newVertex = new Vertex(n);
        verticesList.add(newVertex);

        return newVertex;
    }

    /**
     * Remove the given vertex from the graph. The name of the removed vertex is returned, or
     * null if the graph does not contain the vertex.
     *
     * @param v Vertex
     * @return n String representing the name of the removed Vertex
     */
    public String removeVertex(Vertex v) {
        String name = null;

        for (int i = 0; i < verticesList.size(); i++) {
            if (verticesList.get(i) == v) {
                name = verticesList.get(i).getName();
                verticesList.remove(i);
            }
        }

        return name;
    }

    /**
     * Build a new edge with end vertices u and v and insert into the graph.
     * NB: This implementation assumes the vertices u and v must already be in the graph
     * for simplicity sake, so you do not have to do error handling.
     *
     * @param u Vertex
     * @param v Vertex
     * @param n the name of the Edge
     * @return e the Edge that was inserted into the Graph
     */
    public Edge insertEdge(Vertex u, Vertex v, String n) {
        Edge newEdge = new Edge(u, v, n);
        edgesList.add(newEdge);

        return newEdge;
    }

    /**
     * Remove the edge e from the graph. The name of the old edge is returned (or null if the edge is not
     * in the graph).
     *
     * @param e the edge to be removed
     * @return String name of the edge that was removed
     */
    public String removeEdge(Edge e) {
        String name = null;
        for (int i = 0; i < edgesList.size(); i++) {
            if (edgesList.get(i) == e) {
                name = edgesList.get(i).getName();
                edgesList.remove(i);
                //New method removeVertices in Edge.java.
                e.removeVertices();
            }
        }

        return name;
    }

    /**
     * Find and return the vertex opposite v in edge e.
     *
     * @param e an edge
     * @param w a vertex
     * @return Vertex that is opposite of the given vertex along edge e
     */
    public Vertex opposite(Edge e, Vertex w) {
        Vertex returnVertex = null;

        //If vertex u is equal to the input vertex, then vertex v is opposite.
        if (e.getVertex_u() == w) {
            returnVertex = e.getVertex_v();
        } else {
            returnVertex = e.getVertex_u();
        }

        return returnVertex;
    }

    /**
     * Return an iterable collection containing of all of the vertices in the graph.
     *
     * @return ArrayList<Vertex>
     */
    public ArrayList<Vertex> vertices() {
        return verticesList;
    }

    /**
     * Return an iterable collection of all of the edges in the graph.
     *
     * @return ArrayList<Edge>
     */
    public ArrayList<Edge> edges() {
        return edgesList;
    }

    /**
     * Checks whether two vertices are adjacent (i.e. joined by a single edge) or not.
     *
     * @param v a vertex
     * @param w a vertex
     * @return boolean, true if v and w are adjacent, false otherwise
     */
    public boolean areAdjacent(Vertex v, Vertex w) {
        boolean adjacent = false;

        //Loop through edges until vertex v is found. Then check if vertex w shares the same edge
        for (Edge edge : edgesList) {
            if (edge.getVertex_u() == v) {
                if (edge.getVertex_v() == w) {
                    adjacent = true;
                }
            }
        }

        return adjacent;
    }

    /**
     * Finds and returns the set of edges that are incident to a given vertex.
     *
     * @param v the vertex
     * @return ArrayList<Edge> a list of all edges incident on the given vertex
     */
    public ArrayList<Edge> incidentEdges(Vertex v) {

        //Loop through edges. If the edge has v as a vertex, then add it as new incident edge
        for (Edge edge : edgesList) {
            if (edge.getVertex_u() == v || edge.getVertex_v() == v) {
                v.addIncidentEdge(edge);
            }
        }

        return v.getIncidentEdges();
    }

    /**
     * Rename vertex v as n
     *
     * @param v a vertex
     * @param n the new name
     * @return String name of Vertex that was over-written
     */
    public String rename(Vertex v, String n) {
        v.setName(n);

        return v.getName();
    }

    /**
     * Rename edge e as n
     *
     * @param e an edge
     * @param n the new name
     * @return String name of Edge that was over-written
     */
    public String rename(Edge e, String n) {
        e.setName(n);

        return e.getName();
    }

    //Used in bfTraverse.
    private ArrayList<Vertex> visitedVertices = new ArrayList<>();
    private boolean recursion = false;
    private Vertex newStart;

    /**
     * Perform a breadth-first search traversal of the graph (i.e, of the entire rail network).
     * This will work with either connected or unconnected graphs. It works by iterating through
     * the graph vertices and carrying out a breadth-first traversal for each unvisited vertex.
     *
     * @return a list of Vertices in order visited (so v will always be at index 0)
     */
    public ArrayList<Vertex> bfTraverse() {
        //Checks for empty graph.
        if(verticesList.size() == 0){
            return visitedVertices;
        }
        ArrayList<Edge> incidenceEdgeCollection = new ArrayList<>();
        Vertex current;

        //Recursion is false means this is the first bfTraverse, so start from beginning.
        //Recursion is true means this is not first bfTraverse, start from a different point.
        if(!recursion){
            visitedVertices.clear();
            current = verticesList.get(0);
        }else{
            current = newStart;
        }

        //Add current vertex to visitedVertices.
        visitedVertices.add(current);

        //Start loop at visitedVertices.size() - 1. This prevents array index out of
        // bounds errors caused by unconnected components.
        for (int i = visitedVertices.size() - 1; i < verticesList.size(); i++) {
            //If visitedVertices is less than or equal to i, then there are unconnected vertices.
            if(visitedVertices.size() <= i){
                //To get to the unconnected vertices, find them in verticesList and run bfTraverse again.
                for (Vertex vertex : verticesList) {
                    if (!vertex.isVisited()) {
                        recursion = true;
                        newStart = vertex;
                        bfTraverse();
                    }
                }
            }
            current = visitedVertices.get(i);
            current.labelAsVisited();
            incidenceEdgeCollection.addAll(incidentEdges(current));


            for (Edge edge : incidenceEdgeCollection) {
                //Only visit unvisited vertices.
                if (!edge.getVertex_v().isVisited()) {
                    visitedVertices.add(edge.getVertex_v());
                    edge.getVertex_v().labelAsVisited();
                }
                if (!edge.getVertex_u().isVisited()) {
                    visitedVertices.add(edge.getVertex_u());
                    edge.getVertex_u().labelAsVisited();
                }
            }
            //Clear out incidence edges after each itteration.
            incidenceEdgeCollection.removeAll(incidentEdges(current));
        }

        //If the first check doesn't catch unconnected components, then this will.
        for (Vertex vertex : verticesList) {
            if (!vertex.isVisited()) {
                recursion = true;
                newStart = vertex;
                bfTraverse();
            }
        }

        return visitedVertices;
    }


    /**
     * Perform a breadth-first search traversal of the connected component of the graph that
     * starts at the given vertex v.
     *
     * @param v a vertex to start from
     * @return a list of Vertices in order visited (so v will always be at index 0)
     */
    public ArrayList<Vertex> bfTraverse(Vertex v) {
        //Almost the same as other bfTraverse with some changes
        ArrayList<Edge> incidenceEdgeCollection = new ArrayList<>();
        visitedVertices.clear();


        //Start at vertex v
        Vertex current = v;
        visitedVertices.add(current);

        for (int i = 0; i < verticesList.size(); i++) {
            //If visitedVertices size is less than or equal to i then the graph is not connected
            //so return the vertices that can be reached.
            if(visitedVertices.size() <= i){
                return visitedVertices;
            }

            current = visitedVertices.get(i);
            current.labelAsVisited();
            incidenceEdgeCollection.addAll(incidentEdges(current));


            for (Edge edge : incidenceEdgeCollection) {
                if (edge.getVertex_v() != null && !edge.getVertex_v().isVisited()) {
                    visitedVertices.add(edge.getVertex_v());
                    edge.getVertex_v().labelAsVisited();
                }
                if (edge.getVertex_v() != null && !edge.getVertex_u().isVisited()) {
                    visitedVertices.add(edge.getVertex_u());
                    edge.getVertex_u().labelAsVisited();
                }
            }
            incidenceEdgeCollection.removeAll(incidentEdges(current));
        }

        return visitedVertices;
    }

    /**
     * Return a list of all of the vertices reachable from the given 'start' vertex. This works
     * by simply performing a breadth-first search of the graph to label vertices. Any 'visited'
     * vertices have by definition been reached and are returned in a list.
     * (In other words,  return a list of all of the stations that can be reached
     * by rail when starting from v.
     *
     * @param v Vertex
     * @return a list of vertices that have been reached
     */
    public ArrayList<Vertex> allReachable(Vertex v) {
        //Unvisit every vertex before running bfTraverse to avoid incorrect results
        for (Vertex vertex : verticesList) {
            vertex.unVisit();
        }

        return bfTraverse(v);
    }

    /**
     * Determine whether the graph is connected, or not.
     * Note that an empty graph is by definition connected.
     * (in other words, return true if all the stations in the entire rail network
     * can be reached from one another, and false if not.)
     *
     * @return true if the graph is connected; false otherwise.
     */
    public boolean allConnected() {
        //Graph with only 1 vertex is connected.
        if(verticesList.size() <= 1){
            return true;
        }

        //Unvisit every vertex before running bfTraverse to avoid incorrect results
        for (Vertex vertex : verticesList) {
            vertex.unVisit();
        }

        return bfTraverse(verticesList.get(0)).size() >= verticesList.size();
    }

    /**
     * Use breadth-first search traversal to find a 'most direct route' between u and v.
     * In other words, given two stations u and v, return the path with the least amount of
     * stations between them; or return null to indicate that the two stations cannot
     * be reached from one another.
     * The path itself is represented as an ArrayList of Edge objects.
     *
     * @param u start vertex
     * @param v end vertex
     * @return an ArrayList of edges
     */
    public ArrayList<Edge> mostDirectRoute(Vertex u, Vertex v) {
        //Unvisit all vertices before running
        for (Vertex vertex : verticesList) {
            vertex.unVisit();
        }

        //bfs arrays
        ArrayList<Edge> incidenceEdgeCollection = new ArrayList<>();
        ArrayList<Vertex> visitedVertices = new ArrayList<>();
        //arrays used to get path
        ArrayList<TableRow> routeTable = new ArrayList<>();
        ArrayList<Edge> intermediary = new ArrayList<>();
        ArrayList<Edge> path = new ArrayList<>();

        //Returns null if the graph is empty.
        if(verticesList.size() == 0){
            return null;
        }
        //If the target vertex is not in the graph, return null;
        if(!verticesList.contains(v)){
            return null;
        }

        //Initialize a table which will contain all vertices
        //See TableRow class.
        for (Vertex vertex : verticesList) {
            routeTable.add(new TableRow(vertex, null, null));
        }

        //Start bfs at vertex u
        Vertex current = u;
        visitedVertices.add(current);

        for (int i = 0; i < verticesList.size(); i++) {
            //Visited vertices < i would mean there are components that cannot be reached.
            if (visitedVertices.size() > i) {
                current = visitedVertices.get(i);
                current.labelAsVisited();
                incidenceEdgeCollection.addAll(incidentEdges(current));

                for (Edge edge : incidenceEdgeCollection) {
                    if (!edge.getVertex_v().isVisited()) {
                        visitedVertices.add(edge.getVertex_v());
                        edge.getVertex_v().labelAsVisited();

                        //Update the table with new data. See updateTable()
                        updateTable(routeTable, current, edge.getVertex_v(), edge);
                    }
                    if (!edge.getVertex_u().isVisited()) {
                        visitedVertices.add(edge.getVertex_u());
                        edge.getVertex_u().labelAsVisited();

                        //Update the table with new data. See updateTable()
                        updateTable(routeTable, current, edge.getVertex_u(), edge);
                    }
                }
                incidenceEdgeCollection.removeAll(incidentEdges(current));
            }
        }

        //Finding the path
        current = v;
        boolean pathFound = false;

        while (!pathFound) {
            //Follow the previous vertices back from the target vertex to get the path
            for (TableRow tableRow : routeTable) {
                if (tableRow.getVertex() == current) {

                    //If loop hits null, and current is not equal to the start vertex, then
                    //start cannot be reached, so return null. Stop while loop when start vertex found.
                    if (tableRow.getEdgeTraversed() != null) {
                        intermediary.add(tableRow.getEdgeTraversed());
                        current = tableRow.getPrevious();
                    }else if(current == u){
                        pathFound = true;
                    }else{
                        return null;
                    }
                }
            }
        }

        //The path stored in intermediary is backwards, so it has to be flipped.
        //The flipped path is stored in arrayList path.
        for(int f = 1; f <= intermediary.size(); f++){
            path.add(intermediary.get(intermediary.size() - f));
        }

        return path;
    }

    /*
    / INSERT ALL PRIVATE METHODS USED BELOW THIS POINT
    / You are welcome to use as many private methods as necessary, but do not edit the types
    / or method header of any provided public methods.
    */


    //When updating the routeTable, the previous vertex and the edgeTraversed is added to the
    //correct entry (vertex) in routeTable
    private void updateTable(ArrayList<TableRow> routeTable, Vertex previous, Vertex vertex, Edge edgeTraversed) {
        for (TableRow tableRow : routeTable) {
            if (tableRow.getVertex() == vertex) {
                tableRow.setPrevious(previous);
                tableRow.setEdgeTraversed(edgeTraversed);
            }
        }
    }

}


