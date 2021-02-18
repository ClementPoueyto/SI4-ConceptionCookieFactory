package fr.unice.polytech.customer;

import fr.unice.polytech.tools.Position;
import fr.unice.polytech.order.Invoker;
import fr.unice.polytech.order.OrderStatus;
import fr.unice.polytech.order.command.CommandValidateOrder;

public abstract class Customer extends Invoker {
    private String email;
    protected boolean isRegistered;
    protected boolean isMember;
    private Position position;
    
    public abstract int getCookiePot();
    public abstract boolean subscribeToLoyaltyProgram();
    public abstract boolean applyLoyaltyProgram(int cookies);

    
    Customer(String email, Position position){
        this.email = email;
        this.position = position;
        this.isMember=false;
    }
    
    /**
     * 
     * @return status command if the command is not null
     */
    public OrderStatus getStatusCommand(){
        if(getCommand()!=null){
            CommandValidateOrder command = (CommandValidateOrder) getCommand();
            return command.getOrder().getOrderStatus();
        }

        return null;
    }

    /**
     * 
     * @return isRegistered
     */
    public boolean isRegistered(){
        return isRegistered;
    }

    /**
     * 
     * @return isMember
     */
    public boolean isMember(){
        return isMember;
    }

    
    /**
     * 
     * @return position
     * position is an object with attribute latitude and longitude
     */
    public Position getPosition() {
        return position;
    }

    /**
     * 
     * @param position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * 
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }


}
