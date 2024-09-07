@admin @deleteAPI @API

Feature: DELETE API validations
  @deleteAPI1
  Scenario: DELETE single user.
    Given DELETE end URl "users/2" without query parameter
    And check 204 status code



