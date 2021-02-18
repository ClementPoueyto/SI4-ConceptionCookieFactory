package fr.unice.polytech.customer;

import fr.unice.polytech.tools.Position;

public class Guest extends Customer {


    public Guest(String email, Position position) {
        super(email, position);
        this.isRegistered = false;
    }

    public Guest(String email) {
        this(email, new Position(0, 0));
    }

    /**
     * @return false
     * a guest can't be registered to loyalty program, so this method always return false
     */
    @Override
    public boolean subscribeToLoyaltyProgram() {
        return false;
    }

    /**
     * @return false
     * we can't apply loyalty program to a guest, so return false to indicate that he is not a member
     */
    @Override
    public boolean applyLoyaltyProgram(int cookies) {
        return false;
    }
    
    /**
     * @return )
     * guest doesn't have a cookie pot so it'll return 0
     */
    @Override
    public int getCookiePot() {
        return 0;
    }


}
