@admin @putAPI @API

Feature: PUT API validations
  @putAPI1
  Scenario: PUT update single user.
    Given get scheme "putSingleUser"
    When PUT end URl "users/2" without query parameter
    And check 200 status code
    Then validate Response for ".name" is String
    Then validate Response for ".name" is "Ashish"
    Then validate Response for ".job" is String
    Then validate Response for ".job" is "SDET-II"
    Then validate Response for ".createdAt" is String


