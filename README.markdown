A simple lib to help you running Javascript unit tests inside a Java
application.

How to use
==========

1. Build the JAR with `ant`. All dependencies are included;
2. Include the JAR and the dependencies in your application;
3. Extend the `JavascriptTestSuite` class as desired to fit your
   application structure and Javascript testing frameworks (see below);
4. Run it as a JUnit test case (in your IDE, in the command line or in
   your continuous integration tool).

How to customize
================

Extend the JavascriptTestSuite class and then use your customized
class to run the tests. More instructions can be found on
JavascriptTestSuite javadoc.
