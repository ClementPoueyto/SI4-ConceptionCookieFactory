package fr.unice.polytech.stepdefs;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;

import fr.unice.polytech.exception.RoleEmployeeException;
import fr.unice.polytech.recipe.CookingType;
import fr.unice.polytech.recipe.MixType;
import fr.unice.polytech.recipe.Recipe;
import fr.unice.polytech.recipe.RecipeBuilder;
import fr.unice.polytech.recipe.item.Dough;
import fr.unice.polytech.recipe.item.Flavour;
import fr.unice.polytech.recipe.item.Topping;
import fr.unice.polytech.tools.Analytic;
import org.junit.runner.RunWith;

import fr.unice.polytech.factory.Employee;
import fr.unice.polytech.factory.FactoryFacade;
import fr.unice.polytech.shop.Shop;
import fr.unice.polytech.shop.timesheet.Timesheet;
import io.cucumber.java8.En;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(value = Cucumber.class)
@CucumberOptions(plugin = {"pretty"}, features = "src/test/resources/features")

public class ShopManagingStepdefs implements En {
    FactoryFacade factory;
    Shop shop;
    Employee manager;

    public ShopManagingStepdefs() {
        Given("a shop with these analytics: {string} {int} {string} {int} {string} {int}",
                (String recipe1, Integer qt1, String recipe2, Integer qt2, String recipe3, Integer qt3) -> {
                    factory = new FactoryFacade();
                    shop = new Shop(factory);

                    shop.addAnalytic(new Analytic(factory.getCookie(recipe1), qt1));
                    shop.addAnalytic(new Analytic(factory.getCookie(recipe2), qt2));
                    shop.addAnalytic(new Analytic(factory.getCookie(recipe3), qt3));
                });
        And("Also in the analytics the following {string} recipe dough {string}, flavour {string}, toppings {string}, {string}, cooking {string} and {string} ordered {int} times",
                (String customName, String dough, String flavour, String topping1, String topping2, String cooking, String mix, Integer count) -> {
                    Recipe custom = new RecipeBuilder(Dough.DoughType.valueOf(dough), CookingType.valueOf(cooking))
                            .withFlavour(Flavour.FlavourType.valueOf(flavour))
                            .withMix(MixType.valueOf(mix))
                            .withTopping(Topping.ToppingType.valueOf(topping1))
                            .withTopping(Topping.ToppingType.valueOf(topping2))
                            .withName(customName)
                            .build();
                    shop.addAnalytic(new Analytic(custom, count));

                });
        And("the shop timetable : {string} from {int} to {int}",
                (String days, Integer from, Integer to) -> {
                    Timesheet timesheet = new Timesheet();
                    timesheet.setDaysSchedule(strToArray(days), LocalTime.of(from, 0), LocalTime.of(to, 0));
                    shop.setTimesheet(timesheet);
                });
        And("a manager named {string} {string}",
                (String firstName, String lastName) -> {
                    manager = new Employee(shop, firstName, lastName, true);
                }
        );

        When("The manager wants to check the stats of his shop", () -> {

        });

        Then("He sees that {int} cookies have been sold", (Integer nbSold) -> {
            int i = 0;
            for(Analytic a : shop.getAnalytics().getAnalytics()) {
                i += a.getCount();
            }
            assertEquals(nbSold, i);
        });

        Then("He sees that the best-seller is the {string}", (String cookieStr) -> {
            assertEquals(factory.getCookie(cookieStr), shop.getAnalytics().getBest());
        });

        Then("He sees that there's no technical failure", () -> {
            assertFalse(shop.hasTechnicalFailure());
        });

        When("{string} {string} changes the timesheet of {string} to : from {int} to {int}",
                (String firstName, String lastName, String days, Integer from, Integer to) -> {
                    Employee employee = shop.getEmployee(firstName, lastName);
                    Timesheet timesheet = new Timesheet(shop.getTimesheet().getDaySchedules());
                    timesheet.setDaysSchedule(strToArray(days), LocalTime.of(from, 0), LocalTime.of(to, 0));
                    if(!employee.isManager()) {
                        assertThrows(RoleEmployeeException.class, () -> {
                            employee.setShopTimeSheet(timesheet);
                        });
                    }
                    else {
                        employee.setShopTimeSheet(timesheet);
                    }

                }
        );
        Then("the shop has the correct timetable : {string} from {int} to {int}, {string} from {int} to {int}",
                (String days1Str, Integer days1From, Integer days1To, String days2Str, Integer days2From, Integer days2To) -> {
                    for (DayOfWeek d : strToArray(days1Str)) {
                        assertEquals(LocalTime.of(days1From, 0), shop.getTimesheet().getDaySchedule(d).getFrom());
                        assertEquals(LocalTime.of(days1To, 0), shop.getTimesheet().getDaySchedule(d).getTo());
                    }
                    for (DayOfWeek d : strToArray(days2Str)) {
                        assertEquals(LocalTime.of(days2From, 0), shop.getTimesheet().getDaySchedule(d).getFrom());
                        assertEquals(LocalTime.of(days2To, 0), shop.getTimesheet().getDaySchedule(d).getTo());
                    }
        }
        );
        When("the manager hire the employee {string} {string}", (String firstName, String lastName) -> {
            assertEquals(1, shop.getEmployees().size());
            new Employee(shop, firstName, lastName, false);
            assertEquals(2, shop.getEmployees().size());
        });
        Then("{string} {string} is an employee of the shop", (String firstName, String lastName) -> {
            assertNotNull(shop.getEmployee(firstName, lastName));
        });

        Then("the shop timetable hasn't changed, because he is not a manager", () -> {

        });
        Then("the shop has the correct timetable : {string} from {int} to {int}",
                (String days, Integer from, Integer to) -> {
                    for (DayOfWeek d : strToArray(days)) {
                        assertEquals(LocalTime.of(from, 0), shop.getTimesheet().getDaySchedule(d).getFrom());
                        assertEquals(LocalTime.of(to, 0), shop.getTimesheet().getDaySchedule(d).getTo());
                    }
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
