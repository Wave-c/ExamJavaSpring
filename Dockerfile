FROM maven:latest AS stage1
WORKDIR /ExamJavaSpring
COPY pom.xml /ExamJavaSpring
RUN mvn dependency:resolve
COPY . /ExamJavaSpring
RUN mvn clean
RUN mvn clean install
RUN mvn package -DskipTests

FROM openjdk:17 as final
COPY --from=stage1 /ExamJavaSpring/target/*.jar ExamJavaSpring.jar
EXPOSE 8080
CMD ["java", "-jar", "ExamJavaSpring.jar"]