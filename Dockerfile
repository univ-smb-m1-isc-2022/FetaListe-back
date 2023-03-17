FROM openjdk:17-jdk-slim

COPY ./target/fetaliste-back-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

CMD ["sh","-c","java -XX:InitialRAMPercentage=50 -XX:MaxRAMPercentage=70  -XshowSettings $JAVA_OPTS -jar fetaliste-back-0.0.1-SNAPSHOT.jar"]
