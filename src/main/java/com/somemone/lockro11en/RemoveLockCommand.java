package com.somemone.lockro11en;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.BlockIterator;
import org.jetbrains.annotations.NotNull;

public class RemoveLockCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if ( !(sender instanceof Player)) return false;
        Player player = (Player) sender;

        BlockIterator iterator = new BlockIterator(player, 5);
        Block block = iterator.next();
        while (iterator.hasNext()) {
            if (block.getState().getType() == Material.CHEST)
                break;

            block = iterator.next();
        }

        if (block.getState().getType() == Material.CHEST) {

            Chest chest = (Chest) block.getState();
            if (chest.getPersistentDataContainer().has(LockedChest.lockKey)) {
                if (chest.getPersistentDataContainer().get(LockedChest.lockKey, PersistentDataType.STRING).equals(player.getUniqueId().toString()) ) {
                    chest.getPersistentDataContainer().remove(LockedChest.lockKey);

                    sender.sendMessage(ChatColor.GOLD + "Protection has been removed from this chest");
                } else {
                    sender.sendMessage(ChatColor.RED + "You don't own this chest!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "This chest has no protection on it.");
            }

        }

        return true;
    }
}
