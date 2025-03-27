import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Matrix {
    enum OutputMode{
        TOTAL,
        INDIVIDUAL
    }

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
                        resultMatrix[row][column] = dotMultiplyVectors(getRow(row), input.getColumn(column));
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

    public static BigDecimal dotMultiplyVectors(BigDecimal[] v1, BigDecimal[] v2, OutputMode outputMode){
        //todo finish this and implement it as part of the matrix multiplication.
        if(v1.length != v2.length){
            throw new IllegalArgumentException("vectors supplied to dot multiplication are not of compatible length");
        }
        BigDecimal total = new BigDecimal(0);
        for(int i=0; i<v1.length; i++){
            total = total.add(v1[i].multiply(v2[i]));
        }
        return total;
    }

    public BigDecimal[] multiplyRowByValue(int rowNum, BigDecimal factor, int precision){
            BigDecimal[] row = matrix[rowNum];
            MathContext context = new MathContext(precision);
            for(int i=0; i<no_columns; i++){
                row[i] = row[i].multiply(factor, context);
            }
            return row;
    }

    public Matrix gaussianElimination(Matrix inputMatrix){
        BigDecimal[][] m = inputMatrix.getContents();


        //int col = 0;
        BigDecimal scanner = BigDecimal.ZERO;
        Boolean empty = false;
        int colA = 0;
        int alpha_row = 0;
        BigDecimal alpha = BigDecimal.ZERO;
        //step 1 find the leftmost column with a non-zero value (column A)
        for (int column = 0; column < inputMatrix.getNo_columns(); column++){
            for(int row = 0; row < inputMatrix.getNo_rows(); row++){
                if (m[row][column] != BigDecimal.ZERO) {
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
        for(int row = 0; row < inputMatrix.getNo_rows(); row++) {
            if (m[row][colA].compareTo(alpha) > 0) {
                alpha_row = row;
                alpha = m[row][colA];
            }
        }
        //step 2 - if it is not already, swap the top row with row alpha to make value a the topmost value in its column
        if(alpha_row != 0){
            m = inputMatrix.swapRow(0, alpha_row);
            System.out.printf("Swapped %d with 0\n", alpha_row);
        }
        //step 3 - convert value a to 1 by multiplying the topmost row by its inverse
        BigDecimal multiplicand = BigDecimal.ONE.divide(alpha, 400, RoundingMode.HALF_UP);
        System.out.println(alpha.multiply(multiplicand, new MathContext(32))); //this should result in an output of 1. find out why it isn't doing that.
        System.out.println("scale: " + alpha.scale());
        m[0] = multiplyRowByValue(0,multiplicand, 32);
        System.out.println(m[0][colA]);
        System.out.println(colA); //TODO Ask others for their opinions on how best to handle determining the rounding. I think I will just default to 32dp
        System.out.println("alpha: " + alpha);
        System.out.println("result of multiplication:" + m[0][colA]);
       /* for(BigDecimal x:m[0]){
            System.out.println(x);
        }*/

        //step

        return null;
    }

    //used to swap the positions of two rows in the matrix
    BigDecimal[][] swapRow(int row1, int row2){
        BigDecimal[] temp = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = temp;
        return matrix;
    }
}
/*idea final step is recursive, calling gaussian Elim on a new matrix (or just change param to matrix contents.
Then, gaussian elim returns a new matrix consisting of the first row plus the results of the gaussian elim one level lower

Remember you need to make a vector class to support both results of the vector multiplication.
Just make the vector multiplication return a new vector, taking two others as params, and give vector a getTotal method*/