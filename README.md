# spring-boot-rabbitmq

## Run integration tests
```
mvn verify -Ddocker_host=localhost
```

## Run Rabbit
```
mvn docker:run -Ddocker_host=localhost
```

## Run application
```
mvn spring-boot:run -Ddocker_host=localhost
```
