# ============================================================
# ETAPA 1: Construcción (Build)
# ============================================================
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# Copiar solo el descriptor pom.xml para aprovechar la caché de Docker
COPY pom.xml .

# Descargar dependencias del proyecto (queda cacheado si pom.xml no cambia)
RUN mvn dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Compilar y empaquetar la aplicación omitiendo los tests (para agilizar el deploy)
RUN mvn clean package -DskipTests

# ============================================================
# ETAPA 2: Imagen final de ejecución (Runtime ligera)
# ============================================================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Crear un usuario y grupo sin privilegios para mayor seguridad
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Copiar el archivo .jar generado en la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Asignar la propiedad del archivo al usuario no root
RUN chown appuser:appgroup app.jar

# Usar el usuario seguro
USER appuser

# Puerto por defecto (en servicios como Render se sobreescribe con $PORT)
EXPOSE 8080

# Opciones de JVM optimizadas para contenedores (gestión eficiente de memoria)
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"

# Comando de inicio de la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

