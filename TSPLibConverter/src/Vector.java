import java.math.BigDecimal;
import java.math.MathContext;

public class Vector {
    private BigDecimal[] contents;

    public Vector(BigDecimal[] contents){
        this.contents = contents;
    }

    public BigDecimal[] getContents(){
        return this.contents;
    }
    public BigDecimal getSumTotal(){
        BigDecimal total = BigDecimal.ZERO;
        for(BigDecimal item: this.contents){
            total = total.add(item);
        }
        return total;
    }

    public Vector dotMultiplication(Vector otherVector, int precision){
        BigDecimal[] v1 = this.contents;
        BigDecimal[] v2 = otherVector.getContents();
        if(v1.length != v2.length){
            throw new IllegalArgumentException("vectors supplied to dot multiplication are not of compatible length");
        }
        MathContext context = new MathContext(precision);
        BigDecimal[] v3 = new BigDecimal[v1.length];

        for (int i = 0; i<v1.length; i++){
            v3[i] = v1[i].multiply(v2[i], context);
        }
        return new Vector(v3);
    }
    public Vector dotMultiplication(BigDecimal multiplicand, int precision){
        BigDecimal[] v1 = this.contents;
        BigDecimal[] v2 = new BigDecimal[v1.length];
        MathContext context = new MathContext(precision);

        for (int i = 0; i<v1.length; i++){
            v2[i] = v1[i].multiply(multiplicand, context);
        }
        return new Vector(v2);
    }
}
