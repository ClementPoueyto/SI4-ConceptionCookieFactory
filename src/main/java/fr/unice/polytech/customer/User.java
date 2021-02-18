package fr.unice.polytech.customer;

import fr.unice.polytech.factory.FactoryFacade;
import fr.unice.polytech.tools.Position;


public class User extends Customer {
    private long id;
    private String name;
    private int cookiePot;

    public User(String name, String email, Position posCustomer) {
        super(email, posCustomer);
        this.id = FactoryFacade.userCount++;
        this.name = name;
        this.cookiePot = 0;
        this.isMember = false;
        this.isRegistered = true;
    }

    public User(String name, String email) {
        this(name, email, new Position(0, 0));
    }


    public void setId(int id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public int getCookiePot() {
        return cookiePot;
    }

    public void setCookiePot(int cookiePot) {
        this.cookiePot = cookiePot;
    }

    public void addToCookiePot(int n) {
        this.cookiePot += n;
    }

    public void setMember(boolean member) {
        isMember = member;
    }

    /**
     * @return boolean
     * if the user is already registered as a loyal member it returns false (we fail to apply loyalty program to him)
     * else set isMember to true
     */
    @Override
    public boolean subscribeToLoyaltyProgram() {
        if (this.isMember) {
            return false;
        }
        this.setMember(true);;
        return true;
    }

    
    /**
     * @param cookies (int)
     * @return boolean
     * if the user is member, update his cookie pot with @param [cookie]
     * and then return the status of member of the user. So if he isn't member, 
     * his cookie pot is not updated and the method return false
     */
    @Override
    public boolean applyLoyaltyProgram(int cookies) {
        if(this.isMember) this.addToCookiePot(cookies);
        return this.isMember;
    }


}
