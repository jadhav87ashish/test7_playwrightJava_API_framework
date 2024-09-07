@admin @patchAPI @API

Feature: PATCH API validations
  @patchAPI1
  Scenario: PATCH update single user.
    Given get scheme "patchSingleUser"
    When PATCH end URl "users/2" without query parameter
    And check 200 status code
    Then validate Response for ".name" is String
    Then validate Response for ".name" is "Ashish"
    Then validate Response for ".job" is String
    Then validate Response for ".job" is "SDET-II"
    Then validate Response for ".updatedAt" is String


