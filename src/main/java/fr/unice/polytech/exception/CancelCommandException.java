package fr.unice.polytech.exception;


public class CancelCommandException extends RuntimeException{

    /**
     * 
     * @param message 
     *      Exception raised in case of use of a Command that has not the
     *      expected status If the command is cancelled, it doesn't have
     *      any status
     */
    public CancelCommandException(String message) {
        super(message);
    }
}
