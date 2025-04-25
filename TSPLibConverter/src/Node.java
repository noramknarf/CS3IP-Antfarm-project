import java.util.ArrayList;

public class Node {
    private int label;
    private ArrayList<Edge> edges;

    public Node(int label){
        this.label = label;
        this.edges = new ArrayList<Edge>();
    };

    public void AddEdge(Edge e){
        edges.add(e);
    }

    public ArrayList<Edge> getEdges(){
        return this.edges;
    }

    public int getID(){
        return this.label;
    }

    public String toString(){
        return(Integer.toString(this.label));
    }
}
