# Etapa 1
FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar el pom y descargar dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el resto del código fuente
COPY src ./src

# Compilar el proyecto y empaquetar el .jar
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final
FROM eclipse-temurin:21-alpine
WORKDIR /app

# Copiar el jar generado desde la etapa de build
COPY --from=build /app/target/inventory-system-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto de Spring Boot
EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
