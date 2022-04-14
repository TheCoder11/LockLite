package com.somemone.lockro11en;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.WorldCoord;
import com.somemone.lockro11en.Locks;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.persistence.PersistentDataType;
import com.palmergames.bukkit.towny.object.Town;

import java.util.UUID;

public class LockedChest {

    public static final NamespacedKey lockKey = new NamespacedKey(Locks.getInstance(), "locked-chest");

    private UUID owner;
    private boolean isChest;
    private Block block;

    public LockedChest(Block block) {
        isChest = block.getState() instanceof Chest;
        if (!isChest) return;
        this.block = block;
        if (((Chest) block.getState()).getPersistentDataContainer().has(lockKey)) {
            owner = UUID.fromString(((Chest) block.getState()).getPersistentDataContainer().getOrDefault(lockKey, PersistentDataType.STRING, ""));
        }
        else {
            isChest = false;
        }
    }

    public boolean isProtected (UUID accessor) throws NotRegisteredException {
        if (!isChest) return false;

        if (accessor.equals(owner)) return true;

        Town town = WorldCoord.parseWorldCoord(block).getTownBlock().getTownOrNull();
        Resident resident = TownyUniverse.getInstance().getResidentOpt(owner).get();
        
        if (town == null) return false;
        if (town == resident.getTownOrNull()) return true;
        
        return false;
    }

}
