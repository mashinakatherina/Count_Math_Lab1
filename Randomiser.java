import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Randomiser {
    private static final Double EPS = 1e-9;

    private List<Equation> matrix;

    static List<Equation> getRandomMatrix(int n) {
        List<Equation> matrix = new ArrayList<Equation>();
        for (int i = 0; i < n; i++) {
            List<Double> curLine = new ArrayList<Double>();
            for (int j = 0; j <= n; j++)
                curLine.add((new Random().nextInt(21) - 10) + 0.0);
            matrix.add(new Equation(curLine));
        }
        return matrix;
    }


}