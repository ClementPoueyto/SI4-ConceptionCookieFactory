package fr.unice.polytech.exception;

public class StockException extends RuntimeException {


    /**
     * 
     * @param message
     * Exception raised when there is an error with stock, for example is there 
     * is not enough stock for a command
     */
    public StockException(String message) {
        super(message);

    }
}
