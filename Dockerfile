# Dockerfile para la aplicación Spring Boot Blog
# Utiliza una imagen base de OpenJDK 17 oficial
FROM openjdk:17-jdk-slim

# Información del mantenedor
LABEL maintainer="alvaro696"
LABEL description="Aplicación Spring Boot Blog API REST"
LABEL version="1.0"

# Crear directorio de trabajo
WORKDIR /app

# Copiar archivos de Maven
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn

# Dar permisos de ejecución al wrapper de Maven
RUN chmod +x mvnw

# Descargar dependencias (esto se cachea si el pom.xml no cambia)
RUN ./mvnw dependency:go-offline -B

# Copiar el código fuente
COPY src src

# Construir la aplicación
RUN ./mvnw clean package -DskipTests

# Exponer el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "target/blog-0.0.1-SNAPSHOT.jar"]

# Configuración adicional para optimizar el contenedor
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar target/blog-0.0.1-SNAPSHOT.jar"]
