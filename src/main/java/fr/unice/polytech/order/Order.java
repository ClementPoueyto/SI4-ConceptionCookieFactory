package fr.unice.polytech.order;

import fr.unice.polytech.customer.Customer;
import fr.unice.polytech.factory.FactoryFacade;
import fr.unice.polytech.marceleat.MarcelEat;
import fr.unice.polytech.shop.Shop;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order extends ArrayList<OrderItem>{
    

    private long id; // id = current date in millis
    private Customer customer;
    private Shop shop;
    private LocalDateTime pickupDate;
    private double price;

    private OrderStatus orderStatus;
    private double deliveryFee = 0.0;

    public Order(Customer customer, Shop shop, List<OrderItem> orderItems) {
        this(customer, shop, orderItems, null);
    }

    public Order(Customer customer, Shop shop, List<OrderItem> orderItems, LocalDateTime pickupDate) {
        super(orderItems);
        this.id = FactoryFacade.orderCount++;
        this.customer = customer;
        this.shop = shop;
        this.pickupDate = pickupDate;
    }

    public String toString(){
        String res = "The order is : \n";
        for (OrderItem item : this){
            res += "- "+item.getCount()+ " " + item.getCookie().getName() + "\n";
            res += item.getCookie().toString() +"\n\n";
        }
        return res;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public double getPrice(){
        return this.price;
    }


    /**
     * Price calculation method (see details along the method)
     * Customization fee
     * Shop fee
     * Delivery fee
     * BestOf fee
     * CookiePot extra cookie free
     * @return
     */
    public double calculatePrice() {
        double total = 0;
        for (OrderItem oi : this) {
            double itemPrice = 0;
            double bestOfFee = 1;
            double customizationFee = 1;
            if(!shop.getFactory().isBuiltInRecipe(oi.getCookie())){
                customizationFee = 1.25;
            }
            // get a discout if the cookie is best of of the shop or at national scale            
            if(shop.getAnalytics().getSize() != 0 && (oi.getCookie().equals(shop.getFactory().getBestRecipe()) || oi.getCookie().equals(shop.getBestRecipe()))){
                bestOfFee = 0.9;
            }

            itemPrice += oi.getCount() * oi.getCookie().getPriceExclTaxes(); // Excl prices of recipe
            itemPrice *= customizationFee; // Excl prices + customization
            itemPrice *= 1 + shop.getFee(); // All taxes included
            itemPrice *= bestOfFee; // All taxes included with reduc
            total += itemPrice;
        }
        if(customer.getCookiePot() >= 30) {
            total -= total * 0.1;
        }
        // round result to 2 decimals (ex : 5.25 and not 5.2467)
        total = total * 100;
        total = Math.round(total);
        total = total/100;
        if(orderStatus != OrderStatus.VALIDATED) total += this.deliveryFee;
        return total;
    }

    /**
     * Make a call on MarcelEat to ask for delivery and set deliveryFee if delivery is accepted
     * @param mE
     * @return true if the command could be delivered, else false
     */
    public boolean asksForDelivery(MarcelEat mE){
        deliveryFee = mE.askDelivery(this.customer.getPosition().distance(this.shop.getPosition()), this.pickupDate);
        return deliveryFee > 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Order)) return false;
        Order order = (Order) obj;
        return this.id == order.id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public LocalDateTime getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDateTime pickupDate) {
        this.pickupDate = pickupDate;
    }

    public boolean isReady() {
        if(orderStatus==OrderStatus.VALIDATED){
            this.customer.applyLoyaltyProgram(getNumberOfCookies());
        }
        return false;
    }

    public void setReady() {
        orderStatus = OrderStatus.PREPARED;
    }

    public boolean isClosed() {
        return pickupDate != null;
    }

    public int getNumberOfCookies(){
        int total=0;
        for(OrderItem oI : this){
            total += oI.count;
        }
        return total;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

}
