
FROM eclipse-temurin:17-jdk-jammy as builder

WORKDIR /workspace


COPY gradlew .
COPY gradlew.bat .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src


RUN chmod +x gradlew
RUN ./gradlew clean build -x test


RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)


FROM eclipse-temurin:17-jre-jammy

VOLUME /tmp

ARG DEPENDENCY=/workspace/build/dependency
COPY --from=builder ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=builder ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=builder ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java", "-cp", "app:app/lib/*", "tn.itbs.note.SystemeDeGestionApplication"]

EXPOSE 8080
