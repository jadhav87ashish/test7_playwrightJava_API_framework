@admin @postAPI @API

Feature: POST API validations
  @postAPI1
  Scenario: POST Create single user.
    Given get scheme "createSingleUser"
    When POST end URL "users" without query parameter
    And check 201 status code
    Then validate Response for ".name" is String
    Then validate Response for ".job" is String
    Then validate Response for ".id" is String
    Then validate Response for ".createdAt" is String

  @postAPI2
  Scenario: POST register successful
    Given get scheme "registerSingleUser"
    When POST end URL "register" without query parameter
    And check 200 status code
    Then validate Response for ".id" is int
    Then validate Response for ".token" is String

  @postAPI3
  Scenario: POST Register - unsuccessful
    Given get scheme "registerUnsuccessfulSingleUser"
    When POST end URL "register" without query parameter
    And check 400 status code
    Then validate Response for ".error" is String
    Then validate Response for ".error" is "Missing password"

  @postAPI4
  Scenario: POST Login - successful
    Given get scheme "loginSuccessful"
    When POST end URL "login" without query parameter
    And check 200 status code
    Then validate Response for ".token" is String
