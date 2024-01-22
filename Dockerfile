#Imágen base a utilizar 
FROM openjdk:17-slim

#Copia el archivo JAR de la aplicación al contenedor de Docker y lo renombra como app.jar
COPY target/PagoService-0.0.1-SNAPSHOT.jar app.jar

#Ejecución de la aplicación 
ENTRYPOINT ["java", "-jar", "app.jar"]