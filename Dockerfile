FROM openjdk:17-alpine
MAINTAINER Elleined

# Docker MySQL Credentials
ENV MYSQL_HOST=mysql_server
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root
ENV MYSQL_PORT=3306
ENV MYSQL_DATABASE=spring_oauth_social_login_db
ENV PORT=8087

ADD ./target/*.jar spring-oauth-social-login.jar
EXPOSE 8087
CMD ["java", "-jar", "spring-oauth-social-login.jar"]
