# Java Automation Framework example

### Requirements
* Java  1.8 (1.8.0_201)
* Maven

### Description & Motivation
- This is a Java automation framework, based in Gherking for specifying
the behavior of the system
- Behavior of the system is translated to scenarios (or test cases)
- Each scenario consists of steps
- Cucumber is a Java tool that supports Behaviour-Driven Development(BDD).
Behaviour-Driven Development (BDD) is a set of practices that aim 
to reduce some common wasteful activities in software development:
    - Rework caused by misunderstood or vague requirements
    - Technical debt caused by reluctance to refactor code
    - Slow feedback cycles caused by silos and hand-overs
- Using Gherkin and BBD allow developers to clearly separate the
modelling of the System under test, the Specification of the test
cases, and the Implementation of automation code


### Libraries and frameworks:
- Cucumber
- TestNG
    -- It can be used for executing the test cases
- google-http-client Library
    -- Used for implementing the modelling of the System under test
- Log4j


#### Running the tests
- mvn clean install
This will execute all test cases and generate the reports

#### Reports & Logs
- Log4j is used to add log entries to the console output.
If you want to modify the logging (or create a file for the
log output) you can configure the log4j.properties file
- The report for the execution can be accessed after the project is built.
The directory is "cucumber-html-reports"
