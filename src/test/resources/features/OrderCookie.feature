Feature: order_cookie
    As a guest then a user I want to order a cookie

    Background: A shop with stocks and employee
        #######     SHOP1     #######
        Given a shop with fee of 0.25 and another with fee of 0.2
        And first with a position of 43.59927891456512, 7.0855012404924445 and second with a position of 43.622434445147924, 7.0465445423113655
        
        ###         TIMETABLE         ###
        And first shop's timetable: "MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY" from 9 to 18, "SATURDAY" from 10 to 16, "SUNDAY" from 10 to 14
        And second shop opens "MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY" from 8 to 20

        ### EMPLOYEES / MANAGER ###
        And first shop has "employee" "Donald" "McFee", second shop has "employee" "Mickey" "Smith"

        ###           ANALYTICS        ###
        And first shop analytics: "Chocolalala" 45 "Dark Temptation" 12 "Soo Chocolate" 25
        And second shop analytics: "Chocolalala" 10 "Dark Temptation" 32 "Soo Chocolate" 25

        ########     CUSTOMERS     #######
        And a user of name "Ursul" with email "ursul@gmail.com"
        And a guest with email "jojo@gmail.com"
        And a position 43.61830871790319, 7.076209825108425

    Scenario: Guest orders classical cookies (with bestof cookies) to the first shop, then subscribe, order custom cookie
        When he orders 3 "Chocolalala"
        And the employee delivers the order
        # customer pays the exact amount
        # chocolalala is best of : so 10% and shop fee is 0.25
        And customer pays $5.74
        # shop stock
        Then the stock of the shop is DOUGH "OATMEAL" 97
        And the stock of the shop is FLAVOUR "CINNAMON" 97
        And the stock of the shop is TOPPING "MILK_CHOCOLATE" 97 "MNMS" 97
        # analytics
        And the shop analytics are "Chocolalala" 48
        And the factory analytics are "Chocolalala" 58
        # guest register
        When "Joel" registers and subscribe to loyalty program
        Then there is an account with his information in the factory
        And he is a member and he has 0 cookie in his cookie pot
        # Scenario : classic order
        When user orders 31 "Soo Chocolate"
        And the employee scans this order
        Then Joel pays $27.13 
        And he has 31 cookies in his pot
        # Scenario : 10% order cookie and order custom cookie
        Then he orders 3 cookies "customjojo" dough "PLAIN", flavour "VANILLA", toppings "WHITE_CHOCOLATE", "MILK_CHOCOLATE", cooking "CHEWY" and "MIXED" with dose 2
        And the recipe "customjojo" is 3 times in the analytics
        And he pays the amount of $5.91
        # Scenario : user wants to order a cookie but shop has a technical failure so another shop is proposed
        Then the shop has a technical failure
        And Joel wants to order cookies but he can't
        Then he chooses another near shop with position 43.622434445147924, 7.0465445423113655 
        # Scenario : user wants to order a cookie but the shop doesn't have enough stock 
        Then user wants to order 150 "Dark Temptation"
        And shop hasn't enough stock

    Scenario: Guest orders extended recipes of cookies
        When the customer chooses the recipe "Chocolalala"
        And modifies the dose by 3
        And replaces the 2st topping with "DARK_CHOCOLATE"
        And renames it "ChocoMALALA"
        Then he orders 3 cookies with this customized recipe
        And customer pays $18.28

        
    Scenario: Changing an order from a shop to another
        And a shop with a fee of 0.2
        And another shop
        And the first shop has a technical failure
        When he creates an order of 1 special cookies of a custom recipe with "CHOCOLATE" dough, "VANILLA" flavour, "WHITE_CHOCOLATE" topping, "TOPPED" mix and "CHEWY" cooking
        Then the order cannot start
        Then he changes his order shop for the 0th proposed
        Then the order can start


    Scenario: Pass an order with MarcelEat delivery
        And he is at the position 43.59329 7.093917
        And a shop with a fee of 0.2 
        And the shop is at the position 43.618119 7.075675
        And a MarcelEat deliver
        When he creates an order of 3 cookies "Chocolalala"
        And he wants his order to be delivered in 1 hour
        Then he pays $6.12 plus $2.06 for delivery


    Scenario: Ask for last minute delivery MarcelEat delivery
        And he is at the position 43.59329 7.093917
        And a shop with a fee of 0.2 
        And the shop is at the position 43.618119 7.075675
        And a MarcelEat deliver
        And he has an order of 3 cookies "Chocolalala"
        When he asks a last minute delivery 20 minutes before picked up time
        Then he pays $3.09 for delivery


    Scenario: Ask delivery but he is too far from the shop 
        And he is at the position 43.652627 6.89499
        And a shop with a fee of 0.2 
        And the shop is at the position 43.618119 7.075675
        And a MarcelEat deliver
        When he creates an order of 3 cookies "Chocolalala"
        Then he can't be delivered because he is too far


