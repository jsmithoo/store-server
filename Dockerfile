# Primera fase: Construcción con Gradle
FROM gradle:8.5-jdk AS build
 
# Establecer el directorio de trabajo
WORKDIR /app
 
# Copiar el archivo build.gradle y gradle-wrapper.properties
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
 
# Descargar dependencias sin construir la aplicación
RUN gradle build --no-daemon -x test || true
 
# Copiar el código fuente de la aplicación
COPY src ./src
 
# Compilar la aplicación y generar el JAR
RUN gradle clean bootJar --no-daemon
 
# Segunda fase: Imagen ligera con el JAR de la aplicación
FROM openjdk:17-alpine
 
# Crear un directorio de trabajo (Directorio raiz)
WORKDIR /app
 
# Copiar el JAR generado en la fase anterior y pegarlo dentro del contenedor
COPY --from=build /app/build/libs/*.jar app.jar

# Exponer el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]


