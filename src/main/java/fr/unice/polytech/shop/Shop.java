package fr.unice.polytech.shop;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.unice.polytech.tools.Analytic;
import fr.unice.polytech.tools.Analytics;
import fr.unice.polytech.tools.Position;
import fr.unice.polytech.factory.Employee;
import fr.unice.polytech.factory.FactoryFacade;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.order.command.CommandPrepareOrder;
import fr.unice.polytech.order.command.CommandValidateOrder;
import fr.unice.polytech.recipe.Recipe;
import fr.unice.polytech.shop.stocks.StocksIngredient;
import fr.unice.polytech.shop.timesheet.Timesheet;

public class Shop {
    private FactoryFacade factory;
    private double fee;
    private Timesheet timesheet;
    private Position position;
    private int countOrders = 0; // counter of all orders passed to the shop

    Analytics analytics = new Analytics();
    private StocksIngredient stocksIngredient = new StocksIngredient(100);
    private ShopValidateOrders shopValidateOrders = new ShopValidateOrders();
    private ShopPrepareOrders shopPrepareOrders = new ShopPrepareOrders().addObservers(Arrays.asList(new ShopOrdersObserver[]{this.analytics, this.stocksIngredient}));
    private List<Employee> employees = new ArrayList<>();
    private boolean hasTechnicalFailure = false;

    public Shop(FactoryFacade factory, double fee, Timesheet timesheet, Position position) {
        this.factory = factory;
        this.fee = fee;
        this.timesheet = timesheet;
        this.position = position;
        factory.addShop(this);
    }

    public void setHasTechnicalFailure(boolean hasTechnicalFailure) {
        this.hasTechnicalFailure = hasTechnicalFailure;
    }

    public Shop(FactoryFacade factory, double fee){
        this.factory = factory;
        this.fee = fee;
        this.timesheet=initTimesheet();
        this.position = new Position(0, 0);
        factory.addShop(this);

    }

    public Shop(FactoryFacade factory,Position posShop) {
        this.factory = factory;
        this.position = posShop;
        this.timesheet=initTimesheet();
        this.fee=0;
        factory.addShop(this);

    }

    public Shop(FactoryFacade factory){
        this.factory = factory;
        this.timesheet=initTimesheet();
        factory.addShop(this);
    }

    /**
     * Launch the preparation of the order
     * @param order
     */
    public void cook(CommandValidateOrder order){
        CommandPrepareOrder cpo = new CommandPrepareOrder(order.getOrder());
        cpo.execute();
        this.shopPrepareOrders.add(cpo);
        this.shopValidateOrders.remove(order);
    }

    /**
     * Create the Timesheet of the current shop
     * @return a new TimeSheet
     */
    private Timesheet initTimesheet(){
        Timesheet iniTimesheet = new Timesheet();
        iniTimesheet.setDaysSchedule(Arrays.asList(DayOfWeek.values()), LocalTime.of(9,0), LocalTime.of(18,0));
        return iniTimesheet;
    }

    //

    public List<Employee> getEmployees() {
        return employees;
    }

    public Employee getEmployee(String firstName, String lastName) {
        for(Employee e : employees) {
            if(e.getFirstName().equals(firstName) && e.getLastName().equals(lastName)) return e;
        }
        return null;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public void addEmployee(Employee employee){
        this.employees.add(employee);
    }

    public Timesheet getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(Timesheet timesheet) {
        this.timesheet = timesheet;
    }



    public Analytics getAnalytics() {
        return analytics;
    }

    public void setAnalytics(Analytics analytics) {
        this.analytics = analytics;
    }

    public void addAnalytic(Analytic analytic) {
        this.analytics.add(analytic);
    }

    public String analyticsToString(){
        String res = "";
        res += "--- Shop analytics ---";
        res += this.analytics.toString();
        return res;
    }


    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }



    /**
     * @return true if shop has a technical failure, else false
     */
    public boolean hasTechnicalFailure(){
        return this.hasTechnicalFailure;
    }

    public void hasTechnicalFailure(boolean toggle){
        this.hasTechnicalFailure = toggle;
    }

    public FactoryFacade getFactory() {
        return factory;
    }



    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public void addPrepareOrder(CommandPrepareOrder order){
        shopPrepareOrders.add(order);
    }
    public void addValidateOrder(CommandValidateOrder order){
        this.countOrders++;
        shopValidateOrders.add(order);
    }


    public CommandValidateOrder getValidateCommandById(long id){
        return this.shopValidateOrders.getCommandById(id);
    }

    public CommandPrepareOrder getPrepareCommandById(long id){
        return this.shopPrepareOrders.getCommandById(id);
    }

    public Recipe getBestRecipe(){
        return this.analytics.getBest();
    }

    public StocksIngredient getStocksIngredient() {
        return stocksIngredient;
    }

    /**
     * @param order
     * @return true if there is enough stock for the order, else a StockException is thrown 
     */
    public boolean enoughStock(Order order){
        return this.stocksIngredient.enoughStock(order);
    }

    public ShopValidateOrders getShopValidateOrders() {
        return shopValidateOrders;
    }

    public ShopPrepareOrders getShopPrepareOrders() {
        return shopPrepareOrders;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "factory=" + factory +
                ", fee=" + fee +
                ", timesheet=" + timesheet +
                ", position=" + position +
                ", countOrders=" + countOrders +
                ", validateOrders=" + shopValidateOrders +
                ", preparedOrders=" + shopPrepareOrders +
                ", analytics=" + analytics +
                ", stocksIngredient=" + stocksIngredient +
                ", employees=" + employees +
                '}';
    }
}
