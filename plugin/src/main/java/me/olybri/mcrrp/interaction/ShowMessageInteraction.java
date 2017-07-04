package me.olybri.mcrrp.interaction;// Created by Loris Witschard on 7/4/2017.

import me.olybri.mcrrp.Database;
import me.olybri.mcrrp.Message;
import me.olybri.mcrrp.Tr;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowMessageInteraction extends PlayerInteraction
{
    private String message;
    
    public ShowMessageInteraction(String message)
    {
        this.message = message;
    }
    
    @Override
    protected void run(Player player, Player target) throws SQLException
    {
        ResultSet citizen = Database.citizen(player);
        String name = citizen.getString("first_name") + " " + citizen.getString("last_name");
    
        String title = "{name:" + name + "} " + Tr.s("to") + " {name:" + Tr.s("you") + "}:";
        new Message(title, message).send(target);
    
        title = "{name:" + Tr.s("You") + "} " + Tr.s("showed") + ":";
        new Message(title, message).send(player);
    }
}
