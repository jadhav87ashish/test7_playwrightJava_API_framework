@admin @getAPI @API

Feature: GET API validations
  @getAPI1
  Scenario: List all users.
    Given GET end URL "users" with Query parameter "page=2"
    And check 200 status code
    Then validate Response for ".page" is int
    Then validate Response for ".per_page" is int
    Then validate Response for ".total" is int
    Then validate Response for ".total_pages" is int

    Then validate Response for ".data[*].id" is int
    Then validate Response for ".data[0].id" is int
    Then validate Response for ".data[0].id" is 7

    Then validate Response for ".data[*].email" is String
    Then validate Response for ".data[0].email" is String
    Then validate Response for ".data[0].email" is "michael.lawson@reqres.in"

    Then validate Response for ".data[*].first_name" is String
    Then validate Response for ".data[0].first_name" is String
    Then validate Response for ".data[0].first_name" is "Michael"

    Then validate Response for ".data[*].last_name" is String
    Then validate Response for ".data[0].last_name" is String
    Then validate Response for ".data[0].last_name" is "Lawson"

    Then validate Response for ".data[*].avatar" is String
    Then validate Response for ".support.url" is String
    Then validate Response for ".support.text" is String

  @getAPI2
  Scenario: List single users.
    Given GET end URL "users/2" without Query parameter
    And check 200 status code
    Then validate Response for ".data.id" is int
    Then validate Response for ".data.email" is String
    Then validate Response for ".data.first_name" is String
    Then validate Response for ".data.last_name" is String
    Then validate Response for ".data.avatar" is String

  @getAPI3
  Scenario: Single users not found 404.
    Given GET end URL "users/23" without Query parameter
    And check 404 status code

  @getAPI4
  Scenario: GET List Resources.
    Given GET end URL "unknown" without Query parameter
    And check 200 status code
    Then validate Response for ".page" is int
    Then validate Response for ".per_page" is int
    Then validate Response for ".total" is int
    Then validate Response for ".total_pages" is int
    Then validate Response for ".total_pages" is int
    Then validate Response for ".total_pages" is int
    Then validate Response for ".data[0].id" is int
    Then validate Response for ".data[*].id" is int
    Then validate Response for ".data[0].name" is String
    Then validate Response for ".data[*].name" is String
    Then validate Response for ".data[*].year" is int
    Then validate Response for ".data[*].color" is String
    Then validate Response for ".data[*].pantone_value" is String
    Then validate Response for ".support.url" is String
    Then validate Response for ".support.text" is String

  @getAPI5
  Scenario: GET List single resource
    Given GET end URL "unknown/2" without Query parameter
    And check 200 status code
    Then validate Response for ".data.id" is int
    Then validate Response for ".data.year" is int
    Then validate Response for ".data.name" is String
    Then validate Response for ".data.name" is "fuchsia rose"
    Then validate Response for ".data.color" is String
    Then validate Response for ".data.pantone_value" is String
    Then validate Response for ".support.url" is String
    Then validate Response for ".support.text" is String

  @getAPI6
  Scenario: GET Single resource not found
    Given GET end URL "unknown/23" without Query parameter
    And check 404 status code