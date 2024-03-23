# UI

To set up the user interface for our application, the group used JavaFX to design and implement FXML files for all scenes. Each scene has its own controller, which is responsible for handling specific UI functionalities. To ensure clearity in the project the controlle'´s name match the fxml-file, such as Login.fxml and LoginController.java. 

The UI module includes all visual compentets, layout and elements the user interact with. It is crucial for providing a user-friendly experience while interacting with the application. The UI also includes classes for data-access, which are vital for managing interactions with core, and the rest server.

### **Structure**
The UI module seperates controller, dataaccess, and FXML files, to enchance organization. The dataaccess classes are seperated in `dataaccess`, positioned at the same level as the internal ui directory. 
Controllers are located in `ui/controllers` directory. The FXML files are located in `ui/resources`. 

Class diagram for the controllers you can find [here](../diagrams/README.md)

#

### **UI consists of the following classes**

#### Controller classes 

- [AbstractController.java](src/main/java/ui/controllers/AbstractController.java) 
- [AppController.java](src/main/java/ui/controllers/AppController.java)
- [CreateUserController.java](src/main/java/ui/controllers/CreateUserController.java)
- [LoginController.java](src/main/java/ui/controllers/LoginController.java)
- [NoteController.java](src/main/java/ui/controllers/NoteController.java)
- [NoteEditController.java](src/main/java/ui/controllers/NoteEditController.java)


The `AbstractController` class is an abstract class for all the controller classes in the UI module. The class includes methodes to transition between different scenes, and establishes connection with data access. For more information about this structure choice see [ADR](/docs/ADR.md).

The `LoginController` initiates the application's start scene and manages login functionality. It also includes a button to create a user, leading to the `CreateUserController` scene. The `AppController` administers the scene displaying all user notes. The `NoteController` handles the creation of new notes, while the `NoteEditController` is responsible for editing existing notes.

#

#### FXML files 

- [App.fxml](src/main/resources/ui/App.fxml)
- [CreateUser.fxml](src/main/resources/ui/CreateUser.fxml)
- [Login.fxml](src/main/resources/ui/Login.fxml)
- [Note.fxml](src/main/resources/ui/Note.fxml)
- [NoteEdit.fxml](src/main/resources/ui/NoteEdit.fxml)

The FXML files define the stucture of the user interface. 

#

#### DataAccess classes

- [NoteAccess.java](src/main/java/dataaccess/NotesAccess.java)
- [LocalNoteAccess.java](src/main/java/dataaccess/LocalNotesAccess.java)
- [RemoteNoteAccess.java](src/main/java/dataaccess/RemoteNotesAccess.java)

For information about the choice of having a data access interface, NotesAccess, see [ADR](/docs/ADR.md).

`RemoteNotesAccess` extends the NotesAccess interface and is responsible for handling user data stored on a remote server. The class communicates with the server through HTTP requests, and managing user actions like authenticating user logins, and handling note operations remotely. 

`LocalNotesAccess` also extends the NotesAccess interface, but gets data stored locally in accounts.json, which is located on user.home. Its main functionality include handling user logins, managing note operations and updating the local storage after each relevant operation. 

 ### **UI tests**

 We have written tests for all controllers. 

 ### Test classes 

 #### Controller tests
- [AppControllerTest.java](src/test/java/ui/controllers/AppControllerTest.java)
- [CreateUserControllerTest.java](src/test/java/ui/controllers/CreateUserControllerTest.java)
- [LoginControllerTest.java](src/test/java/ui/controllers/LoginControllerTest.java)
- [NoteControllerTest.java](src/test/java/ui/controllers/NoteControllerTest.java)
- [NoteEditControllerTest.java](src/test/java/ui/controllers/NoteEditControllerTest.java)

#### DataAccess test
- [RemoteNotesAccessTest.java](src/test/java/dataaccess/RemoteNotesAccessTest.java)

### Test Coverage
For the UI module we aimed to test as much as we could. The test coverage for this module is 82%, and the controllers have 99% test coverage. Testing the UI extensively is important because these tests simulate real user interactions, and helps us catch bugs and issues early that might be missed by other testing methods. It is crucial to test user interactions, therefore we have prioritized achieving a high percentage of UI controller tests. 

It is important to mention that the UI tests is using local data storage. RemoteDataAccess is therefore tested seperatly with WireMock.


 