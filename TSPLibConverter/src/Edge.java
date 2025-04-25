import java.math.BigDecimal;

public class Edge implements Comparable<Edge> {

private int origin;
private int destination;
private BigDecimal distance;


public Edge(int origin, int destination, BigDecimal distance){
    this.origin = origin;
    this.destination = destination;
    this.distance = distance;
}

public int getOrigin(){
    return this.origin;
}

public int getDestination(){
    return this.destination;
}

public BigDecimal getDistance(){
    return this.distance;
}

    //Allows edges to easily be sorted using java's inbuilt sort() method,
    //with the edges being arranged by their origin node or by their endpoint if they share the same origin
    @Override
    public int compareTo(Edge o) {
    int surfaceLevelCompare = Integer.compare(this.origin, o.getOrigin());
    if (surfaceLevelCompare == 0){
        return(Integer.compare(this.destination, o.getDestination()));
    }
    else{
        return surfaceLevelCompare;
    }
    //return Integer.compare(this.origin, o.getOrigin());
    }

    public String toString(){
        return(this.origin + " - " + this.destination);
    }
}