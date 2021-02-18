package fr.unice.polytech.order.command;

import fr.unice.polytech.exception.UnavailableShopException;

public interface Command
{
    public void execute() throws UnavailableShopException;

    public void cancel();


}