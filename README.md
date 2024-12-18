CLI Command: mvn clean test "-Dcucumber.filter.tags=@API" -Denv=prod
allure generate -c target/allure-results -o target/ashish

DefaultTimeOut=15000
## or configure from CLI==> mvn clean test "-Dcucumber.filter.tags=@getAPI7" -Denv=prod -Dtime=30000

Default PLAYWRIGHT_RETRY=2
Do from CLI==>set(windows)/export(MAC) PLAYWRIGHT_RETRY=<integer value>
then from CLI==> mvn clean test "-Dcucumber.filter.tags=@getAPI7" -Denv=prod