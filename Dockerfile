FROM openjdk:8
EXPOSE 8888
ADD target/apiordonnanceur.jar apiordonnanceur.jar
ENTRYPOINT ["java","-jar","/apiordonnanceur.jar"]