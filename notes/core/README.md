# Core
The core module is the main part of the application. It consists of the classes with the core functions of the app. This part is separate from the user interface, and it deals with the main tasks and processes that the app needs to do.

### **Structure**
In the core directory, there are two subfolders: core and json. The core folder contains code related to logic for the core functions of the app, while the json folder houses the code for file management and translation between object and json stirngs.

Class diagram of the core class can be found [here](../diagrams/README.md). 

### JSON File Format
#
Our project utilizes JSON (JavaScript Object Notation) as the data interchange format for storing and retrieving information. JSON is a lightweight and human-readable data format that is widely supported in various programming languages. This section provides an overview of the JSON file format used in our project.

### File Structure

In our project, JSON files are structured as follows:

```sh
[
    {
        "username": {username},
        "password": {password},
        "noteOverview": [
            {
                "title": {title},
                "text": {text}, 
                "created": {createdDate},
                "edited": {editedDate}
            },
            .
            .
            .
        ]
    },
    .
    .
    .
]
```
#

### **Classes**
**core**
- [Accounts.java](src/main/java/core/Accounts.java)
- [CreatedDateComparator.java](src/main/java/core/CreatedDateComparator.java)
- [EditedDateComparator.java](src/main/java/core/EditedDateComparator.java)
- [Errors.java](src/main/java/core/Errors.java)
- [Note.java](src/main/java/core/Note.java)
- [NoteOverview.java](src/main/java/core/NoteOverview.java)
- [TitleComparator.java](src/main/java/core/TitleComparator.java)
- [User.java](src/main/java/core/User.java)
- [UserValidation.java](src/main/java/core/UserValidation.java)


`Note` class handles represents an individual note. It enforces non-empty title and text through validation in the setters. `NoteOverview` manages a collection of Note objects, and has functionality such as add, remove and sort notes. 

The three classes `CreatedDateComaprator`, `EditedDateComaprator` and `TitleComaprator` is the comparator classes with logic for sorting the notes, and they are implemented in `NoteOverview`. 

`User` class represents an individual user in the application. Each User object has a username, password and an associated NoteOverview object, with all the users notes. 
`UserValidation` class is responsible for ensuring that username and password inputs is in the right format according to the requirements. 


`Accounts` class contains logic for managing all users of the app, it allows adding new users, checking if a given user exists in the system, and removing users. The class provides methods to validate user login. 

`Errors` enum is a collection of predefined error messages, where each enum constant represents a spesific type of scenario. 
#

**json**
- [AccountsPersistence.java](src/main/java(json/AccountsPersistance.java))

    **internal**

    - [AccountsDeserializer.java](src/main/java/json/internal/AccountsDeserializer.java)
    - [AccountsModule.java](src/main/java/json/internal/AccountsModule.java)
    - [AccountsSerializer.java](src/main/java/json/internal/AccountsSerializer.java)
    - [NoteDeserializer.java](src/main/java/json/internal/NoteDeserializer.java)
    - [NoteOverviewDeserializer.java](src/main/java/json/internal/NoteOverviewDeserializer.java)
    - [NoteOverviewSerializer.java](src/main/java/json/internal/NoteOverviewSerializer.java)
    - [NoteSerializer.java](src/main/java/json/internal/NoteSerializer.java)
    - [UserDeserializer.java](src/main/java/json/internal/UserDeserializer.java)
    - [UserSerializer.java](src/main/java/json/internal/UserSerializer.java) 

 `AccountsModule.java` handles serialization and deserialization specifically for Accounts and related classes as Note, NotOoverview and User.

 `AccountsPersistence.java` is responsible for managing storage and retrieval of Accounts data using json file format. It sets the file path for data storage and provides methods to load Accounts from and save Accounts to specified file.
 
#
### **Tests**

Listed below are the test classes that validate their corresponding classes in the core module. Our goal has been to achieve a test coverage of 90%, using the JaCoCo plugin. In pursuit of this target, we've employed JUnit for our testing procedures. In the core module, our **test coverage is at 99%.**
#
### Test classes

**core tests**
- [AccountsTest.java](src/test/java/core/AccountsTest.java) &rarr; Test for  [Accounts.java](src/main/java/core/Accounts.java) 
- [CreatedDateComparatorTest.java](src/test/java/core/CreatedDateComparatorTest.java) &rarr; Test for [CreatedDateComparator.java](src/main/java/core/CreatedDateComparator.java)

- [EditedDateComparatorTest.java](src/test/java/core/EditedDateComparatorTest.java) &rarr; Test for [EditedDateComparator.java](src/main/java/core/EditedDateComparator.java)

- [NoteOverviewTest.java](src/test/java/core/NoteOverviewTest.java) &rarr; Test for [NoteOverview.java](src/main/java/core/NoteOverview.java)

- [NoteTest.java](src/test/java/core/NoteTest.java) &rarr; Test for [Note.java](src/main/java/core/Note.java)
- [TitleComparatorTest.java](src/test/java/core/TitleComparatorTest.java) &rarr; Test for [TitleComparator.java](src/main/java/core/TitleComparator.java)

- [UserTest.java](src/test/java/core/UserTest.java) &rarr; Test for [User.java](src/main/java/core/User.java)

- [UserValidationTest.java](src/test/java/core/UserValidationTest.java) &rarr; Test for [UserValidation.java](src/main/java/core/UserValidation.java):


**json tests**
- [AccountsPersistanceTest.java](src/test/java/json/AccountsPersistanceTest.java) &rarr; Test for [AccountsPersistence.java](src/main/java(json/AccountsPersistance.java))

    **internal**
    - [AccountsJsonTest.java](src/test/java/json/internal/AccountsJsonTest.java) &rarr; Test for all classes in internal folder





