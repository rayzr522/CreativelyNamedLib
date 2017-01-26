/**
 * 
 */
package com.rayzr522.creativelynamedlib.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * @author Rayzr
 *
 */
public class ClickEvent {

    private InventoryClickEvent raw;
    private GUI gui;
    private Component component;

    /**
     * Constructs a new {@link ClickEvent} from an {@link InventoryClickEvent}
     * 
     * @param raw The raw {@link InventoryClickEvent}
     */
    public ClickEvent(InventoryClickEvent raw, GUI gui, Component component) {
        this.raw = raw;
        this.gui = gui;
        this.component = component;
    }

    /**
     * @return The raw {@link InventoryClickEvent}
     */
    public InventoryClickEvent raw() {
        return raw;
    }

    /**
     * @return The player that clicked
     */
    public Player getPlayer() {
        return (Player) raw().getWhoClicked();
    }

    /**
     * @return The {@link ClickType type} of this click
     */
    public ClickType getType() {
        return raw().getClick();
    }

    /**
     * @return <code>true</code> if it was a shift-click
     */
    public boolean isShift() {
        return raw().isShiftClick();
    }

    /**
     * @return <code>true</code> if it was a left-click
     */
    public boolean isLeft() {
        return raw().isLeftClick();
    }

    /**
     * @return <code>true</code> if it was a right-click
     */
    public boolean isRight() {
        return raw().isRightClick();
    }

    /**
     * @return <code>true</code> if it was a middle-click
     */
    public boolean isMiddle() {
        return getType() == ClickType.MIDDLE;
    }

    /**
     * @return Whether or not the event has been cancelled
     */
    public boolean isCancelled() {
        return raw().isCancelled();
    }

    /**
     * @param cancelled Whether or not the event should be cancelled (defaults to <code>true</code>)
     */
    public void setCancelled(boolean cancelled) {
        raw().setCancelled(cancelled);
    }

    /**
     * @return The {@link GUI} that was clicked
     */
    public GUI getGui() {
        return gui;
    }

    /**
     * @return The {@link Component} that was clicked
     */
    public Component getComponent() {
        return component;
    }

}
