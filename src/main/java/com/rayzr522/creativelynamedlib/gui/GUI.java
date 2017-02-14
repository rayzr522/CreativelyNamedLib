package com.rayzr522.creativelynamedlib.gui;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import com.rayzr522.creativelynamedlib.utils.ItemFactory;
import com.rayzr522.creativelynamedlib.utils.text.TextUtils;
import com.rayzr522.creativelynamedlib.utils.types.Point;

public class GUI implements InventoryHolder {

    private Map<Component, Point> components = new LinkedHashMap<>();
    private Inventory inventory;

    private boolean allowClosing = true;

    public GUI(InventoryType type, String title) {
        this.inventory = Bukkit.createInventory(this, type, TextUtils.colorize(title));
    }

    public GUI(int rows, String title) {
        this.inventory = Bukkit.createInventory(this, rows * 9, TextUtils.colorize(title));
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * @param player The {@link Player} to open this {@link GUI} for
     */
    public void open(Player player) {
        open(player, true);
    }

    /**
     * @param player The {@link Player} to open this {@link GUI} for
     */
    public void open(Player player, boolean reRender) {
        if (reRender)
            reRender();
        player.openInventory(getInventory());
    }

    /**
     * Closes the {@link GUI} if it is open
     */
    public void close() {
        HumanEntity[] entities = getInventory().getViewers().toArray(new HumanEntity[0]);
        
        boolean reset = allowClosing;
        allowClosing = true;
        
        for (HumanEntity entity : entities) {
            entity.closeInventory();
        }
        
        allowClosing = reset;
    }

    /**
     * Override this method to add behaviour to GUIs when they close
     * 
     * @param event The {@link InventoryCloseEvent}
     */
    public void onClose(InventoryCloseEvent event) {
    }

    /**
     * Adds a new component to this {@link GUI}
     * 
     * @param component The component to add
     * @param position The position to place the component at
     * @return This {@link GUI} instance
     */
    public GUI add(Component component, Point position) {
        Objects.requireNonNull(component, "component cannot be null!");
        Objects.requireNonNull(position, "position cannot be null!");
        this.components.put(component.clone(), position);
        return this;
    }

    /**
     * Sets whether the {@link GUI} should be allowed to close. Defaults to <code>true</code>. This does not affect calling {@link #close()}
     * 
     * @param allowClosing Whether or not the GUI should be allowed to close
     * @return This {@link GUI} instance
     */
    public GUI setAllowClosing(boolean allowClosing) {
        this.allowClosing = allowClosing;
        return this;
    }
    
    /**
     * @return Whether or not the {@link GUI} should be allowed to close. Defaults to <code>true</code>.
     */
    public boolean allowClosing() {
        return allowClosing;
    }

    /**
     * This returns a direct reference to the components map of this {@link GUI}. <em>WARNING: Modifying this will modify the actual components of the GUI!</em>
     * 
     * @return The components map
     */
    public Map<Component, Point> getComponents() {
        return components;
    }

    void handleClick(InventoryClickEvent raw) {
        int slot = raw.getRawSlot(), col = getColumns();
        Point pos = Point.at(slot % col, slot / col);

        List<Component> possible = components.entrySet().stream().filter(e -> pos.subtract(e.getValue()).isWithin(e.getKey().getSize())).map(e -> e.getKey()).collect(Collectors.toList());
        if (possible.size() < 1) {
            return;
        }
        Component component = possible.get(possible.size() - 1);

        component.getClickHandler().ifPresent(handler -> {
            ClickEvent event = new ClickEvent(raw, this, component);
            handler.accept(event);
            if (event.shouldCloseOnClick()) {
                this.close();
            }
        });
    }

    /**
     * Re-render all components of this {@link GUI}
     */
    public void reRender() {
        inventory.setContents(new ItemStack[0]);
        components.entrySet().stream().forEach(e -> e.getKey().render(this, e.getValue()));
    }

    /**
     * Sets an item in the inventory at the given slot
     * 
     * @param slot The slot to change
     * @param item The item to set at that slot
     */
    public void setItem(int slot, ItemFactory item) {
        setItem(slot, item.build());
    }

    /**
     * Sets an item in the inventory at the given slot
     * 
     * @param slot The slot to change
     * @param item The item to set at that slot
     */
    public void setItem(int slot, ItemStack item) {
        Validate.isTrue(slot >= 0 && slot < inventory.getSize(), "slot must be between 0 and " + (inventory.getSize() - 1));
        inventory.setItem(slot, item);
    }

    /**
     * @return The number of rows for this GUI
     */
    public int getRows() {
        return inventory.getContents().length / getColumns();
    }

    /**
     * @return The number of columns for this GUI's inventory type (not perfect, bound to break!)
     */
    public int getColumns() {
        switch (inventory.getType()) {
            case ANVIL:
                return 3;
            case BEACON:
                return 1;
            case BREWING:
                return 4;
            case CHEST:
                return 9;
            case DISPENSER:
                return 3;
            case DROPPER:
                return 3;
            case ENCHANTING:
                return 1;
            case ENDER_CHEST:
                return 9;
            case FURNACE:
                return 3;
            case HOPPER:
                return 5;
            case MERCHANT:
                return 3;
            case WORKBENCH:
                return 3;
            default:
                return 9;

        }
    }
}
