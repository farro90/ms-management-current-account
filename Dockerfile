FROM openjdk:11
VOLUME /tmp
EXPOSE 8106
ADD ./target/ms-management-current-account-0.0.1-SNAPSHOT.jar ms-management-current-account.jar
ENTRYPOINT ["java","-jar","ms-management-current-account.jar"]