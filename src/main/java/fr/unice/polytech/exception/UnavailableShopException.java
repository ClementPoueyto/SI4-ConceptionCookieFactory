package fr.unice.polytech.exception;

import java.util.List;

import fr.unice.polytech.shop.Shop;

public class UnavailableShopException extends Exception {


    private Shop shop;

    /**
     * 
     * @param message
     * @param shop
     * Exception raised when a shop has a technical failure, stock program or 
     * bad date/hour of pickup for an order
     */
    public UnavailableShopException(String message, Shop shop){
        super(message);
        this.shop = shop;
    }
    
    /**
     * 
     * @return List<Shop>
     * called on an object of UnavailableShopException it returns a list of shop near
     * the position of the @param [this.shop] 
     */
    public List<Shop> getFallBackShops(){
        return this.shop.getFactory().getNearestShops(this.shop);
    }

}
