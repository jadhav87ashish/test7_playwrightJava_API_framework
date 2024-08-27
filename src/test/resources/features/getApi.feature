@admin @getAPI

  Feature: Sample GET api case
    Scenario: Check GET API response
      Given GET request on end url "/api/users" and query param "page=2"
#      And verify API status code equals 200