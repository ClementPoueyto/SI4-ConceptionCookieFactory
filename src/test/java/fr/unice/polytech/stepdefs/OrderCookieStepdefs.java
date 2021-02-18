package fr.unice.polytech.stepdefs;

import fr.unice.polytech.factory.FactoryFacade;
import fr.unice.polytech.marceleat.MarcelEat;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.order.OrderItem;
import fr.unice.polytech.recipe.CookingType;
import fr.unice.polytech.recipe.MixType;
import fr.unice.polytech.recipe.Recipe;
import fr.unice.polytech.recipe.RecipeBuilder;
import fr.unice.polytech.recipe.item.Dough;
import fr.unice.polytech.recipe.item.Flavour;
import fr.unice.polytech.recipe.item.Topping;
import fr.unice.polytech.customer.Guest;
import fr.unice.polytech.customer.User;
import fr.unice.polytech.exception.UnavailableShopException;
import fr.unice.polytech.factory.Employee;
import fr.unice.polytech.tools.Analytic;
import fr.unice.polytech.tools.Position;
import fr.unice.polytech.shop.Shop;
import fr.unice.polytech.shop.stocks.StockIngredient;
import fr.unice.polytech.shop.timesheet.Timesheet;

import io.cucumber.java8.En;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(value = Cucumber.class)
@CucumberOptions(plugin = {"pretty"}, features = "src/test/resources/features")

public class OrderCookieStepdefs implements En {
    FactoryFacade factory = new FactoryFacade();
    Shop shop1 = new Shop(factory);
    Shop shop2 = new Shop(factory);
    Shop shop;
    Shop anotherShop;
    OrderItem orderItem;
    Order order;
    List<Shop> nearestShops;
    Guest guest;
    User user;
    User ursul;
    Employee employee1;
    Employee employee2;
    Order order1;
    Order order2;
    Order order3;
    Recipe custom;
    RecipeBuilder customizer;
    UnavailableShopException shopException;
    MarcelEat marcelEat;


    public OrderCookieStepdefs(){
        /* -------------- Given : init --------------- */
        
        // fee
        Given("a shop with fee of {double} and another with fee of {double}", (Double feeShop1, Double feeShop2)->{
            shop1.setFee(feeShop1);
            shop2.setFee(feeShop2);
            assertEquals(2, factory.getShops().size());
        });
        // position
        And("first with a position of {double}, {double} and second with a position of {double}, {double}", (Double latShop1, Double longShop1, Double latShop2, Double longShop2) -> {
            Position shop1Pos = new Position(latShop1, longShop1);
            shop1.setPosition(shop1Pos);
            Position shop2Pos = new Position(latShop2, longShop2);
            shop2.setPosition(shop2Pos);
        });
        // timetable
        And("first shop's timetable: {string} from {int} to {int}, {string} from {int} to {int}, {string} from {int} to {int}", 
            (String weekday, Integer weekdaysFrom, Integer weekdaysTo, String saturday, Integer saturdayFrom, Integer saturdayTo, String sunday, Integer sundayFrom, Integer sundayTo)->{
                Timesheet timesheetShop1 = new Timesheet();
                timesheetShop1.setDaysSchedule(strToArray(weekday), LocalTime.of(weekdaysFrom, 0), LocalTime.of(weekdaysTo, 0));
                timesheetShop1.setDaySchedule(DayOfWeek.valueOf(saturday), LocalTime.of(saturdayFrom, 0), LocalTime.of(saturdayTo, 0));
                timesheetShop1.setDaySchedule(DayOfWeek.valueOf(sunday), LocalTime.of(sundayFrom, 0), LocalTime.of(sundayTo, 0));
                shop1.setTimesheet(timesheetShop1);
            });
            And("second shop opens {string} from {int} to {int}", (String days, Integer from, Integer to)->{
                Timesheet timesheetShop2 = new Timesheet();
                timesheetShop2.setDaysSchedule(strToArray(days), LocalTime.of(from, 0), LocalTime.of(to, 0));
                shop2.setTimesheet(timesheetShop2);
        });
        // employee
        And("first shop has {string} {string} {string}, second shop has {string} {string} {string}", 
        (String job1, String firstName1, String lastName1, String job2, String firstName2, String lastName2)->{
            employee1 = new Employee(shop1, firstName1, lastName1, job1 == "manager");
            employee2 = new Employee(shop2, firstName2, lastName2, job2 == "manager");
        });
        // analytics
        And("first shop analytics: {string} {int} {string} {int} {string} {int}", 
        (String recipe1, Integer qt1, String recipe2, Integer qt2, String recipe3, Integer qt3)->{
            shop1.addAnalytic(new Analytic(factory.getCookie(recipe1), qt1));
            shop1.addAnalytic(new Analytic(factory.getCookie(recipe2), qt2));
            shop1.addAnalytic(new Analytic(factory.getCookie(recipe3), qt3));

            factory.addAnalytic(new Analytic(factory.getCookie(recipe1), qt1));
            factory.addAnalytic(new Analytic(factory.getCookie(recipe2), qt2));
            factory.addAnalytic(new Analytic(factory.getCookie(recipe3), qt3));

            assertEquals(factory.getCookie(recipe1), shop1.getBestRecipe());
            assertEquals(shop1.getAnalytics().getSize(), 3);
            assertEquals(45, shop1.getAnalytics().getOneAnalytic(factory.getCookie(recipe1)).getCount());
            assertEquals(shop1.getAnalytics().getOneAnalytic(factory.getCookie(recipe1)).getCount(), qt1);
            
        });
        And("second shop analytics: {string} {int} {string} {int} {string} {int}", 
        (String recipe1, Integer qt1, String recipe2, Integer qt2, String recipe3, Integer qt3)->{

            shop2.addAnalytic(new Analytic(factory.getCookie(recipe1), qt1));
            shop2.addAnalytic(new Analytic(factory.getCookie(recipe2), qt2));
            shop2.addAnalytic(new Analytic(factory.getCookie(recipe3), qt3));

            factory.addAnalytic(new Analytic(factory.getCookie(recipe1), qt1));
            factory.addAnalytic(new Analytic(factory.getCookie(recipe2), qt2));
            factory.addAnalytic(new Analytic(factory.getCookie(recipe3), qt3));

            assertEquals(45, shop1.getAnalytics().getOneAnalytic(factory.getCookie(recipe1)).getCount());

            assertEquals(shop2.getAnalytics().getSize(), 3);
            assertEquals(55, factory.getAnalytics().getOneAnalytic(factory.getCookie(recipe1)).getCount());
            assertEquals(10, shop2.getAnalytics().getOneAnalytic(factory.getCookie(recipe1)).getCount());
            assertEquals(factory.getCookie(recipe1), factory.getBestRecipe());
            assertEquals(factory.getCookie(recipe2), shop2.getBestRecipe());

        });

        // customers
        And("a user of name {string} with email {string}", (String name, String email) ->{
            ursul = factory.addUser(new Guest(email), name);
        });
        And("a guest with email {string}", (String email)->{
            guest = new Guest(email);
        });
        And("a position {double}, {double}", (Double lat, Double lon)->{
            Position posGuest = new Position(lat, lon);
            guest.setPosition(posGuest);
        });

        /* ---- end Given --- */

        When("he orders {int} {string}", (Integer qt, String recipe)->{
            Recipe orderRecipe = factory.getCookie(recipe);
            OrderItem orderItem = new OrderItem(orderRecipe, qt);
            ArrayList<OrderItem> orderItems = new ArrayList<>();
            orderItems.add(orderItem);
            order1 = new Order(guest, shop1, orderItems);
            order1.setPickupDate(LocalDateTime.of(2021, 01, 14, 12, 55));
            factory.startCommand(order1);
            order1.setPrice(order1.calculatePrice());
            factory.payCommand(order1, order1.getPrice());
            shop1.cook(shop1.getShopValidateOrders().getCommandById(order1.getId()));
            order1.isReady();
        });
        And("the employee delivers the order", ()->{
            employee1.scans(order1.getId());
        });
        And("customer pays ${double}", (Double paid)->{
            assertEquals(order1.getPrice(), paid);
        });
        Then("the stock of the shop is DOUGH {string} {int}", 
        (String doughtype, Integer qt)->{
            Dough dough = new Dough(Dough.DoughType.valueOf(doughtype));
            StockIngredient currentStock = shop1.getStocksIngredient().get(dough);
            assertEquals((int) qt, currentStock.getQuantity());
            
        });
        And("the stock of the shop is FLAVOUR {string} {int}", 
        (String flavourType, Integer qt)->{
            Flavour flavour = new Flavour(Flavour.FlavourType.valueOf(flavourType));
            StockIngredient currentStock = shop1.getStocksIngredient().get(flavour);
            assertEquals((int) qt, currentStock.getQuantity());
        });
        And("the stock of the shop is TOPPING {string} {int} {string} {int}", 
        (String toppingType1, Integer qt1, String toppingType2, Integer qt2)->{
            Topping topping1 = new Topping(Topping.ToppingType.valueOf(toppingType1));
            Topping topping2 = new Topping(Topping.ToppingType.valueOf(toppingType2));
            StockIngredient currentStockTopping1 = shop1.getStocksIngredient().get(topping1);
            StockIngredient currentStockTopping2 = shop1.getStocksIngredient().get(topping2);
            assertEquals(currentStockTopping1.getQuantity(), qt1);
            assertEquals(currentStockTopping2.getQuantity(), qt2);
        });
        And("the shop analytics are {string} {int}", (String recipe, Integer qtOrdered)->{
            Recipe recipeToAnalyze = factory.getCookie(recipe);
            Analytic recipeAnalytic = shop1.getAnalytics().getOneAnalytic(recipeToAnalyze);
            assertEquals(recipeAnalytic.getCount(), qtOrdered);
        });
        And("the factory analytics are {string} {int}", (String recipe, Integer qtOrdered)->{
            Recipe recipeToAnalyze = factory.getCookie(recipe);
            Analytic recipeAnalytic = factory.getAnalytics().getOneAnalytic(recipeToAnalyze);
            assertEquals(recipeAnalytic.getCount(), qtOrdered);
        });

        When("{string} registers and subscribe to loyalty program", (String name)->{
            // register
            user = factory.addUser(guest, name);
            // subsribe
            user.setMember(true);
        });

        Then("there is an account with his information in the factory", ()->{
            assertTrue(factory.getUsers().contains(user));
        });

        And("he is a member and he has {int} cookie in his cookie pot", (Integer nbCookies)->{
            assertTrue(user.isMember());
            assertEquals(user.getCookiePot(), nbCookies);
        });

        When("user orders {int} {string}", (Integer qt, String recipe)->{
            Recipe orderRecipe = factory.getCookie(recipe);
            OrderItem orderItem = new OrderItem(orderRecipe, qt);
            ArrayList<OrderItem> orderItems = new ArrayList<>();
            orderItems.add(orderItem);
            order2 = new Order(user, shop1, orderItems);
            order2.setPickupDate(LocalDateTime.of(2021, 01, 14, 12, 55));
            factory.startCommand(order2);
            order2.setPrice(order2.calculatePrice());
            factory.payCommand(order2,  order2.getPrice());
            shop1.cook(shop1.getShopValidateOrders().getCommandById(order2.getId()));
            order2.isReady();
        });
        And("the employee scans this order", ()->{
            employee1.scans(order2.getId());
        });
        And("he has {int} cookies in his pot", (Integer nbCookiesInPot)->{
            assertEquals(user.getCookiePot(), nbCookiesInPot);
        });
        Then("Joel pays ${double}", (Double pricepaid)->{
            assertEquals(order2.getPrice(), pricepaid);
        });
        Then("he orders {int} cookies {string} dough {string}, flavour {string}, toppings {string}, {string}, cooking {string} and {string} with dose {int}",
        (Integer qt, String name, String dough, String flavour, String topping1, String topping2, String cooking, String mix, Integer dose)->{
        custom = new RecipeBuilder(Dough.DoughType.valueOf(dough), CookingType.valueOf(cooking))
                            .withFlavour(Flavour.FlavourType.valueOf(flavour))
                            .withMix(MixType.valueOf(mix))
                            .withTopping(Topping.ToppingType.valueOf(topping1))
                            .withTopping(Topping.ToppingType.valueOf(topping2))
                            .withDose(dose)
                            .withName(name)
                            .build();
        OrderItem orderItem = new OrderItem(custom, qt);
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        order3 = new Order(user, shop1, orderItems);
        order3.setPickupDate(LocalDateTime.of(2021, 01, 14, 12, 55));
        factory.startCommand(order3);
        order3.setPrice(order3.calculatePrice());
        factory.payCommand(order3, order3.getPrice());
        shop1.cook(shop1.getShopValidateOrders().getCommandById(order3.getId()));
        order3.isReady();
        employee1.scans(order3.getId());
        });
        And("the recipe {string} is {int} times in the analytics",(String recipe, Integer qtOrdered)->{
            Analytic recipeAnalytic = shop1.getAnalytics().getOneAnalytic(custom);
            assertEquals(recipeAnalytic.getCount(), qtOrdered);
        }); 
        And("he pays the amount of ${double}", (Double pricepaid)->{
            assertEquals(order3.getPrice(), pricepaid);
        });
        Then("the shop has a technical failure",()->{
            shop1.hasTechnicalFailure(true);
        });
        And("Joel wants to order cookies but he can't", ()->{
            OrderItem orderItem = mock(OrderItem.class);
            ArrayList<OrderItem> orderItems = new ArrayList<>();
            orderItems.add(orderItem);
            Order orderWillFail = new Order(user, shop1, orderItems);
            shopException = assertThrows(UnavailableShopException.class, ()->factory.startCommand(orderWillFail));
            
        });
        Then("he chooses another near shop with position {double}, {double}", (Double latitude, Double longitude)->{
            List<Shop> nearestShops = shopException.getFallBackShops();
            assertFalse(nearestShops.contains(shop1));
            assertTrue(nearestShops.contains(shop2));
            assertEquals(nearestShops.get(0), shop2);
            assertEquals(shop2.getPosition().getLatitude(),latitude );
            assertEquals(shop2.getPosition().getLongitude(), longitude);
        });
        Then("user wants to order {int} {string}", (Integer nbCookie, String recipe)->{
            Recipe orderRecipe = factory.getCookie(recipe);
            OrderItem orderItem = new OrderItem(orderRecipe, nbCookie);
            ArrayList<OrderItem> orderItems = new ArrayList<>();
            orderItems.add(orderItem);
            order2 = new Order(user, shop1, orderItems);
            order2.setPickupDate(LocalDateTime.of(2021, 01, 14, 12, 55));
        });
        And("shop hasn't enough stock", ()->{
            assertThrows(UnavailableShopException.class ,()->factory.startCommand(order2));
        });


        // Recipe customization
        When("the customer chooses the recipe {string}", (String recipeName) -> {
            Recipe recipeToCustomize = factory.getCookie(recipeName);
            customizer = RecipeBuilder.load(recipeToCustomize);
        });
        And("modifies the dose by {int}",(Integer dose) ->{
            customizer.withDose(dose);
        });
        And("replaces the {int}st topping with {string}", (Integer index, String topping) -> {
            customizer.withTopping(Topping.ToppingType.valueOf(topping), index - 1);
        });
        And("renames it {string}", (String newName) -> {
            customizer.withName(newName);
        });
        And("he orders {int} cookies with this customized recipe", (Integer qty) -> {
            Recipe orderRecipe = customizer.build();
            OrderItem orderItem = new OrderItem(orderRecipe, qty);
            ArrayList<OrderItem> orderItems = new ArrayList<>();
            orderItems.add(orderItem);
            order1 = new Order(guest, shop1, orderItems);
            order1.setPickupDate(LocalDateTime.of(2021, 01, 14, 12, 55));
            factory.startCommand(order1);
            order1.setPrice(order1.calculatePrice());
            factory.payCommand(order1,  order1.getPrice());
            shop1.cook(shop1.getShopValidateOrders().getCommandById(order1.getId()));
            order1.isReady();
        });
        Then("a new extended recipe is created", () -> {
        });

       
        

        And("a shop with a fee of {double}", (Double fee) -> {
            shop = new Shop(factory, fee);
        });

        And("another shop", () -> {
            anotherShop = new Shop(factory, 0);
        });
        And("the first shop has a technical failure", () -> {
            shop.hasTechnicalFailure(true);
        });
        When("he creates an order of {int} special cookies of a custom recipe with {string} dough, {string} flavour, {string} topping, {string} mix and {string} cooking",
        (Integer cookiesNb, String dough, String flavour, String topping, String mix, String cooking) -> {
            Recipe newRecipe = new RecipeBuilder(Dough.DoughType.valueOf(dough), CookingType.valueOf(cooking))
                    .withFlavour(Flavour.FlavourType.valueOf(flavour))
                    .withMix(MixType.valueOf(mix))
                    .withTopping(Topping.ToppingType.valueOf(topping))
                    .build();
            orderItem = new OrderItem(newRecipe, cookiesNb);
            ArrayList<OrderItem> orderItems = new ArrayList<>();
            orderItems.add(orderItem);
            order = new Order(ursul, shop, orderItems);
            order.setPickupDate(LocalDateTime.of(2020,5,26,10,0));
        });
        Then("the order cannot start", () -> {
            UnavailableShopException error = assertThrows(UnavailableShopException.class, () -> {
                factory.startCommand(order);
            });
            nearestShops = error.getFallBackShops();
        });
        When("he changes his order shop for the {int}th proposed", (Integer id) -> {
            Shop newShop = nearestShops.get(id);
            order.setShop(newShop);
            assertEquals(newShop, anotherShop);
        });
        Then("the order can start", () -> {
            assertDoesNotThrow(() -> {
                factory.startCommand(order);
            });
        });
        And("he is at the position {double} {double}", (Double lat, Double lon) -> {
            ursul.setPosition(new Position(lat.doubleValue(), lon.doubleValue()));
        });

        And("the shop is at the position {double} {double}", (Double lat, Double lon) -> {
            shop.setPosition(new Position(lat.doubleValue(), lon.doubleValue()));
        });
        And("a MarcelEat deliver", () -> {
            marcelEat = new MarcelEat();
        });
        And("he has an order of {int} cookies {string}", (Integer count, String name) -> {
            orderItem = new OrderItem(factory.getCookie(name), count);
            ArrayList<OrderItem> orderItems = new ArrayList<>();
            orderItems.add(orderItem);
            order = new Order(ursul, shop, orderItems);
            order.setPickupDate(LocalDateTime.now().plusMinutes(20));
        });
        When("he asks a last minute delivery {int} minutes before picked up time", (Integer min)->{
            order.setPickupDate(LocalDateTime.now().plusMinutes(min));
            assertTrue(order.asksForDelivery(marcelEat));
        });
        Then("he pays ${double} for delivery", (Double deliveryFee) -> {
            assertEquals(deliveryFee, order.getDeliveryFee(), 0.01);
        });
        When("he creates an order of {int} cookies {string}", (Integer cookiesNb, String cookieName) -> {
            orderItem = new OrderItem(factory.getCookie(cookieName), cookiesNb);
            ArrayList<OrderItem> orderItems = new ArrayList<>();
            orderItems.add(orderItem);
            order = new Order(ursul, shop, orderItems);
            order.setPickupDate(LocalDateTime.of(2020,5,26,10,0));

        });
        Then("he can't be delivered because he is too far", () -> {
            assertFalse(order.asksForDelivery(marcelEat));
        });
        Then("he pays ${double} plus ${double} for delivery", (Double normalPrice, Double deliveryFee) -> {
            assertEquals(deliveryFee.doubleValue()+normalPrice.doubleValue(), order.calculatePrice(), 0.01);
        });
        And("he wants his order to be delivered in {int} hour", (Integer hour) -> {
            order.setPickupDate(LocalDateTime.now().plusHours(1));
            assertTrue(order.asksForDelivery(marcelEat));
        });

    }

    private static ArrayList<DayOfWeek> strToArray(String daysStr) {
        ArrayList<DayOfWeek> days = new ArrayList<>();
        String[] daysSplitted = daysStr.split(",");
        for (String s : daysSplitted) {
            days.add(DayOfWeek.valueOf(s));
        }
        return days;
    }


}
