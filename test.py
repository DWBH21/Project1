#! /usr/bin/python3
import subprocess
import sys
import random
from decimal import Decimal, getcontext
class MyInfArithPy:

    def __init__(self):
        self.jar_path = "/home/anant/Programs/SDF/Project1/target/aarithmetic.jar"  # Absolute Path to the Jar File
        self.classes_path = "/home/anant/Programs/SDF/Project1/target/aarithmetic.jar:target/classes"
        self.java_test_path = "MyInfArith/MyInfArith.java"                               # Relative Path to the MyINfArith.java file
        self.java_exec_path = "MyInfArith.MyInfArith"

    def compile(self):
        command = [
            'javac',
            '-cp',
            f'.:{self.jar_path}',
            f'{self.java_test_path}',
        ]
        try:
            subprocess.run(command, check=True)
        except subprocess.CalledProcessError as e:
            print(f"Error compiling Java program: {e}")
            print(f"Command was: {e.cmd}")
            print(f"Return code was: {e.returncode}")
            print(f"Standard Error:\n{e.stderr}")
        except :
            print(f"Some unknown error occured.")
        
    def execute(self, data_type, oper, operand1, operand2):
        command = [
            'java',
            '-cp',
            f'.:{self.classes_path}',
            self.java_exec_path,
            data_type,
            oper,
            operand1,
            operand2,
        ]
        try:
            result = subprocess.run(command, capture_output=True, text=True, check=True)
            return result.stdout.strip()  # Removing trailing newline
        except subprocess.CalledProcessError as e:
            print(f"Error running Java program: {e}")
            print(f"Command was: {e.cmd}")
            print(f"Return code was: {e.returncode}")
            print(f"Standard Error:\n{e.stderr}")
            return None
        except :
            print(f"Some unknown error occured.")
            return None

    def generateRandom(self, no_cases=10):
        getcontext().prec = 200
        data_types = ['int' , 'float']
        operators = ['add' , 'sub' , 'mul', 'div']
        operator_symbols = {'add':'+', 'sub':'-' , 'mul':'*', 'div':'/'}
        sign = ['' , '-']
        digits = [str(digit) for digit in range(0 , 9)]
        
        tester = MyInfArithPy()
        tester.compile()
            
        for i in range(no_cases):              
            data_type = random.choice(data_types)         # Choose the data type randomly
            operator = random.choice(operators)           # Choose the arithmetic operator randomly
            
            operand1 = ''.join(random.choice(digits) for _ in range(random.randint(3,50)))  # generate two large integers
            operand2 = ''.join(random.choice(digits) for _ in range(random.randint(3,50)))


            if(data_type == 'float'):                               # Add a decimal at a random index to create two large floating values
                index = random.randint(1, len(operand1) - 1)
                operand1 = operand1[:index] + '.' + operand1[index:]
                index = random.randint(1, len(operand2) - 1)
                operand2 = operand2[:index] + '.' + operand2[index:]

            sign1 = random.choice(sign)     # Randomly choose whether it should be positive or negative
            sign2 = random.choice(sign)
            operand1 = sign1 + operand1     
            operand2 = sign2 + operand2

            print(f'{i+1}. {operand1} {operator_symbols[operator]} {operand2}')
            java_result = tester.execute(data_type, operator, operand1, operand2)       # execute the myinfarith.java code
            
            if(data_type=='float'):
                java_result = trim(java_result).rstrip('0')
            

            if(data_type=='int'):       # typecast to proper data type for arithmetic operations
                d1 = int(operand1)
                d2 = int(operand2)
            if(data_type=='float'):
                d1 = Decimal(operand1)
                d2 = Decimal(operand2)

            if(operator=='add'):
                python_result = (d1 + d2)
            elif(operator=='sub'):
                python_result = (d1 - d2)
            elif(operator=='mul'):
                python_result = (d1 * d2)
            elif(operator=='div'):
                if(data_type=='float'):
                    python_result = (d1 / d2)
                else:
                    result_sign = (sign1=='-') ^ (sign2=='-')
                    python_result = (abs(d1) // abs(d2))
                    if(result_sign):
                        python_result *= -1
                                    
            if(data_type=='int'):
                python_result = str(int(python_result))         # to ignore digits after decimal
            elif(data_type=='float'):
                python_result = trim(format(python_result, 'f')).rstrip('0')  # to supress scientific notation
            
            if(python_result == java_result):
                match = "Correct ✅"
            else:
                match = "Incorrect ❌"

            print(f'Java: {java_result}  Python: {python_result}  {match}')
            print()
                

def trim(float_result, precision=30):           # to format the java result to 30 digits of precision
        # print("Result without trimming: " , float_result)
        length = len(float_result)
        decimalIndex = float_result.index('.')
        return float_result[:decimalIndex + 1] + float_result[decimalIndex+1: min(decimalIndex + 1 + precision , length)]

def main():
    args = sys.argv

    if len(args)==1:
        print("No arguments found. Generating custom test cases")
        no_cases = int(input("Enter number of test cases to generate: "))
        tester = MyInfArithPy()
        tester.compile()
        tester.generateRandom(no_cases)
        
    elif len(args)==5:
        tester = MyInfArithPy()
        tester.compile()
        print(tester.execute(args[1], args[2], args[3], args[4]))
    else:
        print("For verification by a custom operation: Usage: /usr/bin/python3 test.py <int/float> <add/sub/mul/div> <operand1> <operand2>")
        print("For verification by randomly generated operations: Usage: /usr/bin/python3 test.py")
        sys.exit(1)

if __name__ == "__main__":
    main()