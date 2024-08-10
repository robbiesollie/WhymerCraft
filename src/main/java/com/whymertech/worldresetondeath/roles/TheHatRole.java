package com.whymertech.worldresetondeath.roles;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.whymertech.worldresetondeath.GameManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.structure.Structure;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import static com.whymertech.worldresetondeath.Utils.plugin;
import static com.whymertech.worldresetondeath.Utils.randInt;

public class TheHatRole extends GenericRole {

    public double health = 20.0;
    public Location island;
    private int xOffset = -1;
    private int yOffset = 4;
    private int zOffset = 4;

    public TheHatRole(Player player) {
        super(player);
    }
    
    @Override
    public Location getRoleSpawnLocation() {
        World world = Bukkit.getWorld(GameManager.WORLD_NAME);

        if (world != null) {
            return new Location(world,
                    island.getBlockX() + xOffset,
                    island.getBlockY() + yOffset,
                    island.getBlockZ() + zOffset);
        }
        
        return null;
    }

    @Override
    public void preparePlayer() {
        File skyblockFile = new File(plugin.getDataFolder(), "skyblock.nbt");
        World world = Bukkit.getWorld(GameManager.WORLD_NAME);
        try {
            island = new Location(world, randInt(-100, 100), 250, randInt(-100, 100));
            Structure skyblock = Bukkit.getStructureManager().loadStructure(skyblockFile);
            //Random orientation is cool, but save until i can calculate correct spawn location
//            skyblock.place(island, false, StructureRotation.values()[randInt(0, 3)], Mirror.values()[randInt(0, 2)], 0, 1, new Random());
            skyblock.place(island, false, StructureRotation.NONE, Mirror.NONE, 0, 1, new Random());
            giveItems();
        } catch (IOException e) {
            giveFreeElytra();
            island = new Location(world,
                    world.getSpawnLocation().getBlockX() - xOffset,
                    world.getSpawnLocation().getBlockY() - yOffset,
                    world.getSpawnLocation().getBlockZ() - zOffset);
        }
    }

    @Override
    public void resetPlayer() {
        super.resetPlayer();
    }

    @Override
    public String name() {
        return "TheHat";
    }

    public void giveFreeElytra() {
        ItemStack elytra = new ItemStack(Material.ELYTRA);
        ItemStack rockets = new ItemStack(Material.FIREWORK_ROCKET, 64);

        super.enchantItem(Enchantment.MENDING, 1, elytra);
        super.enchantItem(Enchantment.UNBREAKING, 255, elytra);

        giveItems();

        super.player.getInventory().addItem(elytra, rockets);

    }

    public void giveItems() {
        giveBaseSword();
        giveBasePickaxe();
        giveBaseAxe();
        giveBaseShovel();
    }
}