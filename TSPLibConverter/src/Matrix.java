import java.math.BigDecimal;

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

        if (no_columns == input.getNo_rows()){
            BigDecimal[][] resultMatrix = new BigDecimal[no_columns][input.getNo_rows()];
            for(int row=0; row < no_rows; row++){
                for(int column=0; column < input.getNo_columns(); column++){
                    try{
                        resultMatrix[row][column] = dotMultiplyVectors(getRow(row), input.getColumn(column));
                    }
                    catch(IllegalArgumentException e){
                        throw e;
                    }
                }
                return new Matrix(resultMatrix);
                //vectorMultiply(getColumn(i), )
            }
        }
        else{
            System.out.println("Matrices do not have the correct dimensionality for multiplication");

        }
        return null;
    }

    public static BigDecimal dotMultiplyVectors(BigDecimal[] v1, BigDecimal[] v2){
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
}
