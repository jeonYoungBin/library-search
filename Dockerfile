FROM gradle:8.5-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle :search-api:bootJar -x test --no-daemon

FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/search-api/build/libs/library-search-0.0.1.jar library-search.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "library-search.jar"] 