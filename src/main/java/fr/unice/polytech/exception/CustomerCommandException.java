package fr.unice.polytech.exception;

import fr.unice.polytech.customer.Customer;
import fr.unice.polytech.order.command.Command;

public class CustomerCommandException extends RuntimeException{



    private Command command;

    /**
     * 
     * @param message
     * @param customer
     *      Exception raised in case of the customer has already an order in preparation
     *      A customer can't make more than one order at the same time
     */
    public CustomerCommandException(String message, Customer customer){
        super(message);
        if(customer.getCommand()!=null) {
            this.command = customer.getCommand();
        }
    }

    public Command getCurrentCommand(){
        return command;
    }


}
