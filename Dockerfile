FROM eclipse-temurin:21-jre
WORKDIR /app
COPY build/libs/*.jar app.jar
EXPOSE 80
ENTRYPOINT ["java","-Doracle.jdbc.timezoneAsRegion=false","-jar","/app/app.jar"]
