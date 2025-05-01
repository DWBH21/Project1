
# Arbitrary Precision Arithmetic

## Overview

This project implements an arbitrary precision arithmetic system in Java to overcome the limitations of built-in data types like `int`, `long`, `float`, and `double`. These native types have restricted ranges and precision, making them unsuitable for applications requiring high numerical accuracy or very large integer operations.

## Features

### Java Classes
- `AInteger` - For large integer arithmetic
- `AFloat` - For arbitrary precision float arithmetic
- Implementation of arithmetic operations:
  - Addition
  - Subtraction
  - Multiplication
  - Division
- `MyInfArith` - Class used for demonstrating use of aarithmetic.jar as library. It allows execution of the AInteger and AFloat classes through the command line

## Dependencies 

- **Language:** Java 21
- **Build Tool:** Apache Maven 3.8.7
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
```
Output: 
```bash
No arguments found. Generating custom test cases
Enter number of test cases to generate: 5
1. -521201065.054138 * 620630855288177.7375262484013817
Java: -323473462781658831951562.4986088855474645024746  Python: -323473462781658831951562.4986088855474645024746  Correct ✅

2. -3784232701732567.1384814303 + -75411387.082507610303687
Java: -3784232777143954.220989040603687  Python: -3784232777143954.220989040603687  Correct ✅

3. 226683331152.05418541558854525310087381377548 / 80844845767.57634254
Java: 2.80393052890166893516831828541  Python: 2.80393052890166893516831828541  Correct ✅

4. 20462715076325413850025736353241 - -47072024605867114
Java: 20462715076325460922050342220355  Python: 20462715076325460922050342220355  Correct ✅

5. 21536675153185832235511362548500761874361 / -57015111710288053304423667124362
Java: -377736261  Python: -377736261  Correct ✅

5 out of 5 test cases passed
```

- **Compilation:** (done only once at the starting) 

```bash
javac -cp .:home/anant/Programs/SDF/Project1/target/aarithmetic.jar MyInfArith/MyInfArith.java
```
 **Random Test Case Generation**
Test cases are randomized over the following:
    - Data types: int , float
    - Operators: add , sum, multiply, divide
    - Operands: Large random numbers, with random signs and decimal points (for floats).

For Each Test Case:
- **Execution:**

```bash
java -cp .:home/anant/Programs/SDF/Project1/target/aarithmetic.jar:target/classes MyInfArith.MyInfArith <data_type> <operator> <operand1> <operand2>
```

- **Verification:**  
  Python calculates the expected result using `int` or the `Decimal` module (for float) and compares it with Java's output (up to 30 decimal digits for float).
  And prints total number of test cases passed.

## Containerization with Docker

### Build the Docker Image
  ```bash
  docker build -t <image_name> .
  ```
The project uses a multi-stage Docker build. 
- Stage 1 uses a Maven image to build the JAR.
- Stage 2 uses a JDK 21 image with Python for running tests.

### Run Java CLI in a Container

```bash
docker run <image_name> int add 123 456
Output: 579
```
### Run Python Test Script in a Container

**Randomized Testing:**

```bash
docker run -it --entrypoint python3 <image_name> /app/test.py
```

**Specific Test:**

```bash
docker run --entrypoint python3 <image_name> /app/test.py float div 10.5 2.5
```

### CLI Help

If no arguments are passed, the container displays usage instructions:
```bash
docker run <image_name>
```

Other helpful Docker commands:

Display all images and their information 
```bash
docker images
```

Display all containers (active and closed) 
```bash
docker ps -a
```
Run the container to start an interactive shell (by overriding the entrypoint)
```bash
docker run -it --entrypoint "/bin/bash" my-arithmetic-app
```

Remove all closed containers 
```bash
docker container prune
```
Remove an image with its image_name
```bash
docker rmi <image_name>
```

## Author

**Anant Maheshwary**  
Student ID: CS24BTECH11006  
Course: CS1023 - Software Development Fundamentals  
April 2025
