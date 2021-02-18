package fr.unice.polytech.exception;

public class InvalidRecipeException extends RuntimeException {



    /**
     * 
     * @param message
     * Exception raised when building a new recipe. For exemple when there is too many
     * toppings or if the dose is too big or too small or when there are errors when access some parameters (i.e out of range when running through toppings)
     */
    public InvalidRecipeException(String message) {
        super(message);

    }

}
