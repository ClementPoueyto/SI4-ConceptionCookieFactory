package fr.unice.polytech.exception;

public class RoleEmployeeException extends RuntimeException{



    /**
     * 
     * @param message
     * Exception raised when an employee doesn't have rights to do actions
     * For example: an employee who isn't manager can't update shop's timesheet
     */
    public RoleEmployeeException(String message) {
        super(message);

    }
}
