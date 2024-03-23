# Realease 2 

For the second release, we aimed to enhance the MVP from the initial release by adding new features such as the possibilty for editing and deleting notes, and sorting the notes based on date or title. We've expanded our tests with additional tests and introduced UI testing. The project maintains its modularity and is built upon a complete three-layer architecture. Note: json package is in core and not in seperate module. This is further explained in the ADR.

## New Features 

In Release 1, we focused on establishing the core structure and functionality of the application (MVP). In this release we focused on expanding the app. Therefore, the new features introduced in this release significantly enhance the app's utility and broaden its functionality. These features include:

- **Added Date to Note**: We enhanced the **`Note`** class by adding date attributes (**`createdDate`** and **`editedDate`**) to track creation and last edit dates for each note.
- **Delete Note**: We implemented logic to delete individual notes from **`NoteOverview`**.
- **Edit Note**: Implemented editNote logic, with a new controller (**`NoteEditController.java`**) and fxml-file (**`NoteEdit.fxml`**), that facilitates editing of existing notes. Users can modify note content, ensuring their notes remain up-to-date and relevant.
- **Listeners/Observers** (implementation described under **Code Quality**) to assure change in classes before we write to file.

### User Story for realease 2: 

```
Edit Notes (US-2): "A user wants to have the capability to edit their existing notes when necessary. The user also wants the option to revert any changes in case they have second thoughts."
``````

```
Sort Notes (US-3): "A user desire the ability to organize their notes by date or title for enhanced organization. The user also wishes to have control over the sorting preferences, allowing them to choose how they want their notes sorted."
```

```
Delete a Note (US-4): "A user should be able to delete notes that are no longer needed or relevant."
```

For a more detailed description of the User Stories, please refer to this [page](../notes/UserStories.md).

## Code quality

In Release 2, alongside the introduction of new features, we enhanced the code quality to establish a sturdy and easily maintainable codebase. Here's how we improved our code:

- **Comparators**: We introduced comparators such as **`CreatedDateComparator`**, **`EditedDateComparator`**, and **`TitleComparator`** to facilitate sorting notes by various criteria in NoteOverview.
- **Listeners/Observers**: We implemented the **`NoteOverviewListener`** and **`NoteListener`** interface in our app to enable real-time updates and maintain code quality. 
    - **`AppController`**  acts as a NoteOverviewListener, and **`NoteOverview`** acts as a NoteListener. 
    - Changes in Note will alert NoteOverview, which then alerts AppController. 
    - This ensures that updates are first applied to core before being reflected in the user interface, and contributes significantly to the overall code quality and user experience. 

  
- **File Management**: Our approach to file management is to implement an implicit storage strategy, ensuring that user data is stored within the application directory. This approach is platform-independent, ensuring versatility across various operating systems and devices. In addition, implicit storage provides a more user-friendly experience by removing the burden of selecting specific storage locations or managing complex document hierarchies. For file format, see this [README.md](/notes/README.md).

```
Note: Our explanation of file management was later revised in Release 3. The reason for this change is that we initially misunderstood the distinction between implicit and desktop storage. This is a desktop metaphor, as the user takes an active step each time something in the file is modified.
```

- **Checkstyle**: We have implemented Checkstyle in the code.  By using it, we can maintain a consistent code style, making the code easier to read and reducing potential errors. We did not prioritize to fix the checkstyle warnings in this release, as it is not cruical for app functionality.
- **Spotbugs**: Similarly, we have also integrated SpotBugs. With its help, we can catch hard-to-detect issues early on, enhancing the reliability and security of our code.

## Architecture

![Image Alt Text](/docs/pictures/architecture.png)

For a more detailed description of stucture, see [README](/README.md). 

## Work habits 
During this release, we improved the structure in our workflow.

### Milestones, issues and merge requests
Every development task is initiated through issues, and for each issue, we use labels to indicate various attributes, such as difficulty level and the type of task, whether it's Core or UI. Each task is carried out in its specific branch, ensuring an organized development path. To measure our progress towards key deliverables, we consistently utilize milestones. This submission is linked to the "Innlevering 2" milestone.

It's crucial that all merge requests are marked as ready and then undergo thorough peer review to ensure the code's correctness before being approved. We carefully check each other's merge requests. Also, each merge commit/request is connected to an issue. Each commit is in a branch connected to a issue.

### Commits
Our commit messages have a structured format, with a comprehensive title followed by a description of the changes made. 

In addition, it is important to mention that we don´t squash our commits. Therefore the merge commits only *mentions* the issue, and for more information you need to check the issue and underlying commits.

### Pair programming 
Additionally, we promoted pair programming, with team members alternating roles, and this collaborative is documented in our commits using the "Co-authored" notation. 

Using this approach, we avoid implementing changes that haven't been approved by the rest of the group, resulting in more accurate code. 




