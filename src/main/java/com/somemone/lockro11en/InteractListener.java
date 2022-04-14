package com.somemone.lockro11en;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    @EventHandler
    public void onInteract (PlayerInteractEvent event) throws NotRegisteredException {
        if (!event.hasBlock()) return;
        if (event.getClickedBlock().getType() != Material.CHEST) return;

        LockedChest chest = new LockedChest(event.getClickedBlock());
        if (chest.isProtected(event.getPlayer().getUniqueId())) {
            event.getPlayer().sendMessage(ChatColor.RED + "This chest is protected. Please contact its owner, whoever it is");
            event.setCancelled(true);
        }
    }

}
