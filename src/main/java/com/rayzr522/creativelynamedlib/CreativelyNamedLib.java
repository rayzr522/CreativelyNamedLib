package com.rayzr522.creativelynamedlib;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.rayzr522.creativelynamedlib.gui.Component;
import com.rayzr522.creativelynamedlib.gui.GUI;
import com.rayzr522.creativelynamedlib.gui.GuiListener;
import com.rayzr522.creativelynamedlib.utils.Point;
import com.rayzr522.creativelynamedlib.utils.Size;

public class CreativelyNamedLib extends JavaPlugin {
    private static CreativelyNamedLib instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new GuiListener(), this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bukkit.plugin.java.JavaPlugin#onCommand(org.bukkit.command.CommandSender, org.bukkit.command.Command, java.lang.String, java.lang.String[])
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "These tests are player-only");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Usage: " + ChatColor.GRAY + "/cnltest [testName]");
            player.sendMessage(ChatColor.RED + "Available tests: " + ChatColor.GRAY + "gui");
            return true;
        }

        String sub = args[0].toLowerCase();

        if (sub.equals("gui")) {

            new GUI(5, "Hello &cWorld!")
                    .add(
                            Component.create()
                                    .setName(" ")
                                    .setSize(Size.of(9, 5))
                                    .setDurability(15).build(),
                            Point.at(0, 0))
                    .add(
                            Component.create()
                                    .setName("&cClick to &kDIE")
                                    .setDurability(14)
                                    .setSize(Size.of(5, 2))
                                    .setClickHandler(e -> e.getPlayer().setHealth(0)).build(),
                            Point.at(3, 2))
                    .open(player);

        }

        return true;
    }

    public static CreativelyNamedLib getInstance() {
        return instance;
    }
}
