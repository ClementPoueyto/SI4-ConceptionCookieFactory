package fr.unice.polytech.order;


import fr.unice.polytech.order.command.Command;

public abstract class Invoker {

    //Name of Waiter, Manager etc
    private String name;

    //Command to invoke
    private Command command;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }
}
