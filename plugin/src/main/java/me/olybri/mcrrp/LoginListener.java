package me.olybri.mcrrp;// Created by Loris Witschard on 6/11/2017.

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginListener implements Listener
{
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) throws SQLException
    {
        if(Database.citizen(event.getPlayer(), false) == null)
        {
            String msg = Tr.s("You first need to register at") + " http://olybri.me";
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, msg);
        }
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException
    {
        event.setJoinMessage(null);
        
        Player player = event.getPlayer();
        ResultSet citizen = Database.citizen(player);
        if(citizen == null)
            return;
        
        String name = citizen.getString("first_name") + " " + citizen.getString("last_name");
        new Message(Tr.s("Welcome") + ", {name:" + name + "}").send(player);
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        event.setQuitMessage(null);
    }
}
