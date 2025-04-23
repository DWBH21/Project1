package arbitraryarithmetic;
public class AInteger {
    // Represents an arbitrarily large integer

    private final int sign;
    // Stores the sign of the integer
    // -1 for negative, 0 for zero and 1 for positive

    private final int num[];
    // An array of integers representing the magnitude of the large integer.
    // It holds the part of the number in base 10^9. The first element of the array holds the Least Significant 9 digits.
    // All elements of this array other than the last store 9 digit decimal numbers.

    private static final int TENPOWERNINE = 1000000000;
    private static final int WORD_LENGTH = 9;

    // Returns the sign of the large integer
    // -1 for negative, 0 for zero and 1 for positive    
    public int getSign() {
        return sign;
    }

    // Returns the array representing the magnitude of the large integer.
    public int[] getNum() {
        return num;
    }

    // Default constructor
    // Initializes the large integer to zero
    AInteger() {
        this.sign = 0;
        this.num = new int[1];   // the num array is a single element array containing zero
        this.num[0] = 0;
    }  

    //Initializes the AInteger from its string representation
    AInteger(String s) {
        // Calls the parse function with the String argument which returns an AInteger and then initializes the current instance using the copy constructor.
        this(parse(s));     
    }
    
    //Copy Constructor
    //Initializes an AInteger object with the same sign and magnitude as the AInteger argument.
    AInteger(AInteger obj) {
        this.num = obj.getNum();
        this.sign = obj.getSign();
    }

    // Initializes an AInteger object with the sign and magnitude array passed as arguments.
    protected AInteger(int sign, int[] num) {
        this.sign = sign;
        this.num = num;
    }

    // Initializes an AInteger object equal to AInteger (whose sign and magnitude is passed as arguments) * 10^(9 * wordOffset)
    // Used in multiplication of AIntegers.
    private AInteger(int sign, int[] num, int wordOffset) {
        this.sign = sign;
        this.num = new int[num.length + wordOffset];
        for(int i=0; i<wordOffset; i++)
        {
            this.num[i] = 0;
        }
        for(int i=0; i<num.length; i++)
        {
            this.num[i + wordOffset] = num[i];
        }
    }

    // Parses the String argument and returns an AInteger object
    // Throws: NumberFormatException for Invalid Strings
    public static AInteger parse(String s) {
        
        int length = s.length();
        if(length==0)    // If the string has zero length, throws NumberFormatException
            throw new NumberFormatException("Empty String");    
        
        // Extracting the sign of the integer
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

        if(length==0)   // If the string has zero length, throws NumberFormatException
            throw new NumberFormatException("Empty String");
        
        // Checking if the String has an Invalid Character.
        for(int i=0; i<length; i++)
        {
            if(s.charAt(i)<'0' || s.charAt(i)>'9') {
                throw new NumberFormatException("String " + s + " has an invalid character");
            }
        }

        // Stripping any leading zeroes from the String
        int index = -1;      
        for(int i=0; i<length; i++)
        {
            if(s.charAt(i)!='0') {
                index = i;      // Stores the index of the first non zero digit.
                break;
            }
        }
        if(index==-1) {         //if no non zero digit found
            return new AInteger();      //The number is equal to zero. Calls the default constructor
        }
        s = s.substring(index, length);     //Truncates the leading zeroes

        // Calls parseString(String) function that returns a magnitude array of the formatted string and then
        // Invokes the constructor with the extracted sign and num array as arguments. 
        return new AInteger(tempSign, parseString(s));
    }
    
    // Returns the String representation of the AInteger object
    @Override
    public String toString() {
        String str;
        // Prepend the String with appropriate sign.
        if(this.sign==0) {
            return "0";
        }
        else if(this.sign==-1) {
            str = "-";
        }
        else {
            str = "";
        }
        int length = num.length;
        str = str + num[length - 1];            // concatenate the string with the most significant digits without any zero padding in front
        for(int i=length-2; i>=0; i--) {
            int word = num[i];
            int digits;
            if(word==0)
                digits = 1;
            else
                digits = (int)Math.floor(Math.log10(word)) + 1;
            for(int j=1; j<=WORD_LENGTH-digits; j++)     //padding any "in between" word with zeroes if it is less than 9 digits
            {
                str = str + "0";
            }
            str = str + "" + word;
        }
        return str;
    }

    // Returns true if the current AInteger is equal to the object passed as arguments, otherwise returns false.
    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj.getClass() != this.getClass()) return false;
        
        AInteger integer2 = (AInteger)obj;
        if(this.sign != integer2.getSign()) return false;
        if(this.num.length != integer2.getNum().length) return false;
        for(int i=0; i<this.num.length; i++)
        {
            if(this.num[i] != integer2.getNum()[i])
                return false;
        }
        return true;
    }

    // Compares the current AInteger object with the AInteger passed as argument.
    // Returns 1: if the current AInteger is greater than the argument, 0 if they are equal and -1 if the current AInteger is less than the argument.
    public int compare(AInteger integer2) {
        int sign1 = this.sign;
        int sign2 = integer2.getSign();
        if(sign1 > sign2)
            return 1;
        else if(sign2 > sign1)
            return -1;
        if(sign1==0)
            return 0;
        int[] mag2 = integer2.getNum();
        int[] mag1 = this.num;
        int length1 = mag1.length;
        int length2 = mag2.length;
        if(length1 > length2)
            return sign1;
        else if(length2 > length1)
            return -sign1;
        for(int i=length1-1; i>=0; i--)
        {
            if(mag1[i]>mag2[i])
                return sign1;
            else if(mag2[i]>mag1[i])
                return -sign1;
        }
        return 0;
    }
    
    // Compares only the magnitude of the current AINtegr obkect with the magnitude of the AInteger passed as argument.
    // Returns 1: if the current AInteger's magnitude is greater than the argument, 0 if they are equal and -1 if the current AInteger's magnitude is less than the argument.
    public int compareMagnitude(AInteger integer2) {
        int[] mag2 = integer2.getNum();
        int[] mag1 = this.num;
        int length1 = mag1.length;
        int length2 = mag2.length;
        if(length1 > length2)
            return 1;
        else if(length2 > length1)
            return -1;
        for(int i=length1-1; i>=0; i--)
        {
            if(mag1[i]>mag2[i])
                return 1;
            else if(mag2[i]>mag1[i])
                return -1;
        }
        return 0;
    }
    
    // Returns an AInteger with the opposite sign but equal magnitude as that of the current AInteger object. 
    public AInteger negate() {
        if(this.sign==0)
            return this;
        return new AInteger(-this.sign, this.num);
    }

    // Returns the absolute value of the current AInteger object.
    public AInteger abs() {
        if(this.sign==-1) {
            return new AInteger(1, this.num);
        }
        else
        {
            return this;
        }

    }

    // Adds the current AInteger object to the AInteger passed as argument
    public AInteger add(AInteger integer2) {
        int sign1 = this.sign;
        int sign2 = integer2.getSign();

        // If either integer is equal to zero, return the other AInteger as result.
        if(sign1==0)
            return new AInteger(integer2);
        if(sign2==0)
            return new AInteger(this);
        
        // If the signs of the AIntegers are different, we have to perform a subtraction of their magnitudes.
        if(sign1*sign2==-1)
        {
            // Finding which AInteger has a greater magnitude and then call the int[] subtractMagnitude(int[], int[]) function with the respective order of magnitudes.
            // Choose the sign of the final result, accordingly
            // And return the AInteger result by invoking the appropriate constructor.
            int compareMagnitude = this.compareMagnitude(integer2);
            if(compareMagnitude==1) {
                return new AInteger(sign1, subtractMagnitude(this.num, integer2.getNum()));
            }
            else if(compareMagnitude==0) {
                return new AInteger();  
            }
            else {
                return new AInteger(-sign1, subtractMagnitude(integer2.getNum(), this.num));
            }
        }
        // If the signs of the AIntegers are the same, we have to perform an addition of their magnitudes.
        // The sign of the result is equal to the sign of the operands.
        // Returns the AInteger result by invoking the appropriate constructor.
        else
        {
            return new AInteger(sign1, addMagnitude(this.num, integer2.getNum()));
        }
    }

    // Subtracts the AInteger passed as argument from the current AInteger object.
    public AInteger subtract(AInteger integer2) {
        // Negates the second AInteger using the AInteger negate() function and then passes it as arguments to AInteger add(AInteger) function
        return this.add(integer2.negate());
    }

    // Multiplies the current AInteger object with the AInteger passed as argument
    public AInteger multiply(AInteger integer2) {
        int sign1 = this.sign;
        int sign2 = integer2.getSign();

        // If either AInteger is equal to zero, return the zero AInteger
        if(sign1==0 || sign2==0)
            return new AInteger();

        // Sign of the result is the product of the signs of the AInteger operands
        int signResult = sign1 * sign2;
        
        AInteger tempResult = new AInteger();       // Create a Zero AInteger object which will act as an accumulator for all the sums
        int[] num1 = this.num;
        int[] num2 = integer2.getNum();

        // The following lines calculate the magnitude of the product using distributive property of multiplication over addition : 

        long multiplyOne;   // Stores the result of multiplication of a 9 digit integer with a 9 digit integer.
        int[] resultOne = new int[2];   // int array that stores the most significant 9 digit integer and the least significant 9 digit integer of multiplyOne 
        for(int i=0; i<num1.length; i++)    // for every block in array of AInteger 1
        {
            for(int j=0; j<num2.length; j++)    // for every block in array of AInteger 2
            {
                multiplyOne = (long)num1[i] * num2[j];   // multiply the two blocks at indices i and j respectively
                resultOne[0] = (int)(multiplyOne % TENPOWERNINE);   // store the Least Significant 9 digits
                resultOne[1] = (int)(multiplyOne / TENPOWERNINE);   // store the Most Significant 9 digits
                tempResult = tempResult.add(new AInteger(1 , resultOne, i+j));   // Multiply resultOne by the respective power of 10 (here 10^(9 * (i+j)) and store the sum in the accumlator
            }
        }

        //removing any leading zero words
        int j;  // will store the index of the first non zero word (9 digit integer)
        int[] tempResultNum = tempResult.getNum();
        int lengthTempResultNum = tempResultNum.length;
        for(j=lengthTempResultNum-1; j>=0; j--) {
            if(tempResultNum[j]!=0)
                break;
        }
        if(j==lengthTempResultNum-1)    //if there were no leading extra zeros 
        {
            if(signResult==1) {
                return tempResult;
            }
            else
                return tempResult.negate();
        }
        else        // if there were extra zeros in the magnitude array.
        {
            int[] resultNum = new int[j+1];
            for(int i=0; i<=j; i++)     // copy the elements into a smaller magnitude array
            {
                resultNum[i] = tempResultNum[i];
            }
            return new AInteger(signResult, resultNum);     // return the AInteger object with the appropriate sign and magnitude array
        }   
    }
 
    // Divides the current AInteger object by the AInteger passed as argument
    // Throws ArithmeticException if the second operand is equal to zero
    public AInteger divide(AInteger divisor) {
        int sign2 = divisor.getSign();
        if(sign2==0)
            throw new ArithmeticException("Division by zero");
        int sign1 = this.sign;
        if(sign1==0)
        {
            return new AInteger();
        }
        if(this.compareMagnitude(divisor)==-1)
        {
            return new AInteger();
        }
        divisor = divisor.abs();
        AInteger dividend = this.abs();
        String strDivisor = divisor.toString();
        String strDividend = dividend.toString() + "0";
    
        int noDigitsDivisor = strDivisor.length();
        AInteger dividendPart = new AInteger(strDividend.substring(0,noDigitsDivisor));
        
        AInteger[] tables = new AInteger[10];
        tables[0] = new AInteger();
        tables[1] = divisor;
        
        int[] numMultiplier = new int[1];
        for(int i=2; i<=9; i++)
        {
            numMultiplier[0] = i;
            tables[i] = divisor.multiply(new AInteger(1, numMultiplier));
        }
        // System.out.println(dividendPart);
        int multiplier = -1;
        String quotient = "";
        for(int i=noDigitsDivisor; i<strDividend.length(); i++)
        {
            //find the greatest multiplier which goes into the division
            int lb = 0;
            int ub = 9;
            int mid;
            while(lb <= ub) 
            {
                mid = (ub + lb)/2;
                int compare = dividendPart.compare(tables[mid]);
                if(compare==0)
                {
                    multiplier = mid;
                    break;
                }
                else if(compare==1)
                {
                    multiplier = mid;
                    lb = mid+1;
                }
                else 
                {
                    ub = mid-1;
                }
            }
            // System.out.println(multiplier);
            quotient = quotient + "" + multiplier; 
            // System.out.println(quotient);
            AInteger remainder = dividendPart.subtract(tables[multiplier]);
            dividendPart = new AInteger(remainder.toString() + "" + strDividend.charAt(i));
        }
        if(sign1 * sign2 == 1)
            return new AInteger(quotient);
        else
            return new AInteger(quotient).negate();
    }

    // Returns the number of digits in the number
    // Used by the divide function in both AInteger and AFloat class
    protected int noOfDigits() {
        int[] num = this.getNum();
        return (num.length-1) * WORD_LENGTH + (int)Math.floor(Math.log10(num[num.length-1])) + 1;
    }
    
    // Takes as argument the string formatted by the AInteger parse(String) function and returns the magnitude array of the AInteger
    // Precondition -> String argument has no sign, no leading zeroes and no invalid character  
    protected static int[] parseString(String s) {
        int strlength = s.length();
        int arrLength = (int)Math.ceil(strlength/9.0);  // Calculate length of the magnitude array required for storing the AInteger denoted by the String
        int[] arr = new int[arrLength];
        int endIndex = strlength;
        int startIndex = endIndex - WORD_LENGTH;
        for(int i=0; i<arrLength-1; i++)    // Loop to assign first arrLength-1 values to the int array
        {
            arr[i] = Integer.parseInt(s.substring(startIndex, endIndex));   // Explicitly type cast a 9 character substring of the string into an integer 
            startIndex-=WORD_LENGTH;     // Update start and end indices for the next substring
            endIndex-=WORD_LENGTH;
        }
        arr[arrLength-1] = Integer.parseInt(s.substring(0, endIndex));  // Store the most significant digits (less than or equal to 9) in the last element of the int array
        // for (int i : arr) {
        //     System.out.println(i);
        // }
        return arr;
    }  
    
    // Adds the magnitudes of two operands represented by their int arrays and returns the int array representation of the result.
    // Called by the Integer add(Integer) function when signs of the two AIntegers to be added are the same.  
    private int[] addMagnitude(int[] num1, int[] num2) {
        // Initialize carry and sum to zero for the first add operation
        int carry = 0;
        int sum = 0;
        int length1 = num1.length;
        int length2 = num2.length;
        if(length1 > length2)
        {
            int tempResult[] = new int[length1];    // Create a new int array with length equal to the length of the array of the number with greater magnitude
            for(int i=0; i<length2; i++)            // Loop over each 9-digit integer element of the magnitude array from the Least Significant to the Most Significant Digits
            {
                sum = num1[i] + num2[i] + carry;    // Add the corresponding elements of both arrays
                carry = sum / TENPOWERNINE;         // Update sum and carry accordingly for the next more significant word.
                sum %= TENPOWERNINE;
                tempResult[i] = sum;
            }
            for(int i=length2; i<length1; i++)      // This loop executes when the smaller magnitude array has less elements than the larger magnitude array
            {
                sum = num1[i] + carry;
                carry = sum / TENPOWERNINE;
                sum %= TENPOWERNINE;
                tempResult[i] = sum;
            }
            if(carry==0)                            // If the final carry is equal to zero, return the tempResult array so obtained.
                return tempResult;
            else                                    // If carry is not zero, it means that the magnitude array of the result is one word greater than the original operand array
            {
                int result[] = new int[length1 + 1];
                for(int i=0; i<length1; i++)        // Copy all the elements into a bigger array
                {
                    result[i] = tempResult[i];
                }
                result[length1] = carry;            // Set the most significant word as the carry
                return result;                      // Return the new array
            }
        }
        else
        {
            int tempResult[] = new int[length2];    // Create a new int array with length equal to the length of the array of the number with greater magnitude
            for(int i=0; i<length1; i++)            // Loop over each 9-digit integer element of the magnitude array from the Least Significant to the Most Significant Digits
            {
                sum = num1[i] + num2[i] + carry;    // Add the corresponding elements of both arrays
                carry = sum / TENPOWERNINE;         // Update the sum and carry accordingly for the next more significant word.
                sum %= TENPOWERNINE;
                tempResult[i] = sum;
            }
            for(int i=length1; i<length2; i++)      // This loop executes when the smaller magnitude array has less elements than the larger magnitude array
            {
                sum = num2[i] + carry;
                carry = sum / TENPOWERNINE;
                sum %= TENPOWERNINE;
                tempResult[i] = sum;
            }
            if(carry==0)                            // If the final carry is equal to zero, return the tempResult array so obtained.
                return tempResult;
            else                                    // If carry is not zero, it means that the magnitude array of the result is one word greater than the original operand array
            {
                int result[] = new int[length2 + 1];
                for(int i=0; i<length2; i++)        // Copy all the elements into a bigger array
                {
                    result[i] = tempResult[i];
                }
                result[length2] = carry;            // Set the most significant word as the carry
                return result;                      // Return the new array
            }
        }
    }

    // Subtracts the magnitudes of two operands represented by their int arrays and returns the int array representation of the result.
    // Called by the Integer add(Integer) function when signs of the two AIntegers to be added are different.
    // Precondition - num1 has greater magnitude than num2
    private int[] subtractMagnitude(int[] num1, int[] num2) {   
        int length1 = num1.length;
        int length2 = num2.length;
        int[] tempResult = new int[length1];    // create an array with a length equal to the length of the rarray of greater magnitude
        int borrow = 0;                         // initialize borrow to 0 for first subtraction operation
        for(int i=0; i<length2; i++)            // Loop over each 9-digit integer element of the magnitude array from the Least Significant to the Most Significant Digits
        {
            int word1 = num1[i];
            int word2 = num2[i] + borrow;       // Add borrow to the number which is being subtracted
            if(word2 <= word1)                  // If the number to be subtracted is less than or equal to, then no borrow is required.
            {
                tempResult[i] = word1 - word2;
                borrow = 0;
            }      
            else                               // If the number to be subtracted is more than the number being subtracted from, borrow 1 from the next more significant word
            {
                borrow = 1;
                tempResult[i] = (word1 - word2) + TENPOWERNINE;
            }
        }
        for(int i=length2; i<length1; i++)     // This loop will execute when the length of the array 1 is strictly greater than the length of array 2
        {
            int word1 = num1[i];
            int word2 = borrow;
            if(word2 <= word1) 
            {
                tempResult[i] = word1 - word2;
                borrow = 0;
            }
            else 
            {
                borrow = 1;
                tempResult[i] = (word1 - word2) + TENPOWERNINE;
            }
        }

        //removing any leading zero words from the tempResult i.e if tempResult has less no of words than num1 after subtraction
        int j;
        for(j=length1-1; j>=0; j--) {
            if(tempResult[j]!=0)
                break;
        }
        if(j==length1 - 1)          // If no zero words, then return tempResult array
            return tempResult;

        int[] result = new int[j+1];   // If there are some leading zero words, create a smaller array to store the result
        for(int i=0; i<=j; i++)         // Copy elements into the smaller array
        {
            result[i] = tempResult[i];     
        }
        return result;                  // Return the final result
    }

} //end of class AInteger