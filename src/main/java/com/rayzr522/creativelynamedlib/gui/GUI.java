package com.rayzr522.creativelynamedlib.gui;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
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
        reRender();
        player.openInventory(getInventory());
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

    void handleClick(InventoryClickEvent raw) {
        int slot = raw.getRawSlot(), col = getColumns();
        Point pos = Point.at(slot % col, slot / col);

        List<Component> possible = components.entrySet().stream().filter(e -> pos.subtract(e.getValue()).isWithin(e.getKey().getSize())).map(e -> e.getKey()).collect(Collectors.toList());
        if (possible.size() < 1) {
            return;
        }
        Component component = possible.get(possible.size() - 1);
        component.getClickHandler().ifPresent(handler -> handler.accept(new ClickEvent(raw, this, component)));
    }

    /**
     * Re-render all components of this {@link GUI}
     */
    public void reRender() {
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
