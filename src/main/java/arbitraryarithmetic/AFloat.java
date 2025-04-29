package arbitraryarithmetic;
public class AFloat {
    // Represents an arbitrarily large float with arbitrarily large precision
    // value is stored internally in scientific notation as num * 10^exp
    // i.e in the form of  x.y * 10^exp where 1<=x<=9 and y has no extra trailing zeroes such that each AFloat has a unique representation
    // The AInteger num stores the integer "x concat y"

    private final AInteger num;
    // an arbitrarily large integer representing the numerical value

    private final int exp;
    // represents the exponent

    private static final int PRECISION = 1000;
    // Maximum default precision used for division

    // Returns the AInteger representing the numerical value of the AFloat
    public AInteger getNum() {
        return this.num;
    }
    
    // Returns the exponent of the AFloat when it is expressed in scientific notation
    public int getExp() {
        return this.exp;
    }
    
    // Default Constructor
    // Initializes the current AFloat object to zero value represented by AInteger = 0 and exp = 1
    public AFloat () {
        this(new AInteger(), 0);
    }
    
    // Copy Constructor
    // Initializes the current AFloat object to be equal to the AFloat passed as argument.
    public AFloat(AFloat float2) {
        this.num = float2.getNum();
        this.exp = float2.getExp();
    }
    
    // Initializes the current AFloat object from its string representation
    // Throws NumberFormatException if String has an Invalid Format
    public AFloat(String s) {
        // Calls the AFloat parse(String s) function that parses a string returns a new AFloat and then invokes the copy constructor with the returned AFloat object
        this(parse(s));
    }
    
    // Initializes the AFloat with the AInteger (representing the digits) and exp when the AFloat is written in scientific notation
    private AFloat(AInteger num, int exp) {
        this.num = num;
        this.exp = exp;
    }
    
    // Parses the String argument and returns an AFloat object
    // Throws NumberFormatException if the String has an Invalid Format
    public static AFloat parse(String s) {
        
        int length = s.length();
        if(length==0)                                // If string has zero length    
            throw new NumberFormatException("Empty String");
        
        //Extracting the sign from the String 
        int tempSign;
        if(s.charAt(0)=='-') {
            tempSign = -1;
            s = s.substring(1, length);
            length--;
        }
        else if(s.charAt(0)=='+') {
            tempSign = 1;
            s = s.substring(1, length);
            length--;
        }
        else
            tempSign = 1;
        
        if(length==0)           // If the string has zero length i.e no digits after the sign
            throw new NumberFormatException("Empty String");
        
        int pointPos = s.indexOf('.');  // Store index of the decimal point
        int exp = 0;
        if(pointPos==0 || pointPos == length - 1)   //If decimal point is at the first or last of the string
        {
            throw new NumberFormatException("Invalid Decimal Position");
        }
        if(pointPos!=-1) //If a decimal point exists, the String does not represent a perfect integer
        {
            for(int i=0; i<s.length(); i++)
            {
                if(i==pointPos)
                    continue;
                if(s.charAt(i)<'0' || s.charAt(i) > '9')        // Checking for any invalid characters
                {
                    throw new NumberFormatException("String " + s + " has an invalid character");
                }
            }
            
            int nonZeroPos = -1;     
            for(int i=0; i<s.length(); i++)
            {
                if(i==pointPos)
                    continue;
                if(s.charAt(i)!='0')
                {
                    nonZeroPos = i;                 // Store the index of the first non zero digit
                    break;
                }   
            }
            if(nonZeroPos == -1)                    // If no non zero digits found, Float is equal to zero
                return new AFloat();                // Invoke the default constructor
            if(nonZeroPos < pointPos)
            {
                exp = pointPos - nonZeroPos - 1;
            }
            else
            {
                exp = pointPos - nonZeroPos;
            }
            int lastNonZeroPos = -1;
            for(int i=length-1; i>=0; i--)
            {
                if(i==pointPos)
                    continue;
                if(s.charAt(i)!='0')               
                {
                    lastNonZeroPos = i;             // Store the index of the last non zero digit
                    break;
                }
            }
            s = s.substring(nonZeroPos, lastNonZeroPos + 1).replace("." , "");      // Extract the digits of the float without any leading or trailing zero digits and remove the decimal point

            // Calls the int[] parseString(String) function of the AInteger class which returns the int array representation of the large integer (the digits of the float)
            // Invokes the AInteger constructor with appropriate sign and int array which returns AInteger "num" of the AFloat being parsed
            // Exponent of the float is calculated by the relative position of the decimal point in the string representation
            // Returns the final AFloat object by passing the appropriate member variables AInteger num and int exp to the constructor.
            return new AFloat(new AInteger(tempSign, AInteger.parseString(s)), exp);
        }
        else            // IF the string does not have a decimal point i.e if the float is a perfect integer
        {
            for(int i=0; i<s.length(); i++)
            {
                if(s.charAt(i)<'0' || s.charAt(i) > '9')       // Checking for any invalid characters
                {
                    throw new NumberFormatException("String " + s + " has an invalid character");
                }
            }
            int nonZeroPos = -1;     
            for(int i=0; i<s.length(); i++)         
            {
                if(i==pointPos)
                    continue;
                if(s.charAt(i)!='0')
                {
                    nonZeroPos = i;                 // Store the index of the first non zero digit
                    break;
                }   
            }
            if(nonZeroPos == -1)                    // If no non zero digits found, Float is equal to zero
                return new AFloat();                // Invoke the default constructor
            int lastNonZeroPos = -1;
            for(int i=length-1; i>=0; i--)
            {
                if(i==pointPos)                     
                    continue;
                if(s.charAt(i)!='0')
                {
                    lastNonZeroPos = i;             // Store the index of the last non zero digit
                    break;
                }
            }
            s = s.substring(nonZeroPos, lastNonZeroPos + 1);        // Extract the digits of the float without any leading or trailing zero digits

            // Calls the int[] parseString(String) function of the AInteger class which returns the int array representation of the large integer (the digits of the float)
            // Invokes the AInteger constructor with appropriate sign and int array which returns AInteger "num" of the AFloat being parsed
            // Exponent of the float is calculated by the =number of digits after the leading zeroes
            // Returns the final AFloat object by passing the appropriate member variables AInteger num and int exp to the constructor.
            return new AFloat(new AInteger(tempSign, AInteger.parseString(s)), length - nonZeroPos - 1);
        } 
    }
    
    // Returns a string representation of the given float
    @Override
    public String toString() {
        AInteger integer = this.getNum();
        int sign = integer.getSign();

        if(sign==0)         // If the AFloat is equal to zero
            return "0.0";
        
        String strInteger = integer.toString().replace("-","");
        int strIntegerLength = strInteger.length();
        String strFloat = sign==-1?"-":"";   // Extract the sign of the AFloat
        int exp = this.exp;
        if(exp < 0)                     // If the AFloat has a negative exponent,
        {
            strFloat += "0.";           // Prepend the float with appropriate number of 0.0000
            for(int i=1; i<-exp; i++)
            {
                strFloat += "0";
            }
            strFloat += strInteger;
        }
        else                          // If the AFloat has a positive exponent,  
        {
            if(exp < strIntegerLength - 1)          // When the exponent is less than the number of digits after the decimal point (when AFloat is written in scientific notation) 
            {
                strFloat += strInteger.substring(0, exp + 1) + "." + strInteger.substring(exp + 1);
            }
            else            // When the exponent is large enough such that extra zeroes need to padded at the end.
            {
                strFloat += strInteger;
                for(int i=1; i<=exp - strIntegerLength + 1; i++)
                {
                    strFloat += "0";
                }
                strFloat += ".0";
            }
        }
        return strFloat;
    }
    
    // Adds the current AFloat object to the AFloat passed as argument
    public AFloat add(AFloat float2) {
        AInteger num1 = this.num;
        AInteger num2 = float2.getNum();
        
        // If either AFloat is zero, return the other AFloat as result 
        if(num1.getSign()==0)
        {
            return new AFloat(float2);
        }
        if(num2.getSign()==0)
        {
            return new AFloat(this);
        }
        
        AFloat result;
        int exp1 = this.exp - num1.noOfDigits();
        int exp2 = float2.getExp() - num2.noOfDigits();
        int resultExp;
        AInteger intResult;
        AInteger ten = new AInteger("10");

        // Method : Find a representation of the two AFloat operands such that both their exponents become equal and then simply add the AIntegers representing their digits
        if(exp1 > exp2) 
        {
            resultExp = exp2;
            for(int i=1; i<=exp1-exp2; i++)        // Multiply the AFloat with the greater exponent with a power of 10 calculated by the difference in exponents
            {
                num1 = num1.multiply(ten);      
            }
            intResult = num2.add(num1);           // Call the add() function of the AInteger class to add the AIntegers
            if(intResult.getSign()==0)
                return new AFloat();
            while(intResult.getNum()[0]%10==0)    // Remove any trailiing zeroes from the AInteger obtained after addition
            {
                intResult = intResult.divide(ten);
                resultExp++;                      // Increment the exponent by one for each trailing zero removed 
            }
            // Call the AFloat constructor with the formatted AInteger and the final exponent when AFloat is represented in scientific notation
            result = new AFloat(intResult, resultExp + intResult.noOfDigits());
        }
        else 
        {
            resultExp = exp1;                     // Multiply the AFloat with the greater exponent with a power of 10 calculated by the difference in exponents
            for(int i=1; i<=exp2-exp1; i++)
            {
                num2 = num2.multiply(ten);
            }
            intResult = num2.add(num1);           // Call the add() function of the AInteger class to add the AIntegers
            if(intResult.getSign()==0)
                return new AFloat();
            while(intResult.getNum()[0]%10==0)    // Remove any trailiing zeroes from the AInteger obtained after addition
            {
                intResult = intResult.divide(ten);
                resultExp++;                     // Increment the exponent by one for each trailing zero removed
            }
            // Invoke the AFloat constructor with the formatted AInteger and the final exponent when AFloat is represented in scientific notation
            result = new AFloat(intResult, resultExp + intResult.noOfDigits());
        }
        return result;
    }

    // Subtracts the the AFloat passed as argument from the current AFloat object. 
    public AFloat subtract(AFloat float2) {
        // Negates the second operand and then calls the AFloat add function
        return this.add(new AFloat(float2.getNum().negate(), float2.getExp()));
    }
    
    // Multiples the current AFloat object with the AFloat passed as argument
    public AFloat multiply(AFloat float2) {
        AInteger num1 = this.num;
        AInteger num2 = float2.getNum();

        // If either AFloat is equal to zero, return zero
        if(num1.getSign()==0 || num2.getSign()==0)
            return new AFloat();

        int exp1 = this.exp - num1.noOfDigits() + 1;
        int exp2 = float2.getExp() - num2.noOfDigits() + 1;
    
        AInteger intResult = num1.multiply(num2);   // multiply the AIntegers representing the digits of the two AFloats
        int resultExp = exp1 + exp2;                // Calculate the exponent of the result
        AInteger ten = new AInteger("10");
        while(intResult.getNum()[0]%10==0)          // Removes any trailing zeroes from the AInteger result
        {
            intResult = intResult.divide(ten);
            resultExp++;                            // Increments the exponent of the result by one for every trailing zero removed 
        }
        // Invoke the AFloat constructor with the formatted AInteger and the final exponent when AFloat is represented in scientific notation
        return new AFloat(intResult, resultExp + intResult.noOfDigits() - 1);   
    }
    
    // Divides the current AFloat object by the AFloat passed as argument
    // Throws ArithmeticException if the second operand is equal to zero
    public AFloat divide(AFloat float2)
    {
        AInteger num1 = this.num;
        AInteger num2 = float2.getNum();
        
        if(num2.getSign()==0)
            throw new ArithmeticException("Division by Zero");
        if(num1.getSign()==0)
            return new AFloat();
        
        
        String strDividend = num1.toString();
        // When both floats are stored in scientific notation, compare stores 1 if the coefficient of the first float is greater than the second, 0 if they are equal and -1 otherwise.
        // Helps determine if the result of the division of the coefficients is less than or greater than 1 and hence how many more digits are required to be calculated for obtaining required precision
        int compare = num1.abs().toString().compareTo(num2.abs().toString()); 
        
        // Exponent of the Result is equal to the difference in exponents and is further decremented by 1 if coefficient of the second float is greater than the coefficient of the first
        int expResult = this.exp - float2.getExp() - (compare<0?1:0);
        int digits1 = num1.noOfDigits();
        int digits2 = num2.noOfDigits();
        for(int i=1; i<=PRECISION + expResult + 1 - digits1 + digits2 - (compare>0?1:0); i++) 
        {
            strDividend += "0";     // pads the AInteger dividend with zeroes in the end, to calculate the more digits of the result upto the required precision
        }
        AInteger intResult = new AInteger(strDividend).divide(num2);    // Calls the divide() function of AInteger class to divide the right-padded dividend by the divisor
        AInteger ten = new AInteger("10");
        while(intResult.getNum()[0]%10==0)      // Removes any trailing zeroes from the AInteger representation of the digits of the AFloat
        {
            intResult = intResult.divide(ten);
        }
        // Invokes the constructor with the formatted AInteger and appropriate exponent.
        return new AFloat(intResult, expResult);    
    }
}
