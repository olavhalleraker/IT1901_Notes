# __Notes-App__

The Notes-App is a simple application designed to help users create, edit, manage, and securely log in to handle their notes. For a more detailed description of the app, please refer to [this README file](/notes/README.md).

## Eclicse-Che
Open [this](https://che.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2311/gr2311?new) link to run in Eclipse che. 

## Building and running the application
___

The project requires Maven for building and running the app from the command line. Hoewever, when running the application within Eclipse Che, Maven is not necessary. 

The application can be ran in two different ways: locally or remote. 

### Running the application locally 
1) Change the directory to the notes folder
2) Execute a clean build of the Maven project and install the package into the local repository
3) Run the JavaFX application user interface module with Maven

```sh
1) 
cd notes

2)
mvn clean install
#Tests can be skipped with mvn clean install -Dmaven.test.skip

3)
mvn javafx:run -pl ui
```
### Running the application remote

1) Change the directory to the notes folder
2) Execute a clean build of the Maven project and install the package into the local repository
3) Start server
4) Open a new terminal window. Run the JavaFX application UI module with Maven

```sh
1) 
cd notes

2)
mvn clean install
#Tests can be skipped with mvn clean install -Dmaven.test.skip

3)
mvn spring-boot:run -pl rest
 
4) 
mvn javafx:run -pl ui -P remoteapp
#Must be written in a separate terminal window after repeating step 1) 

```

## Testing 
___

To run all tests, run: 

```sh
1)
cd notes

2)
mvn test

#To get a report of the tests, run this command. 
3)
mvn jacoco:report
#Report can be found under /report/target/site/index.html
```

To only run `ui` tests:

```sh 
1) cd notes
2. mvn test -pl ui
```

To only run `core` tests:

```sh
1) cd notes
2) mvn test -pl core
```

To only run `rest` tests:

```sh
1) cd notes
2) mvn test -pl rest
```

## Shippable version of product

To load shippable version:
```sh
1)
cd notes

2)
mvn javafx:jlink -f ./ui/pom.xml

3)
mvn jpackage:jpackage -f ./ui/pom.xml
```
If not downloaded automaticly, navigate to `ui/target/dist/NotesFX-1.0.0` and reveal on local storage. Then follow computer instructions.  
## Milestone reports
___
The project has three releases, and you can read about them in the [docs directory](/docs).

**Release 1** 

The first release focused on establishing basic and essential functionality for the application, and initiated on testing of the core module. 

Read more [here](/docs/Release1.md).

**Release 2**

In this release we continued to expand the functionality, implemented additional core testing, and initiated user interface testing. 

Read more [here](/docs/Release2.md).

**Release 3**

In the third release, we focused on adding more functionality, including implementing a login system with users, improving code quality, REST API and server. We also prioritezed achieving high test coverage on all testing areas. 

Read more [here](/docs/Release3.md).# IT1901_Notes
