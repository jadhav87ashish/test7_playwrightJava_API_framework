@admin @getAPI @API

Feature: GET API validations
  @getAPI1
  Scenario: List all users.
    Given GET end URL "users" with Query parameter "?page=2"
    And check 200 status code
    Then validate Response for ".page" is int
    Then validate Response for ".per_page" is int
    Then validate Response for ".total" is int
    Then validate Response for ".total_pages" is int
#    Then validate Response for ".data[*].id" is int
    Then validate Response for ".data[*].email" is String
    Then validate Response for ".data[*].first_name" is String
    Then validate Response for ".data[*].last_name" is String
    Then validate Response for ".data[*].avatar" is String

  @getAPI2
  Scenario: List single users.
    Given GET end URL "users/2" without Query parameter
    And check 200 status code
    Then validate Response for ".data.id" is int
    Then validate Response for ".data.email" is String
    Then validate Response for ".data.first_name" is String
    Then validate Response for ".data.last_name" is String
    Then validate Response for ".data.avatar" is String

