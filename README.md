CLI Command: mvn clean test "-Dcucumber.filter.tags=@API" -Denv=prod
allure generate -c target/allure-results -o target/ashish