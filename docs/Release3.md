# Release-3
In Release 3, the main goal was to deliver the final version of the Notes app. Throughout this release, our primary focus has been on implementing login functionality, establishing a reliable REST API and server, and making significant improvements to the code quality throughout the application. In addition, we have thoroughly written a complete set of tests to ensure the reliability of the entire application. Last, we have set up CI/CD in gitlab to assure that all commits passes through our core tests.

**Table of content**
- Work Habits
- New Features
- User Stories
- Code Improvements

## Work Habits
In Release 3, we improved on our established work habits from the previous release and made workflow improvements. Before the last sprint, the group collectively agreed on a slight adjustment to task management.

Throughout the whole period, we maintained a routine of multiple weekly meetings. In the first meeting of the week, typically held on Mondays, we sit down toghether and and discuss what goals we shoud have this week and what goals are realistic to achieve in the coming week. After release 2, we realised that we had a lot more work to do in the final release, so we decided to increase the frequency of our meetings. 

Our work strategy for the last sprint involved doing larger tasks when we sit togheter, and fix smaller issues independently, minimizing the need for constant communication on less critical tasks. Each team member was assigned a spesific segment of the project to concentrate on. This method allowed us to get a deeper understanding of our respective areas. Although we often worked in the same space, our tasks were more individualized, which we think led to more an effective process.That being said, each time a group member was stuck on a task, we helped each other to avoid time waste.

We reduced the frequency of pair programming with Live Share, emphasizing more individual work. 
The approach was effective during smooth project phases, but faced challenges with larger issues or bugs due to the team's limited familiarity with specific segments. 
Therefore, we still utilized pair programming for tackling significant issues and bugs, finding it more effective in those situations. 

To keep each other updated on the various parts of the code, and to maintain high code quality, we use "code review" in GitLab, where another group member reviews, and if necessary, gives feedback on the code before merging into dev branch. Additionally, since we often worked together physically, we also gave face-to-face feedback.

Upon reflecting on our work, we've identified a work habit that should have been implemented earlier. That is the practice of creating tests more consistently and developing them as we go. Although we started with tests towards the end of Release 2, the effort required to create tests for both the core and UI modules turned out to be more work than anticipated. As we caught up with testing in Release 3, it revealed several flaws in our application, allowing us to address multiple issues. In hindsight, we acknowledge that testing more frequently would have been a better strategy, ultimately improving our code base.
### Commit messages and merge requests 
The group realized the importance for clearer communication and agreed we needed more detailed commit messages forward. Although we have aimed to improved commit messages for this release, there has not always been a pressing need for them in our collaboration, as we often work closely together and verbally communicate our changes.  

Recognizing the need for clearer communication, the group agreed to enhance commit messages. 

```
The title provides a concise overview of the commit, summarizing the essence of the modifications. The description have specifics, mentioning which classes were involved, key functionalities affected, issues resolved, and any other important details. 
```

The approach mentioned ensures that each commit message serves as a valuable reference, facilitating efficient collaboration and comprehension among group members.

We acknowledge that not every single commit messages follow this standard. However, we believe that the combination of descriptive issues, frequent communication in meetings, the commit messages we made is sufficient.

To futher improve clearity, each of our merge requests is linked to a specific branch and issue. This removes the need for extensive merge commit messages into dev, since we don't squash commits. By maintaining individual commit histories for all issues in GitLab, it is unnecessary to duplicate this information in an extra merge commit message.  

### Task Management and Issues in GitLab
In Release 3, we increased the use GitLab's task management for complex issues, breaking them into smaller tasks for better group understanding and collaboration. While effective, applying this method to all issues could further enhance our task management. For example for the issue "Improve test coverage UI", the tasks were "AppControllerTest.java" and "LoginControllerTest.java".

We also improved the organization of issues by introducing more labels, such as `documentation`, `restAPI` and `bugFix`. Additionally, where applicable, we **linked** relevant issues. 

## New User Story for release 3 
```
User Login (US-5): "A user desire a secure and efficient login process to access their notes within the application. If the user does not have an existing account, they should also be able to create one."
```
## New Features 
In Release 3, several new features have been introduced. To improve functionality across different people we have implemented **user login**. We have also integrated a **REST API**, with the possibility to use the app with remote- and local data access.

To achieve this, we have introduced several new classes. Click [here](../notes/rest/README.md) for more information about the REST API and server, and [here](../notes/core/README.md) for login. 
### New classes:

For details about the classes go to README in the module.
### UI: 

#### Controller classes:

- [AbstractController.java](../notes/ui/src/main/java/ui/controllers/AbstractController.java) 
- [LoginController.java](../notes/ui/src/main/java/ui/controllers/LoginController.java) 
- [CreateUserController.java](../notes/ui/src/main/java/ui/controllers/CreateUserController.java) 

#### FXML Files:

- [Login.fxml](../notes/ui/src/main/resources/ui/Login.fxml) 
- [CreateUser.fxml](../notes/ui/src/main/resources/ui/CreateUser.fxml) 

#### DataAccess classes:

- [LocalNotesAcess.java](../notes/ui/src/main/java/dataaccess/LocalNotesAccess.java) 
- [RemoteNotesAcess.java](../notes/ui/src/main/java/dataaccess/RemoteNotesAccess.java) 
- [NotesAcess.java](../notes/ui/src/main/java/dataaccess/NotesAccess.java) 

Link to UI README.md [here](../notes/ui/README.md)
___
### Core:
- [Accounts.java](../notes/core/src/main/java/core/Accounts.java) 
- [User.java](../notes/core/src/main/java/core/User.java) 
- [Errors.java](../notes/core/src/main/java/core/Errors.java) 

Link to core README.md [here](../notes/core/README.md)
___
### RestServer:

- [NotesController.java](../notes/rest/src/main/java/rest/NotesController.java) 
- [NotesService.java](../notes/rest/src/main/java/rest/NotesService.java) 
- [RestServerApplication.java](../notes/rest/src/main/java/rest/RestServerApplication.java) 

Execptions classes:

- [ApiError.java](../notes/rest/src/main/java/rest/exceptions/ApiError.java) 
- [AppExceptionHandler.java](../notes/rest/src/main/java/rest/exceptions/AppExceptionHandler.java) 
- [FileException.java](../notes/rest/src/main/java/rest/exceptions/FileException.java) 
- [NoteNotFoundException.java](../notes/rest/src/main/java/rest/exceptions/NoteNotFoundException.java) 
- [UserAlreadyExistsException.java](../notes/rest/src/main/java/rest/exceptions/UserAlreadyExistsException.java) 
- [UserNotFoundException.java](../notes/rest/src/main/java/rest/exceptions/UserNotFoundException.java) 

Link to rest README [here](../notes/rest/README.md).
___
### IntegrationTest

- [NotesAppIT.java](../notes/integrationtest/src/test/java/NotesAppIT.java) 
 
 Link to IntegrationTest README [here](../notes/IntegrationTest/README.md).
 
### Missed features
Our app is functioning well, but there are a few functionalities missing that would make it truly optimal. 

One potential improvement a 'Log Out' button. Such a feature would not only be convenient for users but would also bolster security. Due to time constraints we did not prioritize this feature.

Additionally, our app lacks a 'Go Back' button on the 'Create User' screen. This would be a useful feature for users who navigate here unintentionally and wish to return to the login screen without creating a new account. Similar to the 'Log Out' button, the implementation of this feature has been deprioritized due to limited time.

A minor yet unaddressed detail is that the user interface currently displays only the 'Created date' of a note, without showing its 'Last edited date.' Although the note object includes this information, it's not visible in the interface.

Finally, a crucial area for improvement is the visibility of passwords during entry. Currently, passwords are not obscured when typed into the app, posing a potential security risk. Addressing this issue is vital for enhancing user privacy and security. 
___

## Code improvments 
In Release 3, we continued to improve the code quality and readability, building upon our previous implemented tools Checkstyle and spotbugs. We addressed and improved issues that Checkstyle had previously identified, ensuring a more consistent code. Additionally, we used SpotBugs to detect and resolve common Java issues, resulting  overall better code quality.

In Release 3, we increased attention to Checkstyle. This focus helped us to eliminate unnecessary imports, maintain consistent line spacing, and overall enhance the readability and thoroughness of our code.

### Test Coverage 
We have significantly improved the overall test coverage for this release. Just as in our previous releases, we have used JaCoCo to evaluate the extent of test coverage. This release also introduces a dedicated module `report` to add all test results into a single unified report.

For this release, our goal was to achieve 90% test coverage overall for the application, with a particular focus on testing all fundamental functionalities. For more detailed information on individual test coverage scores, refer to the module's readme. The overall test coverage is 86%.

### Controller Organization 
An important change in this release involves the reallocation of data from the controllers to Data Access (local and remote). Consequently, our controllers now primarily handle user interface interactions, while the critical logic resides in the core module. This architectural change enhances code clarity, and makes it possible to use both local and remote data access without changing our code.

This improvement, and explaination behind our choice is further explained in ADR [here](ADR.md).

### Persistence
When we introduced the possibility to have multiple users stored in Accounts, we needed to change our persistence classes. Accounts stores users, which stores noteoverview, which then stores notes. We have added serializer and deserializer for Accounts, and modified the module and persistence class to match the changes in structure -> `AccountsPersistence` and `AccountsModule`.

### Error class 

In Release 3, we collectively decided to establish a way for managing error handling within `core`. The solution involved making an Error enum class that contains all error messages. This approach ensures consistency in providing feedback to the user across the entire system.

This is also true for rest module, and is specified in the [rest README](../notes/rest/README.md). In general our exception handling choices are further explained in  ADR [here](ADR.md).

