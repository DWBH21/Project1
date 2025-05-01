# Downloadinig public base image for maven from docker hub in the build stage which creates the build image
FROM maven:3.9.6-eclipse-temurin-21 AS build    

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

# copying readme and report
COPY report.pdf /app/report.pdf
COPY README.md /app/README.md

# Compiling the MyInfArith class
RUN javac -cp .:target/aarithmetic.jar MyInfArith/MyInfArith.java

# Setting default entrypoint for execution of MyInfArith from the command line
# Writing a shell-script inline to print usage instructions conditioned on the number of arguments passed.
RUN echo '#!/bin/sh\n\
if [ $# -eq 0 ]; then\n\
    cat << "EOF"\n\
Usage:\n\
  To run MyInfArith: docker run <image_name> <int/float> <add/sub/mul/div> <operand1> <operand2>\n\
  To run Python tests (interactive): docker run -it --entrypoint python3 <image_name> /app/test.py\n\
  For custom Python tests: docker run --entrypoint python3 <image_name> /app/test.py <int/float> <add/sub/mul/div> <operand1> <operand2>\n\
EOF\n\
else\n\
    exec java -cp .:target/aarithmetic.jar MyInfArith.MyInfArith "$@"\n\
fi' > /app/entrypoint.sh && chmod +x /app/entrypoint.sh

# Set as entrypoint
ENTRYPOINT ["/app/entrypoint.sh"]
CMD []

# The above script is added because:
# Earlier, ENTRYPOINT ["java", "-cp", ".:target/aarithmetic.jar", "MyInfArith.MyInfArith"] 
# This original entry point was creating problems as even when docker run test.py was being run, the default entry point was attempting to execute the MyInfArith.java file.
# This is was creating an error as the MyInfArith.java thought that it was getting invalid number of arguments.