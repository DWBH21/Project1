
# Arbitrary Precision Arithmetic

## Overview

This project implements an arbitrary precision arithmetic system in Java to overcome the limitations of built-in data types like `int`, `long`, `float`, and `double`. These native types have restricted ranges and precision, making them unsuitable for applications requiring high numerical accuracy or very large integer operations.

## Features

### Java Classes
- AInteger
- 
- Implementation of core arithmetic operations:
  - Addition
  - Subtraction
  - Multiplication
  - Division

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

#### 2. Compile CLI Interface

```bash
$ javac -cp .:home/anant/Programs/SDF/Project1/target/aarithmetic.jar MyInfArith/MyInfArith.java
```

#### 3. Run the Program

```bash
$ java -cp .:home/anant/Programs/SDF/Project1/target/aarithmetic.jar:target/classes MyInfArith.MyInfArith int add 4 5
Output: 9
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
