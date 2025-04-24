package MyInfArith;
import arbitraryarithmetic.AInteger;
import arbitraryarithmetic.AFloat;
public class MyInfArith  {
    public static void main(String[] args) {
        if(args.length != 4)
        {
            System.out.println("Usage: java MyInfArith <int/float> <add/sub/mul/div> <operand1> <operand2>");
            return;  
        }

        String type = args[0];
        String oper = args[1];
        String operand1 = args[2];
        String operand2 = args[3];

        try
        {
            if (type.equalsIgnoreCase("int")) {
                AInteger num1,num2;
                try 
                {
                    num1 = AInteger.parse(operand1);
                    num2 = AInteger.parse(operand2);
                }
                catch(NumberFormatException e) 
                {
                    System.out.println("Error: Invalid Operand. Please enter a correct number");
                    return;
                }
                AInteger result;

                switch (oper.toLowerCase()) {
                    case "add":
                        result = num1.add(num2);
                        break;

                    case "sub":
                        result = num1.subtract(num2);
                        break;

                    case "mul":
                        result = num1.multiply(num2);
                        break;

                    case "div":
                        try
                        {
                            result = num1.divide(num2);
                        }
                        catch(ArithmeticException e)
                        {
                            System.out.println("Error: Division by zero");
                            return;
                        }
                        break;

                    default:
                        System.out.println("Error: Invalid integer operation. Use add, sub, mul, or div.");
                    return;
                }
                System.out.println(result.toString());
            }
            else if (type.equalsIgnoreCase("float")) {
                AFloat num1, num2;
                try 
                {
                    num1 = AFloat.parse(operand1);
                    num2 = AFloat.parse(operand2);
                }
                catch(NumberFormatException e)
                {
                    System.out.println("Error: Invalid Operand. Please enter a correct number");
                    return;
                }
                AFloat result;

                switch (oper.toLowerCase()) {
                    case "add":
                        result = num1.add(num2);
                        break;
                    case "sub":
                        result = num1.subtract(num2);
                        break;
                    case "mul":
                        result = num1.multiply(num2);
                        break;
                    case "div":
                        try
                        {
                            result = num1.divide(num2);
                        } 
                        catch(ArithmeticException e)
                        {
                            System.out.println("Error: Division by zero");
                            return;
                        }
                        break;
                    default:
                        System.out.println("Error: Invalid float operation. Use add, sub, mul, or div.");
                        return;
                }
                System.out.println(result);
            } 
            else
            {
                System.out.println("Error: Invalid type. Use int or float.");
                return;
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format. Enter valid numeric values.");
        }
    }
}
