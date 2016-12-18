export DATABASE_URL=postgres://spring:spring@localhost:5432/urtica
java -jar target/dependency/jetty-runner.jar target/*.war
