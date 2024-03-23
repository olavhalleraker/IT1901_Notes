
# User Stories 

#### User stories for future releases will be updated as functionality advances in later releases.

## UserStory for release 1: 

### View and Create Notes (US-1)
```
"To keep track of the notes composed in daily life, users wants to be able to create notes with titles, text, and dates and access them effortlessly."
```

#### Important to See
- US-1-1 Display a list of notes.
- US-1-2 Provide an option to create new notes.

#### Important to Do
- US-1-3 Enable to add a new note.
- US-1-4 The ability to save notes to a file.

## UserStories for release 2: 

### Edit Notes (US-2)
```
"A user wants to have the capability to edit their existing notes when necessary. The user also wants the option to revert any changes in case they have second thoughts."
```

#### Important to See
- US-2-1 Button to edit a selected note.
- US-2-2 Scene to edit note. 

#### Important to Do
- US-2-3 Allow editing of note fields.
- US-2-4 Implement an option to revert changes made to a note.

### Sort Notes (US-3)
```
"A user desire the ability to organize their notes by date or title for enhanced organization. The user also wishes to have control over the sorting preferences, allowing them to choose how they want their notes sorted."
```

#### Important to See
- US-3-1 Button to sort notes. 

#### Important to Do
- US-3-2 Enable sorting of notes by date or title.

### Delete a Note (US-4)
```
 "A user should be able to delete notes that are no longer needed or relevant."
 ```

#### Important to See
- US-4-1 Button to delete a note.

#### Important to Do
- US-4-2 Allow deleting the selected note.
- US-4-3 Ensure that the note is deleted from the file.

## UserStory for release 3: 

## User Login (US-5)
```
"A user desire a secure and efficient login process to access their notes within the application. If the user does not have an existing account, they should also be able to create one."
```

### Important to See
- US-5-1 Present a login screen or page.
- US-5-2 Include fields for entering a username and password.
- US-5-3 Display the user's list of notes.

### Important to Do
- US-5-4 Enable users to enter their login credentials.
- US-5-5 Authenticate the user and grant access to the application.
- US-5-6 Provide an option to create a new user if one does not exist.
- US-5-7 File with information about user authentication and users notes 

# Issues related to User Stories

### User Story 1 
US1-1: 
- #4 Create user interface
- #9 Add date to note
- #10 Remove date from Note constructor
- #26 ListView
- #29 Error handling same title
- #42 Fix bug in AppController.java

US1-2
- #6 Create fxml-file
- #14 Create logic and design for new note stage

US1-3
- #2 Create Note Class
- #3 Create NoteOverview class
- #7 Create NoteObserver class
- #8 Create Controller logic
- #14 Create logic and design for new note stage
- #20 New note is added to ListView
- #51 fix constructor wronghandling

US1-4
- #11 FileManagement classes
- #12 ReadNoteFromFile
- #13 WriteNoteFromFile
- #15 Add Jackson dependencies
- #18 User needs serializers and deserializers
- #22 User needs to be able to save to file

#
### User Story 2 
US2-1: 
- #30 Create editnote user interface

US2-2
- #30 Create editnote user interface
- #36 Edit note

US2-3
- #50 fix getNotes

US2-4
- #36 Edit note
#
### User Story 3
US3-1
- #48 Implement sort

US3-2
- #33 Make comparators for Notes
- #59 Implement date in UI
#
### User Story 4 
US4-1: 
- #35 Delete-button

US4-2
- #34 Create delete note logic
- #37 Fix delete note

US4-3
- #34 Create delete note logic
#
### User Story 5 
US-5-1: 
- #60 New Scene Login

US-5-2
- #60 New Scene Login
- #70 login UI

US-5-3
- #75 Rearrange structure in UI
- #110 Fixes in controllers

US-5-4
- #73 Create LoginController.java

US-5-5
- #61 Login core
- #63 User.java class
- #66 Accounts.java class
- #69 UserValidation.java
- #70 login UI
- #74 Fix checkEqualPassword method
- #82 Change filepath in AccountsPersistence
- #99 Fix validation in core.User
- #103 Fix code in Accounts and User
- #111 Numbers in username
- #125 fix error messages in ui - LoginController

US-5-6
- #70 login UI
- #71 Create CreateUserController.java
- #72 Create CreateUser.fxml

US-5-7
- #83 Create Dataaccess folder and logic
- #84 Create NotesAccess.java
- #85 Create RemoteNotesAccess.java
- #86 Create LocalNotesAccess.java
- #109 Fix constructor in localNotesAccess