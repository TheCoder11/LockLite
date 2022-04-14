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

public class LockCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if ( !(sender instanceof Player )) return false;
        Player player = (Player) sender;

        BlockIterator iterator = new BlockIterator(player, 5);
        Block block = iterator.next();
        while (iterator.hasNext()) {
            if (block.getState().getType() == Material.CHEST)
                break;

            block = iterator.next();
        }

        if (block.getState().getType() == Material.CHEST) {
            if (!((Chest) block.getState()).getPersistentDataContainer().has(LockedChest.lockKey)) {
                ((Chest) block.getState()).getPersistentDataContainer().set(LockedChest.lockKey, PersistentDataType.STRING, player.getUniqueId().toString());

                player.sendMessage(ChatColor.GOLD + "This chest is now only accessible by you. This protection only is in effect when your town is in control of this" +
                        " chunk, so make sure to keep the land safe.");
            } else {
                player.sendMessage(ChatColor.RED + "This chest is already locked by another player.");
            }
        } else {
            player.sendMessage(ChatColor.RED + "That's not a chest. Remember, barrels aren't supported");
        }

        return true;

    }
}
