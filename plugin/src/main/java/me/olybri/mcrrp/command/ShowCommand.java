package me.olybri.mcrrp.command;// Created by Loris Witschard on 7/6/2017.

import me.olybri.mcrrp.MCRRP;
import me.olybri.mcrrp.Message;
import me.olybri.mcrrp.Tr;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

public class ShowCommand extends PlayerCommand
{
    public ShowCommand()
    {
        super(1, false);
    }
    
    @Override
    protected boolean run(ResultSet citizen, List<String> args) throws Exception
    {
        String name = args.remove(0);
        PluginCommand command = MCRRP.command(name);
        
        if(command == null || !(command.getExecutor() instanceof PlayerCommand))
        {
            setMessage(new Message(Tr.s("Cannot show unknown command") + ": {value:" + name + "}"));
            return true;
        }
        
        PlayerCommand executor = (PlayerCommand) command.getExecutor();
        Player player = Bukkit.getPlayer(UUID.fromString(citizen.getString("player")));
        
        if(!executor.apply(player, name, args, true))
            new Message(command.getUsage()).send(player);
        
        return true;
    }
}