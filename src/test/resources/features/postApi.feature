@admin @postAPI @API

Feature: POST API validations
  @postAPI1
  Scenario: Verify mapping API response.
    Given get scheme "createSingleUser"
    When POST end URL "users" without query parameter
    And check 201 status code
    Then validate Response for ".name" is String
    Then validate Response for ".job" is String
    Then validate Response for ".id" is String
    Then validate Response for ".createdAt" is String
