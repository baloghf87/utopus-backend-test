Usage
-----

1, Specify the path to your input file in utopus-backend-test-common/src/main/resources/application-common.properties

2, Optionally edit src/main/resources/application.properties files in other projects according to your needs

3, Build using maven: mvn clean package

4, Run the console application: java -jar utopus-backend-test-console/target/backend-test-console-1.0-FERENC-BALOGH.jar

5, Run the web application: java -jar utopus-backend-test-console/target/backend-test-console-1.0-FERENC-BALOGH.jar

6, Test the endpoints using your browser or favourite REST client.

    Examples using the default port (8090):

    http://localhost:8090/employee/sort

    http://localhost:8090/employee/Michael/Smith/department

    http://localhost:8090/department/finance/employees
