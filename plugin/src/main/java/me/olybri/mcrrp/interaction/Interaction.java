package me.olybri.mcrrp.interaction;// Created by Loris Witschard on 7/4/2017.

import org.bukkit.event.player.PlayerEvent;

public interface Interaction
{
    boolean apply(PlayerEvent event);
}
