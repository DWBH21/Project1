# Downloadinig public base image for maven from docker hub in the build stage which creates the build image
FROM maven:3.8.7-eclipse-temurin-21 AS build    

# Setting working directory
WORKDIR /app

# Copying the pom.xml file (descriptor for Maven)
COPY pom.xml .

# Downloading dependencies
RUN mvn dependency:go-offline

# Copying the rest of the source code
COPY src ./src
COPY MyInfArith/ ./MyInfArith/
COPY test.py .  

# Building the project and create the JAR file
RUN mvn clean install

# Creating the run time image -> Downloading public base image for jdk 21 from docker hub
FROM eclipse-temurin:21-jdk-jammy 

# Setting working directory in runtime container
WORKDIR /app

# Installling Python for running the test script
RUN apt-get update && apt-get install -y python3 && apt-get clean

# Copying JAR file, MyInfArith.java and python test script from the build stage
COPY --from=build /app/target/aarithmetic.jar ./target/aarithmetic.jar
COPY --from=build /app/MyInfArith/ ./MyInfArith/
COPY --from=build /app/test.py . 

# Compiling the MyInfArith class
RUN javac -cp .:target/aarithmetic.jar MyInfArith/MyInfArith.java

# Setting default entrypoint for execution of MyInfArith from the command line
ENTRYPOINT ["java", "-cp", ".:target/aarithmetic.jar", "MyInfArith.MyInfArith"]
