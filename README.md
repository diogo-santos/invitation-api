# Invitation API
Invitation API that process JSON file from a REST API and Post the consolidate data into another API

## Test Invitation process
```
mvn spring-boot:run
```

Now that the app is running, visit http://localhost:8080/invitation/process in order to execute the Invitation REST API

```
# Expected JSON response body:
{"message":"Results match! Congratulations!"}
```