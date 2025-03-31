import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Matrix {

    private BigDecimal[][] matrix;
    private int no_rows;
    private int no_columns;

    public Matrix(BigDecimal[][] matrix){
        this.matrix = matrix;
        no_rows = matrix.length;
        no_columns = matrix[0].length;
    }

    public void multiply(){

    }
    public BigDecimal[] getRow(int i){
        return matrix[i];
    }

    public BigDecimal[] getColumn(int columnNumber){
        BigDecimal[] column = new BigDecimal[no_rows];
        for (int rowNumber = 0; rowNumber < no_rows; rowNumber++){
            column[rowNumber] = matrix[rowNumber][columnNumber];
        }
        return column;
    }

    public int getNo_rows(){
        return this.no_rows;
    }
    public int getNo_columns(){
        return this.no_columns;
    }

    public BigDecimal[][] getContents(){
        return this.matrix;
    }
//words

    public Matrix matrixMultiplication(Matrix input){
        BigDecimal[][] otherMatrix = input.getContents();


        if (this.no_columns == input.getNo_rows()){
            BigDecimal[][] resultMatrix = new BigDecimal[this.no_columns][input.getNo_rows()];
            for(int row=0; row < this.getNo_rows(); row++){
                for(int column=0; column < input.getNo_columns(); column++){
                    try{
                        resultMatrix[row][column] = dotMultiplyVectors(getRow(row), input.getColumn(column), 32).getSumTotal();
                    }
                    catch(IllegalArgumentException e){
                        throw e;
                    }
                }

                //vectorMultiply(getColumn(i), )
            }
            return new Matrix(resultMatrix);
        }
        else{
            System.out.println("Matrices do not have the correct dimensionality for multiplication");

        }
        return null;
    }

    public static Vector dotMultiplyVectors(BigDecimal[] v1, BigDecimal[] v2, int precision){
        //Unsure if this still needs to be here since we now have dot multiplication as part of Vector
        if(v1.length != v2.length){
            throw new IllegalArgumentException("vectors supplied to dot multiplication are not of compatible length");
        }
        return new Vector(v1).dotMultiplication(new Vector(v2), precision);
    }


    public static Vector addVectors(BigDecimal[] v1, BigDecimal[] v2){
        if(v1.length != v2.length){
            throw new IllegalArgumentException("vectors supplied to addition function are not of compatible length");
        }
        return new Vector(v1).add(new Vector(v2));
    }


    //I may decide to change these so they return a vector instead if I have need of it later
    public BigDecimal[] multiplyRowByValue(int rowNum, BigDecimal factor, int precision){
            BigDecimal[] row = matrix[rowNum];
            MathContext context = new MathContext(precision);
            for(int i=0; i<no_columns; i++){
                row[i] = row[i].multiply(factor, context);
            }
            return row;
    }

    public BigDecimal[] multiplyRowByValue(BigDecimal[] inputRow, BigDecimal factor, int precision){
        MathContext context = new MathContext(precision);
        BigDecimal[] row = new BigDecimal[inputRow.length];
        for(int i=0; i<no_columns; i++){
            row[i] = inputRow[i].multiply(factor, context);
        }
        return row;
    }

    public Matrix gaussianElimination(Matrix inputMatrix){
        BigDecimal[][] m = inputMatrix.getContents();


        inputMatrix.outputContents();
        System.out.println("logging contents");
        System.out.println("length= "+m.length);
        //int col = 0;
        boolean empty = true;
        int colA = 0;
        int alpha_row = 0;
        BigDecimal alpha = BigDecimal.ZERO;
        //step 1 find the leftmost column with a non-zero value (column A)
        for (int column = 0; column < inputMatrix.getNo_columns(); column++){
            for(int row = 0; row < inputMatrix.getNo_rows(); row++){
                if ( m[row][column] != null && (m[row][column].compareTo(BigDecimal.ZERO) != 0)) {
                    colA = column;
                    alpha_row = row;
                    empty = false;
                    alpha = m[row][column];
                    break;
                }
            }
            if(!empty){
                break;
            }
        }
        if(empty){
            return null;
        }
        //step 1.5 - find the row of the greatest value in the column (value a, in row alpha)
        for(int row = 0; row < inputMatrix.getNo_rows(); row++) { //todo - change this to use getcolumn()
            if (m[row][colA].compareTo(alpha) > 0 && m[row][colA].compareTo(BigDecimal.ZERO) != 0) {
                alpha_row = row;
                alpha = m[row][colA];
            }
        } //the ones are not coming from here
        //step 2 - if it is not already, swap the top row with row alpha to make value alpha the topmost value in its column
        if(alpha_row != 0){
            m = inputMatrix.swapRow(0, alpha_row);
            System.out.printf("Swapped %d with 0\n", alpha_row);
        }
        System.out.println(alpha);
        //step 3 - convert value a to 1 by multiplying the topmost row by its inverse
        BigDecimal multiplicand = BigDecimal.ONE.divide(alpha, 400, RoundingMode.HALF_UP);

        System.out.println(alpha.multiply(multiplicand, new MathContext(32))); //
        System.out.println("scale: " + alpha.scale());

        m[0] = multiplyRowByValue(m[0], multiplicand, 32);

        System.out.println(m[0][colA]);
        System.out.println("colA = " + colA); //TODO Ask others for their opinions on how best to handle determining the rounding. I think I will just default to 32dp
        System.out.println("alpha: " + alpha);
        System.out.println("result of multiplication:" + m[0][colA]);
        //step 4 multiply each row below the first by a multiple of the first such that each value in the same column as a ends as zero.
        for (int i = 1; i < m.length; i++){
            //Vector targetRow = new Vector(m[i]);
            BigDecimal inverseOfM_i = m[i][colA].negate();
            //System.out.println("m[i][colA].negate =" + m[i][colA].negate() );
            //System.out.println("zero, colA = " + m[0][colA]);       //Identified possible source of the issue: m[0][colA] is changing despite no operations being done on it.
            BigDecimal[] multipleOfRowAlpha = multiplyRowByValue(m[0], inverseOfM_i, 50);
            //System.out.println("zero colA multiplied by m[i][colA].negate is:" + multipleOfRowAlpha[colA]);
            //BigDecimal temp = m[i][colA];
           //System.out.println(temp);

            //System.out.println("Multiple of alpha = "+ multipleOfRowAlpha[colA]);
            BigDecimal[] temp = m[i];

            m[i] = addVectors(m[i],multipleOfRowAlpha).getContents();
            if(i >= 145){
                System.out.printf("row %d: %s + %s = %s. resulted in: %s\n", i, temp[4], multipleOfRowAlpha[4],temp[4].add(multipleOfRowAlpha[4]), m[i][4]);
            }
          //  System.out.println("m[i][colA] = "+ m[i][colA]);
           // System.out.println("");
            //System.out.printf("result of adding %s to %s is %s\n", multipleOfRowAlpha[colA],temp, m[i][colA]);
        }
        Matrix mAsMatrix = new Matrix(m);
        System.out.println("Slice from rows 0-4");
        if (m.length >= 5){

            mAsMatrix.outputRow(0);
            mAsMatrix.outputRow(1);
            mAsMatrix.outputRow(2);
            mAsMatrix.outputRow(3);
            mAsMatrix.outputRow(4);
            System.out.printf("\n.......\n");
        }
        System.out.println("rowA.len = "+ m[alpha_row].length);
        System.out.println("Outputting the entirety of colA:");
        mAsMatrix.outputColumn(colA); //todo: try outputting the details of the multiplication operations being performed on the weird rows
        System.out.printf("\n.......\n");

        BigDecimal[][] output = new BigDecimal[m.length][m[0].length];
        output[0] = m[0];
        if (m.length > 1){
            BigDecimal[][] inputToNextLayer = new BigDecimal[m.length-1][m[0].length];
            System.out.println(m[0][0]);
            for(int row = 1; row<m.length; row++){
                inputToNextLayer[row-1] = m[row];
                for(BigDecimal i : inputToNextLayer[row-1]){
                    System.out.println(i);
                }

            }
            System.out.println("");
            Matrix remainingRows = gaussianElimination(new Matrix(inputToNextLayer));
            int i = 1;
            for(BigDecimal[] row : remainingRows.getContents()){
                output[i] = row;
                i++;
            }
        }
        return new Matrix(output);
    }

    //used to swap the positions of two rows in the matrix
    BigDecimal[][] swapRow(int row1, int row2){
        BigDecimal[] temp = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = temp;
        return matrix;
    }

    public void outputContents(){
        //String output = "";
        for(int j = 0; j < matrix.length; j++){
            for(int i = 0; i < matrix.length; i++){
                if(i == 0){
                    System.out.printf("./.");
                }
                System.out.printf("%s, ", matrix[j][i]);
                if(i == matrix.length-1){
                    System.out.print("\n");
                }
            }
        }
    }

    public void outputRow(int rowNum){
        if(rowNum < matrix.length){
            for (BigDecimal i : matrix[rowNum]){
                System.out.printf("%s, ", i);
            }
            System.out.printf("\n");
        }
        else{
            System.err.println("Error: Row number supplied to outputRow exceeds matrix size");
        }
    }
    public void outputColumn(int colNum){
        if(colNum < matrix[0].length){
            for (BigDecimal i : getColumn(colNum)){
                System.out.printf("%s, ", i);
            }
            System.out.printf("\n");
        }
        else{
            System.err.println("Error: col number supplied to outputRow exceeds matrix size");
        }
    }
}
/*idea final step is recursive, calling gaussian Elim on a new matrix (or just change param to matrix contents.
Then, gaussian elim returns a new matrix consisting of the first row plus the results of the gaussian elim one level lower

Remember you need to make a vector class to support both results of the vector multiplication.
Just make the vector multiplication return a new vector, taking two others as params, and give vector a getTotal method*/