# Notes 

## Project description

Our app is an intuitive note-taking application. Users must log in with username and password, or create a user to use the app. When logged in, the user have access to their saved notes. Users can create, edit and delete notes. All notes are presented in a list, where title, text and creation date is displayed for each note. Users can choose to sort their notes based on 'Created date', 'Last edited date', and alphabetically by 'Title (A-Z)'. 

## Project structure


### Modules
### `core` ### 
Read more about the core-module [here](core/README.md)
### `integrationtest` ###
Read more about the integrationtest-module [here](integrationtest/README.md)
### `report` ###
Read more about the report-module [here](report/README.md)
### `rest` ###
Read more about the rest-module [here](rest/README.md)
### `ui` ###
Read more about the ui-module [here](ui/README.md)

### Diagrams
Diagrams for our Notes-App can be found [here](/notes/diagrams/README.md).

## Functionality of the application

- Log in to an already existing user or create a new user
- Create notes
    - Add title and text
    - Creation date and edited date auto updates
- Edit notes
    - Edit title and/or text
    - Edited date auto updates but is not shown
- Delete notes
- Sort the list of notes based on
    - Created date
    - Last edited date
    - Alphabetically (A-Z)

## User Stories

User Stories can be found [here](../notes/UserStories.md). They are linked up with the issues in GitLab.

## User interface

User interfaces of the application
- Login [here](../docs/pictures/Notes-App-ui6.png) TODO
- Create user [here](../docs/pictures/Notes-App-ui3.png) TODO
- Overview of notes [here](../docs/pictures/Notes-App-ui1.png)
- Create note [here](../docs/pictures/Notes-App-ui4.png) TODO
- Edit note [here](../docs/pictures/Notes-App-ui2.png)
  
## Architectural Decision Records

You can find the detailed Architecture Decision Record (ADR) [here](../docs/ADR.md). It documents important architectural decisions the group made during the development process for this project. 