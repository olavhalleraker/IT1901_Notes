# Integrationtest

### Reason for implementing integrationstests

Integration tests play a vital role in testing the whole application. Even if individual tests pass, there's a chance that the modules may not work together correctly. The integration test checks if separate modules of the application function together as intended.

### Implementation of module in Notes

The integration test module checks if the JavaFX client application effectively connects and communicates with a Spring Boot server. Through Maven and the safe-fail plugin, the integration test module, during testing, initiates the server application with a test configuration.