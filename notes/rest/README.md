
# RestServer 

The reason behind our choice of framework is explained in detail in the [ADR](/docs/ADR.md).
### Structure 

The rest server consists of the following classes:

- [NotesService.java](src/main/java/rest/NotesService.java): Service class, responsible for managing user accounts, notes, and various operations.
- [NotesController.java](src/main/java/rest/NotesController.java): REST controller class, handles HTTP requests and defines API endpoints. 
- [RestServerApplication.java](src/main/java/rest/RestServerApplication.java): Contains the start method for the server application and configures CORS.

In addition there are classes for handling exceptions:
- [AppExceptionHandler.java](src/main/java/rest//exceptions/AppExceptionHandler.java): Handles all the exceptions and connects to HTTP message.
- [ApiError.java](src/main/java/rest//exceptions/ApiError.java): Class for exceptions.
- [FileException.java](src/main/java/rest//exceptions/FileException.java): Exception when file not found. Status is `404 NOT FOUND`.
- [NoteNotFoundException.java](src/main/java/rest//exceptions/NoteNotFoundException.java): Exception when Note not found. Status is `404 NOT FOUND`.
- [UserAlreadyExistsException.java](src/main/java/rest//exceptions/UserAlreadyExistsException.java): Exception when user already exists. Status is `409 CONFLICT`.
- [UserNotFoundException.java](src/main/java/rest//exceptions/UserNotFoundException.java): Exception when user is not found. Status is `404 NOT FOUND`.


Class diagram for rest module you can find [here](../diagrams/README.md).

### Supported Requests

For all methodes the Host is `localhost:8080`.

**Accounts**

- Get Accounts: 
    - Request: GET `/notes/accounts`
    - Response: By this request Accounts will be returned. If successful the HTTP status is 200 OK. If an exception is thrown the status is 500 Internal Server Error. 

**Users and Authentication**

- Get User by Username
    - Request: GET `/notes/user?username={username}`
    - Response: Returns the user object with the specified username, and 200 OK response if successful. Otherwise response is 404 Not Found.

- Authenticate User
    - Request: POST `/notes/authenticate-user?username={username}&password={password}`
    - Response: Authenticates the user with the provided username and password. If successful, the response includes the user with an HTTP status code of 200 OK. In case of unsuccessful authentication, response status code is 404 Not Found.

- Create User
    - Request: PUT `/notes/create-user`
    - Content-Type: application/json
    - Response: Creates a new user. A successful creation will result in a 200 OK response. Otherwise, a response with a response status is 409 CONFLICT.

```
{
  "username" : "User",

  "password" : "Password1",

  "noteOverview" : []
}
```

- Users NoteOverview:
    - Request: GET `/notes/user/noteOverview?username={username}`
    - Response: Retrieves the note overview for a specific user, if this is successfull the HTTP status is set to 200 OK, else it is set to 404 Not Found. 

**Notes**
- Get User`s Note By Index
    - Request: GET `/notes/user/note?username={username}&index={index}`
    - Response: Returns the note object for the specified index and user. 

- Create Note
    - Request: PUT `/notes/create-note?username={username}
    - Content-Type: application/json
    - Response: Creates a new note for the specified user. A successful creation of a user will result in a 200 OK response. Otherwise, a response with a status code of 409 and the message "........." will be received.

- Delete Note
    - Request: DELETE `/notes/delete-note?username={username}&index={index}`
    - Response: Deletes a note for the specified index and user. If the note was deleted successfully the response will be 200 OK. If the note is not found, the HTTP status is 404 NOT FOUND.  

- Sort Notes by Created Date
    - Request: POST `/notes/user/sort-created?username={username}`
    - Response: Sorts the user's notes list by created date. If the user is not found, the HTTP status is 404 NOT FOUND. 

- Sort Notes by Title
    - Request: POST `/notes/user/sort-title?username={username}`
    - Response: Sorts the user's notes list by title. If the user is not found, the HTTP status is 404 NOT FOUND. 

- Sort Notes by Last Edited Date
    - Request: POST `/notes/user/sort-edited?username={username}`
    - Response: Sorts the user's notes list by last edited date. If the user is not found, the HTTP status is 404 NOT FOUND. 

**Test and Normal Modes**

The following methods are there to avoid overwriting server data when tests are run.

- Set Test Mode
    - Request: POST `/notes/test-mode`
    - Response: Sets the server to test mode.

- Set Normal Mode
    - Request: POST `/notes/normal-mode`
    - Response: Sets the server to normal mode.


### **Rest tests**

We have written tests for the rest server and obtained a test coverage of 

 ### Test classes 

- [RestServerApplicationTest.java](src/test/java/rest/RestServerApplicationTest.java)

### Test Coverage
For the rest module we aimed to test as much as we could, but because of limited time we did not achieve 90% test coverage in rest. With that being said, we achieved a test coverage of 67% and covered the most important parts. 

We used MockMvc to test the rest API and server.

