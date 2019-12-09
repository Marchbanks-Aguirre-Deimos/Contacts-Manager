import java.util.Scanner;

public class Input extends Exception{

    public static void main(String[] args) {
        Input input = new Input();

        input.yesNo();
        input.getInt(1,10);
        input.getDouble(1,10);

    }

    private Scanner sc = new Scanner(System.in);

    public String getString() {
        return sc.nextLine();
    }

    public Boolean yesNo() {
        System.out.println("[y/n]");
        String userAnswer = sc.nextLine();
        if (userAnswer.equalsIgnoreCase("y") || userAnswer.equalsIgnoreCase("yes")) {
            return true;
        } else {
            return false;
        }
    }

    public int getInt(int min, int max) {
        int inputNum = Integer.valueOf(getString());
        try {
            if (inputNum < min)
                return getInt(min, max);
            } catch(Exception e){
                System.out.println("Enter a number");
                getInt(min, max);
            }
            System.out.println("You're number is " + inputNum);
            return inputNum;

        }



    public int getInt(){
        System.out.println("Give me a number");
        return Integer.parseInt(getString());
    }

    public double getDouble(double min, double max) {
        System.out.println("Enter a number.");
        double inputNum = Double.valueOf(getString());
        try {
            if (inputNum < min)
                return getDouble(min, max);
            } catch (Exception e) {
                System.out.println("Enter a number");
                getDouble(min, max);
            }
            System.out.println("You're number is " + inputNum);
            return inputNum;
        }

    public double getDouble(){
        return sc.nextDouble();
    }




}

