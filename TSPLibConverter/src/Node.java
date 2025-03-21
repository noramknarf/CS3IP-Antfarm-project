import java.util.ArrayList;

public class Node {
    private int label;
    private ArrayList<Edge> edges;

    public Node(int label){
    /*Do we actually need this to be traversable?
    * if we're trying to translate this into something usable in MC, we need to first get the data imported over, however, we don't know how to even format it.
    * We know we CAN do it now, but it's just down to how to represent it in such a way that would be usable.
    *
    * --No, we don't
    * */
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
