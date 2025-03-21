import java.util.ArrayList;

public class EdgeDataObject {
    private ArrayList<Edge> dataWithDuplicates;
    private ArrayList<Edge> dataWithoutDuplicates;

    public EdgeDataObject(ArrayList<Node> nodes) {

        this.dataWithoutDuplicates = new ArrayList<Edge>();
        this.dataWithDuplicates = new ArrayList<Edge>(); // for use in the dstMatrix

        String concatKey;
        String reverseConcat;
        for (int i = 0; i < nodes.size(); i++) {
            for (Edge entry : nodes.get(i).getEdges()) {
                boolean logicalDuplicate = false;
                boolean exactDuplicate = false;
                for (Edge j : dataWithoutDuplicates) {
                    if ((entry.getOrigin() == j.getOrigin() && entry.getDestination() == j.getDestination())) {
                        exactDuplicate = logicalDuplicate = true;
                        break;
                    } else if ((entry.getDestination() == j.getOrigin() && entry.getOrigin() == j.getDestination())) {
                        logicalDuplicate = true;
                        break;
                        // System.out.println("Duplicate detected");
                    }
                }
                if (!exactDuplicate) {
                    dataWithDuplicates.add(entry);
                }
                if (!logicalDuplicate) {
                    dataWithoutDuplicates.add(entry);
                }
            }
        }
    }

    public ArrayList<Edge> getNoDuplicates(){
        System.out.println("Retrieving datawithduplicates: "+ dataWithDuplicates.getFirst());
        return this.dataWithoutDuplicates;

    }
    public ArrayList<Edge> getWithDuplicates(){
        return this.dataWithDuplicates;
    }
                /* This section exists only for debugging and should be cleaned up later.
                if(i < dest) {
                    int dest = entry.getDestination();
                    concatKey = MessageFormat.format("{0}.{1}", i, dest); //I think once we get to 50%, it will be all duplicates but I'm not sure.
                    reverseConcat = MessageFormat.format("{1}.{0}", i, dest);
                }
                else{
                    concatKey = MessageFormat.format("{0}.{1}", dest, i);
                    reverseConcat = MessageFormat.format("{1}.{0}", i, dest);
                }

                if((i <= 1 || i >= 150 ) && (dest > 150 || dest < 1)) {
                    System.out.println("Dest: " + dest + " ,i: " + i);
                    System.out.println("Edge origin " +entry.getOrigin() +" Edge destination: "+ entry.getDestination());
                    System.out.println("already present = " + logicalDuplicate);
                    //System.out.println("concatKey = " + concatKey);
                }
            }
                for (Edge item: collatedData){
                   System.out.println(MessageFormat.format("edge {0}-{1}, weighting: {2}", item.getOrigin(), item.getDestination(), item.getDistance()));
                }*/


}
