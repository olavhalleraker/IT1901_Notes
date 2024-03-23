# Architectural Decision Records

## **1. Abstract controller in UI**
 The project will contain many different pages and controllers. A lot of the controllers will be very similar, and needs the same methods.

### Considered Options
#### **Option 1: Inheritance with "parent" abstract controller**
Create a AbstractController class that has the method for switching between controllers. All controllers  inherits from this class.
|Pros | Cons|
|---- | ----|
| Share methods that all controllers, such as setDataAccess and setScene. |Complex to implement. Major changes since we haven't implemented it from the beginning. |
| Makes sure all controllers have necessary methods and same incapslation. Makes application more robust.
<br />

#### **Option 2: Static controller class**
Can create a class with static methods that the controllers can use.
|Pros | Cons|
|---- | ----|
| Relatively easy to implement.  | Worse encapsulation that option. 
| | Does not provide an easy way share fields between controllers. (Note: in later release we removed the common fields.)
<br />

#### **Option 3: No static and inheritence**
Individual controllers.
|Pros | Cons|
|---- | ----|
| Easily tailor these methods to each controller, though most likely there will not be a lot of changes between different controllers. If needed, these methods can anyway be added later by overriding or adding static methods. | Lots of repetitive code and unnecessary work. | 
<br />

### Decision Outcome
**Chosen option:** Option 1, Inheritance with abstract controller. At first we thought option 3 was enough, but as we expanded our app in release 3 we decided that an Abstract controller was necessary.

## **2. Document metaphor**
### Problem and context
In the development of our application, we faced the decision of how to store data. Either if we should store data on the desktop, following the document metaphor or opting for implicit storage within the app itself. The chosen storage method would impact the application's architecture, user experience, data privacy, and accessibility.

### Considered Options
#### **Option 1: Desktop style**
- Store both note data and user data on the user's device using a desktop storage metaphor.
- This approach aligns with traditional desktop applications where all data is stored locally and manually.
- For our application, this means that the user decides what to save and store. Every time a user creates a new note, edits a note, it manually has to confirm with a 'Save'-button for the note to be saved to the database. 
- Also the user has to explicitly save its data, when a new user is created. 

#### **Option 2: App style**
- Data is stored automatically in an app-based infrastructure, for example on a cloud based server.
- This option focuses on data sync and accessibility across devices but relies on internet connectivity. 
- For the note application this would mean that notes are automatically saved, and a user is able to for instance edit an already existing note without the need to press the 'Save'-button and so on. 


### Decision Outcome
**Chosen option:** 
We chose Desktop Storage as our primary method for storing both note data and user data. We were not very familiar with this two approaches, and until release 2, we thought the app followed the app style with implicit storage, when we from the start has followed desktop storage. In retrospect, parts of our app, such as saving and editing notes could have been more practical and followed more modern app behavoirs if it was using app style. But there is no big drawbacks by using desktop storage, because our app is relatively simple and largely driven by the user's preferences and actions regarding their notes. If, for instance, the application were a video game or something similar, an 'app style' approach would have been more advantageous. So in conclusion, the desktop style is more convenient for our app. 

## **3. How to access data** 
### Problem and context
How should the program access data from the persistence layer? It is crucial to establish a clear and efficient approach that separates different layers and ensures maintainability.

### Considered Options
#### **Option 1: NotesAccess Interface**
This option involves using a `NotesAccess` interface in a dataaccess package, which provides a set of methods for accessing data. This assures that remote and local data access classes have necessary methods, and enables us to change back and forth between using restAPI and local data storage. 

|Pros | Cons|
|---- | ----|
| A well-defined interface for data access makes the application easier to maintain and understand. |Hides remote data access calls. Can be more expensive than neccessary. |
| Need only one set of controllers, and two appControllers (remote and local), as they can use DataAccess interface.  |
<br />

#### **Option 2: Only one dataaccess**
Only one data access class. Then we must choose between local and remote data access (RestAPI vs local storage).
|Pros | Cons|
|---- | --- |
| Easy to implement. | Can´t have both local and remote access.

<br />

### Decision Outcome
**Chosen option:** Option 1, NotesAccess Interface. Makes it possible to switch between local and remote. Easy to use in controllers.

## **4. Library for mapping JSON** 
### Problem and context
We are going to store NoteOverview and Note objects, and later User and Accounts. Therefore we need serializers and deserialziers.

### Considered Options
#### **Option 1: Jackson library**
Library for serializing and deserializing with mapper to and from json string and objects.
|Pros | Cons|
|--- | ----|
| Members of the group are familiar with using this library.  |  |
| Easy to use, and easy to change when expanding project. |
<br />

#### **Option 2: Gson**
Same as above.
|Pros | Cons|
|---- | ----|
| Easy to use, and easy to change when expanding project.  |  

<br />


### Decision Outcome
**Chosen option:** Option 1, Jackson. Short and simple: we already are familiar with using the library. 

## **5. Framework for rest server API** 
### Problem and context
The project needs a robust solution for implementing a RESTful API, to handle communication between the client and server. Choosing a framework is important for efficient development, maintenance, and extensibility.

### Considered Options
#### **Option 1: Spring Boot**

|Pros | Cons|
|---- | ----|
| Esier to set up, has defaults that save us from writing a lot of code. |Comes with a lots of features, and we might not need all of them. |
| Many developers use Spring Boot, including todo-list-example. |
<br />

#### **Option 2: Other Frameworks**
|Pros | Cons|
|---- | ----|
| Other frameworks may provide more flexibility. | Might be harder to set up, less community support to look for help.
<br />

### Decision Outcome
**Chosen option:** Option 1, Spring Boot. As the group did not have any experience setting up a rest server, we choose to use the same fremework as the example project.

## **6. Error Messages:** 
### Problem and context
As the project's functionality expanded, multiple classes may have similar error messages. This led to the decision of whether to centralize all error messages or manage them within individual classes.

### Considered Options
#### **Option 1: Centralize Error Class**
Centralize all error messages in one class, to have a unified and easily maintainable repository of error messages. Ensures consistency in the project accross different classes.
|Pros | Cons|
|---- | ----|
| Ensures consistent error messages throughout the application. |Might be harder to see what the error message directly in the code. |
| Minimizes the risk of duplicating error messages across different classes. |
<br />

#### **Option 2: Distribute Error Handling**
Not centralize error messages and distribute then in each class. 
|Pros | Cons|
|---- | ----|
| Each class manages its own error messages independently. | Does not ensuring consistency across all error messages that may occur in more classes. 
| Easier to see directly in the code the expected error message. |  Similar error messages might be written in multiple places.
<br />

### Decision Outcome
**Chosen option:** Option 1, centralized error messases. This decision was driven by the importance of consistency in error messages accross all classes. This made it easier for us to comprehend and manage error messages uniformly in the group. However this does not extend to the rest server module. In REST, error messages are closely tied to HTTP status codes, which is we we opted for a own centralization in rest. 
