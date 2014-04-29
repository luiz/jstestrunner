A simple lib to help you running Javascript unit tests inside a Java
application.

How to use
==========

1. Build the JAR and install it locally with `mvn install`;
2. Include the JAR and the dependencies in your application, preferably using
   Maven:

    <dependency>
        <groupId>br.com.caelum</groupId>
        <artifactId>jstestrunner</artifactId>
        <version>1.1.0-SNAPSHOT</version>
    </dependency>

3. In your project, extend the `JavascriptTestSuite` class as desired to fit
   your application structure and Javascript testing frameworks (see below);
4. Run it as a JUnit test case (in your IDE, in the command line or in your
   continuous integration tool).

How to customize
================

Extend the JavascriptTestSuite class and then use your customized class to run
the tests. Don't forget to implement the static `suite` method on your class.
More instructions can be found on JavascriptTestSuite javadoc.

Acknowlegments
==============

Many thanks to Caires Santos (cairesvs) and Lucas Cavalcanti (lucascs)
for the help and original code.
