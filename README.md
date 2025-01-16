# java-compiler-service
this is a compiler java

# INSTALL

## JAVA 21
homebrew link [here](https://formulae.brew.sh/formula/openjdk@21)

```shell
brew install openjdk@21
```

## MAVEN 3.9
homebrew link [here](https://formulae.brew.sh/formula/maven)

```shell
brew install maven
```
# Swagger
http://localhost:8000/grader-service/swagger-ui/index.html


# RUN
## 1. Maven package

```shell
mvn clean install -DskipTests
```

## 2. Run docker-compose

### up

```shell
docker-compose up --build
```

### down

```shell
docker-compose down
```

### Test api
```shell
curl -X GET http://localhost:8000/grader-service/api/health/check
```

###