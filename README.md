# Technical task: user-service

### Getting Started

Java 11 or better should be installed on your device. If it isn't, please, [setup](https://www.oracle.com/java/technologies/downloads/) it.
If previous step is done download zip file and extract file to any directory (or just make 'git clone').

### Installation

1. Clone the repository (or download *.zip* file).
2. Proceed to the project root folder.
3. Run `./mvnw clean package` to build it.

### Usage 

  Run using next command: `java -jar .\target\user-service.jar` (after build was successful)

### Features

- Swagger documentation: use this [endpoint](http://localhost:8078/swagger-ui.html)
- Postman [collection](https://elements.getpostman.com/redirect?entityId=20487409-609630f8-f0f3-420c-af15-768283e005a7&entityType=collection)
- REST API
- H2DB
- Validation

### Configuration

You can configure this project by modifying the `application.yml` [file](application.yml).

Example of age limit configuration:

```yaml
management:
  business:
    permitted-age: 18
