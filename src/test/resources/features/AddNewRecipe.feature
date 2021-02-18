Feature: Add_new_recipe

  Scenario: An admin wants to add a new recipe to the store with one topping
    Given An admin
    When He adds a new recipe named "ChoconillaMs" with "CHOCOLATE" dough, "VANILLA" flavour, "MNMS" topping, "TOPPED" mix and "CHEWY" cooking
    Then The recipe "ChoconillaMs" is added to the website

  Scenario: An admin wants to add a new recipe to the store with two toppings
    Given An admin
    When He adds a new recipe named "ChoconillaMsDark" with "CHOCOLATE" dough, "VANILLA" flavour, "MNMS", "DARK_CHOCOLATE" topping, "TOPPED" mix and "CHEWY" cooking
    Then The recipe "ChoconillaMsDark" is added to the website

  Scenario: An admin wants to add a new recipe to the store with three toppings
    Given An admin
    When He adds a new recipe named "ChoconillaMsDarkWhite" with "CHOCOLATE" dough, "VANILLA" flavour, "MNMS", "DARK_CHOCOLATE", "WHITE_CHOCOLATE" topping, "TOPPED" mix and "CHEWY" cooking
    Then The recipe "ChoconillaMsDarkWhite" is added to the website