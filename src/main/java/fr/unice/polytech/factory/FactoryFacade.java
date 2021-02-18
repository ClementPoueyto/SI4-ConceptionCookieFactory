package fr.unice.polytech.factory;

import fr.unice.polytech.customer.Guest;
import fr.unice.polytech.customer.User;
import fr.unice.polytech.exception.UnavailableShopException;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.order.command.Command;
import fr.unice.polytech.order.command.CommandPlaceOrder;
import fr.unice.polytech.recipe.Recipe;
import fr.unice.polytech.recipe.RecipeBuilder;
import fr.unice.polytech.shop.Shop;
import fr.unice.polytech.tools.Analytic;
import fr.unice.polytech.tools.Analytics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FactoryFacade {
    public static int userCount = 0;
    public static int orderCount = 0;
    private ArrayList<Recipe> cookies;
    private ArrayList<Recipe> famousRecipe;
    private ArrayList<Shop> shops;
    private ArrayList<User> users;
    private Analytics analytics;

    public FactoryFacade() {
        this.cookies = new ArrayList<>();
        this.famousRecipe = new ArrayList<>();
        this.users = new ArrayList<>();
        this.shops = new ArrayList<>();
        this.analytics = new Analytics();
        addMainCookiesToRecipe();
    }

    /**
     * 
     * @param s Shop
     * @return List<Shop>
     * sort shops in factory according to distance between shop given in parameter and the other ones.
     */
    public List<Shop> getNearestShops(Shop s){
        ArrayList<Shop> shopToSort = new ArrayList<>(shops);
        shopToSort.sort(Comparator.comparingDouble(s2 -> s.getPosition().distance(s2.getPosition())));
        shopToSort.remove(s);
        return shopToSort;
    }

    /**
     * 
     * @param order
     * @throws UnavailableShopException
     * execute an order if the shop has no technical failure
     */
    public void startCommand(Order order) throws UnavailableShopException {
        if(!order.getShop().hasTechnicalFailure()) {
            CommandPlaceOrder commandPlaceOrder = new CommandPlaceOrder(order);
            commandPlaceOrder.execute();
        }else{
            throw new UnavailableShopException("Woosh! This Shop has a burning furnace!", order.getShop());
        }
    }

    /**
     * 
     * @param order
     * @param amount
     * @throws UnavailableShopException
     * execute the order if the amout paid is sufficent. And add the content of the order to analytics
     * now because the order is confirmed
     */
    public void payCommand(Order order, double amount) throws UnavailableShopException {
        if(order.calculatePrice()<=amount){
            order.getCustomer().getCommand().execute();
            analytics.update(order);
        }
    }

    /**
     * 
     * @param command
     * @throws UnavailableShopException
     */
    public void cancelCommand(Command command) throws UnavailableShopException {
        command.cancel();
    }


    /**
     * Creates and adds famous cookies to the factory
     */
    private void addMainCookiesToRecipe() {
        Recipe chocolalala = RecipeBuilder.prepareCHOCOLALALA();
        Recipe sooChoco = RecipeBuilder.prepareSOOCHOCOLATE();
        Recipe darkTempt = RecipeBuilder.prepareDARKTEMPTATION();
        addFamousRecipe(chocolalala);
        addFamousRecipe(sooChoco);
        addFamousRecipe(darkTempt);

    }

    /**
     * 
     * @param recipe
     * adds the famous recipe to the base and add them in a special list to
     * ease the analytics
     */
    public void addFamousRecipe(Recipe recipe){
        this.addRecipe(recipe);
        this.famousRecipe.add(recipe);
    }


    /**
     * 
     * @param recipe
     * add recipe to list of recipes known by the factory
     */
    public void addRecipe(Recipe recipe) {
        cookies.add(recipe);
    }

    /**
     * 
     * @param guest
     * @param name
     * @return User
     * add user to factory base based an already existing guest
     */
    public User addUser(Guest guest, String name){ 
        if(findUser(guest.getEmail()) == null){
            User newUser = new User(name, guest.getEmail());
            users.add(newUser);
            return newUser;
        }
        return null;
    }

    /**
     * 
     * @param user
     * add a new user to the base
     */
    public void addUser(User user) {
        users.add(user);
    }

    public void addShop(Shop shop) {
        shops.add(shop);
    }

    public User findUser(String email) {
        for (User c : users) {
            if (c.getEmail().equals(email)) {
                return c;
            }
        }
        return null;
    }

    public Recipe getCookie(String name) {
        for(Recipe c : cookies){
            if(c.getName().equals(name)){
                return c;
            }
        }
        return null;
    }

    /**
     * 
     * @param recipe
     * @return
     * return true if the recipe is already registered in factory base
     */
    public boolean isBuiltInRecipe(Recipe recipe){
        for(Recipe r : this.cookies){
            if(recipe.equals(r)){
                return true;
            }
        }
        return false;
    }

    public List<Recipe> getCookies() {
        return cookies;
    }

    public List<Recipe> getFamousCookies() {
        return this.famousRecipe;
    }

    public void setCookies(ArrayList<Recipe> cookies) {
        this.cookies = cookies;
    }

    public List<Shop> getShops() {
        return shops;
    }

    public void setShops(ArrayList<Shop> shops) {
        this.shops = shops;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }



    public Analytics getAnalytics(){
        return this.analytics;
    }

    public void addAnalytic(Analytic analytic) {
        this.analytics.add(analytic);
    }

    public String analyticsToString(){
        String res = "";
        res += "--- Factory analytics ---";
        res += this.analytics.toString();
        return res;
    }

    public Recipe getBestRecipe(){
        Recipe bestOf; 
        bestOf = this.analytics.getBest();
        return bestOf;
    }

    public Recipe getWorstRecipe(){
        Recipe worst;
        worst = this.analytics.getWorst();
        return worst;
    }

    public void removeWorstRecipe(){
        Recipe recipeToRemove = getWorstRecipe();
        // if recipeToRemove isn't a famous one (chocolalala...)
        if(!famousRecipe.contains(recipeToRemove)){
            // if recipeToRemove doesn't exist in the base, it'll do nothing (no error neither)
            this.cookies.remove(recipeToRemove);
        }
    }

    /**
     * method called when the manager wants to update the best recipe of the month
     * and delete the worst one
     */
    public void refresh(){
        Recipe best = getBestRecipe();
        addRecipe(best);
        removeWorstRecipe();
    }

    public void setAnalytics(Analytics analytics) {
        this.analytics = analytics;
    }
}
