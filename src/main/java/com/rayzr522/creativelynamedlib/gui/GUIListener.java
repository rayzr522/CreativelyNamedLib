/**
 * 
 */
package com.rayzr522.creativelynamedlib.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.scheduler.BukkitRunnable;

import com.rayzr522.creativelynamedlib.CreativelyNamedLib;

/**
 * @author Rayzr
 *
 */
public class GUIListener implements Listener {

    private CreativelyNamedLib plugin;

    public GUIListener(CreativelyNamedLib plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.isCancelled() || event.getInventory() == null || event.getInventory().getHolder() == null
                || !(event.getWhoClicked() instanceof Player) || !(event.getInventory().getHolder() instanceof GUI))
            return;

        InventoryType type = event.getInventory().getType();
        if (type == InventoryType.PLAYER || type == InventoryType.CRAFTING) {
            return;
        }

        GUI gui = (GUI) event.getInventory().getHolder();

        event.setCancelled(true);
        gui.handleClick(event);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory() == null || event.getInventory().getHolder() == null
                || !(event.getPlayer() instanceof Player) || !(event.getInventory().getHolder() instanceof GUI)) {
            return;
        }

        InventoryType type = event.getInventory().getType();
        if (type == InventoryType.PLAYER || type == InventoryType.CRAFTING) {
            return;
        }

        GUI gui = (GUI) event.getInventory().getHolder();

        if (gui.allowClosing()) {
            gui.onClose(event);
        } else {
            new BukkitRunnable() {
                public void run() {
                    event.getPlayer().openInventory(gui.getInventory());
                }
            }.runTaskLater(plugin, 1L);
        }

    }

}
