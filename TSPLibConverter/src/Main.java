import java.io.IOException;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args)  {
        //arraylist in which to store the parsed data as individual node objects
        ArrayList<Node> nodes = new ArrayList<Node>();
        try{
            nodes = translate_XML();
        }
        catch(IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        //Collating the data in all the nodes into two arrayLists of Edges: one with (logical) duplicates (for use in the dstmatrix), one without them.
        EdgeDataObject edgeData = new EdgeDataObject(nodes);
        ArrayList<Edge> collatedData = edgeData.getNoDuplicates();
        ArrayList<Edge> dataWithDuplicates = edgeData.getWithDuplicates();

            /*
            seeding collatedData with nodes to hold the 0 distance edges (solely for the purposes of the distance matrix.
            If having 0 distance edges causes problems once in MC, it could be solved by adding them to a duplicate of the collatedData instead and using that for the dst matrix
            */
            for (int i = 0; i < nodes.size(); i++){
                Edge dummyEdge = new Edge(i, i, BigDecimal.ZERO);
                collatedData.add(dummyEdge);
                dataWithDuplicates.add(dummyEdge);
            }
            //The following is all debugging print statements and should be cleaned up before any proper submission
        // ------------------------------------------------------------------------------------------------------
            printDebuginfo(collatedData, dataWithDuplicates, nodes);
        //-------------------------------------------------------------------------------------------------------
            collatedData.sort(null);
            dataWithDuplicates.sort(null);
            for(Edge i: dataWithDuplicates){
                System.out.println(i);
            }
            Matrix dstMatrix = formDSTMatrix(dataWithDuplicates, nodes.size());
            methodTest();//comment
            Matrix matrixM = calculateM(dstMatrix.matrixMultiplication(dstMatrix));
    }



    static void methodTest(){
        System.out.println("HELLO WORLD");
    }


    static void printDebuginfo(ArrayList<Edge> collatedData, ArrayList<Edge> dataWithDuplicates, ArrayList<Node> nodes){
        System.out.println("total datapoints = " + collatedData.size()); // Just to verify there are exactly the right number of edges, meaning no duplicates.
        System.out.println("total including duplicates = " + dataWithDuplicates.size());
        System.out.println("CollatedData[0] = " + collatedData.getFirst());
        System.out.println("CollatedData[-1] = " + collatedData.getLast());

        //Checking whether Edges now can be successfully compared (should automatically compare based on the id of the origin node)
        if (collatedData.getLast().compareTo(collatedData.getFirst()) > 0){
            System.out.println("[-1] is greater than [0]");
        }
        else if (collatedData.getLast().compareTo(collatedData.getFirst()) < 0){
            System.out.println("[0] is greater than [-1]");
        }
        else{
            System.out.println("Not a clue, friend");
        }
        System.out.println(nodes.getFirst());
        System.out.println(nodes.getLast());
    }


    static Matrix formDSTMatrix(ArrayList<Edge> edgeDataset, int numberOfNodes){
        BigDecimal[][] dstMatrix = new BigDecimal[numberOfNodes][numberOfNodes];
        System.out.println("dstMatrix row length = " + dstMatrix.length);
        System.out.println("dstMatrix length = " + dstMatrix.length * dstMatrix.length);

        int h = 0;
        for(int j = 0; j < dstMatrix.length; j++){
            for(int i = 0; i < dstMatrix.length; i++){
                System.out.printf("i = %d, j = %d, h = %d\n", i, j, h);
                Edge tempEdgeHolder = edgeDataset.get(h);
                System.out.println(tempEdgeHolder);
                dstMatrix[j][i] = tempEdgeHolder.getDistance();

                h++;

            }
        }
        for(int j = 0; j < dstMatrix.length; j++){
            for(int i = 0; i < dstMatrix.length; i++){
                System.out.printf("%s, ", dstMatrix[j][i]);
                if(i == dstMatrix.length-1){
                    System.out.print("\n");
                }
            }
        }
        return new Matrix(dstMatrix);
    }


    static ArrayList<Node> translate_XML() throws IOException{
        Scanner fileReader = new Scanner(new File("Resources/InputFiles/pr152.xml"));
        String dataToRead;
        Matcher patternMatcher;
        Matcher costMatcher = null;
        Matcher destinationMatcher = null;
        ArrayList<Node> nodes = new ArrayList<Node>();
        int numberOfNodes = 0;
        //Match for the start and end of a vertex
        Pattern vertexStart = Pattern.compile("<vertex>");
        Pattern vertexEnd = Pattern.compile("</vertex>");
        Pattern edgeCost = Pattern.compile("cost=\"(.*?)\""); //Should (in theory) match for the shortest uninterrupted string between 'cost="', and '"'
        Pattern destinationPattern = Pattern.compile(">(.*?)<"); //Likewise, should theoretically match for the destination node

        //This is just a mechanism for more cleanly displaying only one node in the terminal output.
        boolean firstnode = true;
        boolean isnode = false;

        Node currentNode = null;

        while (fileReader.hasNextLine()){
            dataToRead = fileReader.nextLine();

            patternMatcher = vertexStart.matcher(dataToRead);

            //identifies the start of a node and sets the boolean to start 'listening' so to speak
            if(patternMatcher.find()){//if (firstnode && patternMatcher.find()){
                isnode = true;
                currentNode = new Node(numberOfNodes);
                nodes.add(currentNode);
                System.out.println("node begins");
                numberOfNodes ++;
            }

            //identifies the end of the node and sets the boolean to stop listening.
            patternMatcher = vertexEnd.matcher(dataToRead);
            if (patternMatcher.find()){//(firstnode && patternMatcher.find()) {
                isnode = firstnode = false;
                System.out.println("node ends");
            }

            if (isnode ){//& firstnode) {
                System.out.println(dataToRead);
                costMatcher = edgeCost.matcher(dataToRead);
                destinationMatcher = destinationPattern.matcher(dataToRead);

                if (costMatcher != null && destinationMatcher != null) {
                    if (costMatcher.find() && destinationMatcher.find()) {
                        System.out.println("costMatcher's group = " + costMatcher.group(1)); //testing to see if we can output only the matched string - we can, but it only remembers the most recent. Will need to add to a data structure.
                        System.out.println("Destination of that node = " + destinationMatcher.group(1));
                        BigDecimal formattedCost = new BigDecimal(costMatcher.group(1));
                        System.out.println(formattedCost);

                        currentNode.AddEdge(new Edge(numberOfNodes-1, Integer.parseInt(destinationMatcher.group(1)), formattedCost)); //figured out the number of nodes was causing this to
                    }
                    //Safety to make sure no formatting errors result in one edge's values being mixed with another
                    costMatcher = destinationMatcher = null;

                }
            }
        }
        fileReader.close();
        return nodes;
    }

    static Matrix calculateM(Matrix inputMatrix){
        BigDecimal[][] Mdata = new BigDecimal[inputMatrix.getNo_rows()][inputMatrix.getNo_columns()]; //doesn't actually matter whether you use rows or columns as inputmatrix will always be a perfect
        BigDecimal[][] Dcontents = inputMatrix.getContents();

        for (int i = 0; i<inputMatrix.getNo_rows(); i++){
            for(int j = 0; j<inputMatrix.getNo_columns(); j++){
                Mdata[i][j] = (Dcontents[1][j].add(Dcontents[i][1]).subtract(Dcontents[i][j])).divide(BigDecimal.valueOf(2.0));
            }

        }
        return new Matrix(Mdata);
    }

}