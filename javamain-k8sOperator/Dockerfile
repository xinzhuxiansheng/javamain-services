FROM openjdk:11-jre-slim
WORKDIR /opt
COPY target/javamain-k8sOperator-1.0-SNAPSHOT.jar /opt/javamain-k8sOperator-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/opt/javamain-k8sOperator-1.0-SNAPSHOT.jar"]