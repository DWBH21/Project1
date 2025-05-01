
# Arbitrary Precision Arithmetic

## Overview

This project implements an arbitrary precision arithmetic system in Java to overcome the limitations of built-in data types like `int`, `long`, `float`, and `double`. These native types have restricted ranges and precision, making them unsuitable for applications requiring high numerical accuracy or very large integer operations.

## Features

### Java Classes
- AInteger - For large integer arithmetic
- AFloat - For arbitrary precision float arithmetic
- Implementation of arithmetic operations:
  - Addition
  - Subtraction
  - Multiplication
  - Division
- MyInfArith - Class used for demonstrating use of aarithmetic.jar as library. It allows execution of the AInteger and AFloat classes through the command line

## Dependencies 

- **Language:** Java 21
- **Build Tool:** Maven
- **Library Format:** Packaged as a reusable JAR
- **Testing:** Python-3 automated test script

## How to Run

### Implementation Techniques

- **Library as a JAR:**  
  `aarithmetic.jar` contains the core arbitrary-precision arithmetic logic and can be reused in other Java programs by including it in the classpath.

- **Command-Line Interface (CLI) via `MyInfArith.java`:**  
  The `MyInfArith` class accepts four arguments:
  1. Data type: `int` or `float`
  2. Operation: `add`, `sub`, `mul`, or `div`
  3. First operand
  4. Second operand

  The program dynamically dispatches to either integer or floating-point arithmetic at runtime. It uses the `parse(String s)` methods from `AInteger` and `AFloat` classes to convert strings to number objects and handles errors like `NumberFormatException` and `ArithmeticException` (e.g., division by zero).

### Example Usage

#### 1. Build the Project using Maven

```bash
$ mvn clean install
```
- `mvn clean` – Cleans the previous build artifacts.
- `mvn install` – Compiles the source and creates the `aarithmetic.jar` in the `target` directory.

#### 2. Compile the MyInfArith class and link it with the JAR file

```bash
$ javac -cp .:<absolute path of the aarithmetic.jar file> <relative path of MyInfArith.java>
```

#### 3. Run the Program

```bash
$ java -cp .:<absolute path of the aarithmetic.jar file> <relative path of MyInfArith.class> <int/float> <add/sub/mul/div> <operand1> <operand2>
```

## Testing

The project includes a `test.py` script for automated testing. It supports:

### 1. Specific Testing

```bash
/usr/bin/python3 test.py <int/float> <add/sub/mul/div> <operand1> <operand2>
```

### 2. Randomized Testing

```bash
/usr/bin/python3 test.py
Output: 
Enter number of test cases to generate: 
```

#### Each Test Performs:

- **Compilation:**

```bash
javac -cp .:home/anant/Programs/SDF/Project1/target/aarithmetic.jar MyInfArith/MyInfArith.java
```

- **Execution:**

```bash
java -cp .:home/anant/Programs/SDF/Project1/target/aarithmetic.jar:target/classes MyInfArith.MyInfArith <data_type> <operator> <operand1> <operand2>
```

- **Verification:**  
  Python calculates the expected result using `int` or the `Decimal` module (for float), and compares it with Java's output (up to 30 decimal digits for float).

## Author

**Anant Maheshwary**  
Student ID: CS24BTECH11006  
Course: CS1023 - Software Development Fundamentals  
April 2025
