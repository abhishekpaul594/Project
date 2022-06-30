@test
Feature: Automation at home

  Scenario: Test
    When I navigate to Home page
    And I click on element MMTHome.close_popup
    And I click on element MMTHome.from_city
    And I enter text Kolkata to element MMTHome.from_txt
    And I wait for 1 secs
    And I click on element MMTHome.select_cty
    And I click on element MMTHome.to_city
    And I enter text Bengaluru to element MMTHome.to_txt
    And I wait for 1 secs
    And I click on element MMTHome.select_cty
    And I click on element MMTHome.search_btn
    And I click element MMTHome.ok_got_it if displayed
    And I click on element MMTHome.view_prices
    And I click on element MMTHome.book_now
    And I switch to child window 1
    And I double click on element MMTHome.not_secure_trip
    And I scroll to element MMTHome.add_new_adult
    And I click on element MMTHome.add_new_adult
    And I enter text Abhishek to element MMTHome.first_name
    And I enter text Paul to element MMTHome.last_name
    And I double click on element MMTHome.gender_male
    And I enter text 8583883199 to element MMTHome.mobile_number
    And I enter text abh@gma.com to element MMTHome.email
    And I press Tab key in keyboard
    And I double click on element MMTHome.continue
    And I double click on element MMTHome.continue
    And I click on element MMTHome.confirm
    And I click element MMTHome.skip_to_addon if displayed
    And I click on element MMTHome.proceed_to_pay