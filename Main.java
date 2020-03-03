import java.util.*;
import java.io.*;

import static ColorUtils.Utils.colorize;

public class Main {
    public static void main(String[] args) {
        FileReader reader;
        Scanner sc = new Scanner(System.in);

        System.out.println(colorize("\n[[CYAN]]This is a program that implements the Gauss method.[[CYAN]]\n"));
        System.out.println(colorize("[[PURPLE]]Input Format:[[PURPLE]]\n\n" +
                "[[BLUE]]n[[BLUE]] [[RESET]]is the number of unknowns and the number of equations.[[RESET]]\n" +
                "Next, in n lines, put n coefficients for unknowns and the free term of each equation separated by space." +
                "\nOr type [[BLUE]]\"random\"[[BLUE]] [[RESET]]if you want the coefficients to be random.[[RESET]]\n\n"));
        System.out.println(colorize("[[PURPLE]]Would you like to import file? [[PURPLE]]\n" +
                "[[RESET]]If [[BLUE]] yes [[BLUE]], [[RESET]]type name of text file in format[[RESET]] [[BLUE]]\"filename\"[[BLUE]] [[RESET]] here.[[RESET]]" +
                "\nIf [[BLUE]]no[[BLUE]], [[RESET]]type[[RESET]] [[BLUE]]\"no\"[[BLUE]]"));

        String file = sc.next();

        if (!file.equals("no")){
            try{
                reader = new FileReader(new File(file));
                sc = new Scanner(reader);
            }
            catch (FileNotFoundException e) {
                System.out.println(colorize("[[RED]]Can not find such file[[RED]]"));
                System.exit(0);
            }
        }

        List<Equation> matrix = new ArrayList<Equation>();
        int n = 0;
        try{n = sc.nextInt();}
        catch(Exception e){
            System.out.println(colorize("[[RED]] Wrong format of input[[RED]]"));
            System.exit(0);
        }
        if (n <= 0 || n > 20){
            System.out.println(colorize("[[RED]] n should be in (0; 20] [[RED]]"));
            System.exit(0);
        }

        sc.nextLine();
        for (int i = 0; i < n; i++){
            ArrayList<Double> list = new ArrayList<Double>();
            String line = sc.nextLine();
            if (i == 0 && line.equals("random")){
                matrix = Randomiser.getRandomMatrix(n);
                break;
            }
            String argss[] = line.split(" ");
            for(String arg : argss){
                try{
                    list.add(Double.parseDouble(arg));
                }
                catch(Exception e){
                    System.out.println(colorize("[[RED]] Wrong format of input [[RED]]"));
                    System.exit(0);
                }
            }
            if (list.size() != n + 1){
                System.out.println(colorize("[[RED]] Wrong format of input [[RED]]"));
                System.exit(0);
            }
            matrix.add(new Equation(list));
        }

        if (matrix.size() != n){
            System.out.println(colorize("[[RED]] Wrong format of input [[RED]]"));
            System.exit(0);
        }

        Matrix systemOfEquations = new Matrix(matrix);
        System.out.println(colorize("[[BLUE]]Your matrix is:[[BLUE]]\n[[WHITE]] "));
        systemOfEquations.print();
        System.out.println();
        System.out.format(colorize("[[BLUE]]det(A)[[BLUE]] = [[WHITE]]%.0f%n[[WHITE]]"), systemOfEquations.getTriangularMatrix());
        System.out.println();
        System.out.println(colorize("[[BLUE]]Triangular matrix :[[BLUE]] [[WHITE]]"));
        systemOfEquations.print();
        System.out.println();
        System.out.println(colorize("[[BLUE]]Solutions :[[BLUE]][[WHITE]]"));
        List<Double> ans = systemOfEquations.getAnswers();
        for (Double x : ans) {
            System.out.format("%.3f ", x);
            System.out.println();
        }
        System.out.println(colorize("[[BLUE]]Residuals :[[BLUE]][[WHITE]]"));
        List<Double> residuals = systemOfEquations.getResidual(ans);
        for (Double x : residuals) {
            if (x!=0){
                System.out.format("%.3f", x*Math.pow(10,15));
                System.out.println(" * 10^(-15)");
            } else {
                System.out.format("%.3f", x);
                System.out.println();}
        }
        System.out.println();
    }
}