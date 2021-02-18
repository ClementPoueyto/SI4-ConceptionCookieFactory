package fr.unice.polytech.stepdefs;

import fr.unice.polytech.factory.FactoryFacade;
import fr.unice.polytech.recipe.CookingType;
import fr.unice.polytech.recipe.MixType;
import fr.unice.polytech.recipe.Recipe;
import fr.unice.polytech.recipe.RecipeBuilder;
import fr.unice.polytech.recipe.item.Dough;
import fr.unice.polytech.recipe.item.Flavour;
import fr.unice.polytech.recipe.item.Topping;
import io.cucumber.java8.En;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(value = Cucumber.class)
@CucumberOptions(plugin = {"pretty"}, features = "src/test/resources/features")
public class AddNewRecipeStepdefs implements En {
    FactoryFacade factory = new FactoryFacade();
    Recipe newRecipe;

    public AddNewRecipeStepdefs(){

        Given("An admin", () -> {
        });

        // one topping
        When("He adds a new recipe named {string} with {string} dough, {string} flavour, {string} topping, {string} mix and {string} cooking",
        (String name, String dough, String flavour, String topping1, String mix, String cooking) -> {
            newRecipe = new RecipeBuilder(Dough.DoughType.valueOf(dough), CookingType.valueOf(cooking))
            .withFlavour(Flavour.FlavourType.valueOf(flavour))
            .withMix(MixType.valueOf(mix))
            .withName(name)
            .withTopping(Topping.ToppingType.valueOf(topping1))
            .build();
            factory.addRecipe(newRecipe);
        });

        // two toppings
        When("He adds a new recipe named {string} with {string} dough, {string} flavour, {string}, {string} topping, {string} mix and {string} cooking", 
        (String name, String dough, String flavour, String topping1, String topping2, String mix, String cooking) -> {
            newRecipe = new RecipeBuilder(Dough.DoughType.valueOf(dough), CookingType.valueOf(cooking))
            .withFlavour(Flavour.FlavourType.valueOf(flavour))
            .withMix(MixType.valueOf(mix))
            .withName(name)
            .withTopping(Topping.ToppingType.valueOf(topping1))
            .withTopping(Topping.ToppingType.valueOf(topping2))
            .build();
            factory.addRecipe(newRecipe);
        });

        // three toppings
        When("He adds a new recipe named {string} with {string} dough, {string} flavour, {string}, {string}, {string} topping, {string} mix and {string} cooking", 
        (String name, String dough, String flavour, String topping1, String topping2, String topping3, String mix, String cooking) -> {
            newRecipe = new RecipeBuilder(Dough.DoughType.valueOf(dough), CookingType.valueOf(cooking))
            .withFlavour(Flavour.FlavourType.valueOf(flavour))
            .withMix(MixType.valueOf(mix))
            .withName(name)
            .withTopping(Topping.ToppingType.valueOf(topping1))
            .withTopping(Topping.ToppingType.valueOf(topping2))
            .withTopping(Topping.ToppingType.valueOf(topping3))
            .build();
            factory.addRecipe(newRecipe);
        });

        Then("The recipe {string} is added to the website", 
            (String name) -> {
                assertEquals(factory.getCookie(name), newRecipe);
            }
        );
    }

}