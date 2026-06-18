# ============================================================
#  Dockerfile - GestionBibliotheque
#  Build en 2 etapes : compilation Maven puis image legere JRE.
# ============================================================

# ----- Etape 1 : compilation -----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Cache des dependances : on copie d'abord le pom seul.
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Puis le code source, et on construit le .jar
COPY src ./src
RUN mvn -B clean package -DskipTests

# ----- Etape 2 : execution -----
FROM eclipse-temurin:17-jre
WORKDIR /app

# On recupere le .jar construit a l'etape precedente
COPY --from=build /app/target/*.jar app.jar

# Limite la memoire de la JVM pour tenir dans 512 Mo (offre gratuite Render)
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75 -XX:+UseSerialGC"

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
