FROM openjdk:22-jdk

 

WORKDIR /app

VOLUME /tmp

COPY target/*.jar preownedcars.jar

EXPOSE 5000

ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=password
ENV POSTGRES_DB=Openframe

COPY data.sql /docker-entrypoint-initdb.d/

ENTRYPOINT ["java", "-jar", "preownedcars.jar"]
