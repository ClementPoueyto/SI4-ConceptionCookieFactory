Feature: manage_a_shop
  As a manager I want to manage my shop informations, and have analytics on sales

  Background: A shop with a manager
    Given a shop with these analytics: "Chocolalala" 78 "Dark Temptation" 53 "Soo Chocolate" 18
    And Also in the analytics the following "custom" recipe dough "PLAIN", flavour "VANILLA", toppings "WHITE_CHOCOLATE", "MILK_CHOCOLATE", cooking "CHEWY" and "MIXED" ordered 20 times
    And the shop timetable : "MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY" from 9 to 18
    And a manager named "Jojo" "Bernard"


  Scenario: The manager wants to check the stats of his shop
    When The manager wants to check the stats of his shop
    Then He sees that 169 cookies have been sold
    Then He sees that the best-seller is the "Chocolalala"
    Then He sees that there's no technical failure

  Scenario: The manager wants to init or change his shop timetable
    When "Jojo" "Bernard" changes the timesheet of "WEDNESDAY,SATURDAY" to : from 10 to 20
    Then the shop has the correct timetable : "MONDAY,TUESDAY,THURSDAY,FRIDAY" from 9 to 18, "WEDNESDAY,SATURDAY" from 10 to 20

  Scenario: The manager hire a new emploeyee, in order to extand his shop timetable
    When the manager hire the employee "Jojo Junior" "Bernard"
    Then "Jojo Junior" "Bernard" is an employee of the shop
    When "Jojo Junior" "Bernard" changes the timesheet of "WEDNESDAY,SATURDAY" to : from 12 to 20
    Then the shop timetable hasn't changed, because he is not a manager
    Then the shop has the correct timetable : "MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY" from 9 to 18
    When "Jojo" "Bernard" changes the timesheet of "SUNDAY" to : from 8 to 12
    Then the shop has the correct timetable : "MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY" from 9 to 18, "SUNDAY" from 8 to 12