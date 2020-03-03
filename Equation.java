import java.util.ArrayList;
import java.util.List;

import static ColorUtils.Utils.colorize;

public class Equation {
    private List<Double> coefficient;

    public Double getItem(int i) {
        if (i < 0 || i >= coefficient.size()) {
            System.out.format(colorize("[[RED]]Index %d is out of bounds [0; %d). Returning null.[[RED]][[WHITE]]"), i, coefficient.size());
            return null;
        }
        return coefficient.get(i);
    }

    public int getLength() {
        return coefficient.size();
    }

    Equation(List<Double> coefficient) {
        this.coefficient = coefficient;
    }

    Equation summarize(Equation l) {
        assert l.getLength() == this.getLength();
        List<Double> coef = new ArrayList<Double>();
        for (int i = 0; i < this.getLength(); i++) {
            coef.add(this.getItem(i) + l.getItem(i));
        }
        return new Equation(coef);
    }

    Equation multiply(double x) {
        List<Double> k = new ArrayList<Double>();
        for (Double value : coefficient) {
            k.add(value * x);
        }
        return new Equation(k);
    }

    void formatOutput() {
        int n = this.getLength();
        for (int i = 0; i < n; i++) {

            if (i + 1 == n) {
                System.out.print("| ");
            }
            System.out.format("%7.3f ", this.getItem(i));
        }
        System.out.println();
    }

}
