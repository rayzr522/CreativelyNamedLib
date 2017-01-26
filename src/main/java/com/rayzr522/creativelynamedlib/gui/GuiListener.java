/**
 * 
 */
package com.rayzr522.creativelynamedlib.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

/**
 * @author Rayzr
 *
 */
public class GuiListener implements Listener {

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

}
