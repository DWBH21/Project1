#! /usr/bin/python3
import os,sys

class MyInfArithPy:

    def __init__(self):
        self.jar_path = "/home/anant/Programs/SDF/Project1/target/aarithmetic.jar"  # Absolute Path to the Jar File
        self.java_test_path = "MyInfArith/MyInfArith"                               # Relative Path to the MyINfArith.java file

    def compile(self):
        os.system(f'javac -cp .:{self.jar_path} {self.java_test_path}.java')

    def execute(self, data_type, oper, operand1, operand2):
        os.system(f'java -cp .:{self.jar_path} {self.java_test_path} {data_type} {oper} {operand1} {operand2}')

def main():
    args = sys.argv
    if len(args)!=5:
        print("Usage: /usr/bin/python3 test.py <int/float> <add/sub/mul/div> <operand1> <operand2>")
        sys.exit(1)
    tester = MyInfArithPy()
    tester.compile()
    tester.execute(args[1], args[2], args[3], args[4])

if __name__ == "__main__":
    main()