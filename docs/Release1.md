## Release-1

In this release we have created a MVP for the application. Our focus areas have been creating the folder- and code structure, reading/saving to file in JSON, and creating a prototype. We have implemented the following classes in core:

```
Note.java
NoteOverview.java
NoteOverviewListener.java
```

For the moment we have the core classes `Note.java` and `NoteOverview.java`, in addition to jackson serializers and deserializers. Writing notes to and from file is implemented in `NotesPersistence.java`.

As user interface we have the launcher `App.java` with two FXML-files: `App.fxml` and `Note.fxml`. For these two we have two controller classes: `AppController.java` and `NoteController.java`

When app is launched, the first scene is defined by `App.fxml`. When new note is created `Note.fxml` scene is portrayed. When a Note-object is created, it is saved in `NoteOverview.java` and written to with the json package in core. 

### User Story for realease 1: 

```
"To keep track of the notes composed in daily life, users wants to be able to create notes with titles, text, and dates and access them effortlessly."
``````
