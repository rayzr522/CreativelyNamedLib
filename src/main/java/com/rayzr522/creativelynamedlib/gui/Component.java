/**
 * 
 */
package com.rayzr522.creativelynamedlib.gui;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.rayzr522.creativelynamedlib.utils.ItemFactory;
import com.rayzr522.creativelynamedlib.utils.types.Point;
import com.rayzr522.creativelynamedlib.utils.types.Size;

/**
 * @author Rayzr
 *
 */
public class Component implements Cloneable {

    private Size size = Size.ONE;
    private ItemFactory item = ItemFactory.of(Material.STAINED_GLASS_PANE);

    private Consumer<ClickEvent> clickHandler = null;
    private RenderHandler renderHandler = (gui, offset) -> {
        System.out.println(getItem().build().getType().name());
        return getItem();
    };

    private Component() {
        // Used for the Builder
    }

    /**
     * @return The size of this component
     */
    public Size getSize() {
        return size;
    }

    /**
     * @param size The size to set
     */
    public void setSize(Size size) {
        Objects.requireNonNull(size, "size cannot be null!");
        this.size = size;
    }

    /**
     * @return The item
     */
    public ItemFactory getItem() {
        return item;
    }

    /**
     * @param item The item to use
     */
    public void setItem(ItemFactory item) {
        Objects.requireNonNull(item, "item cannot be null!");
        this.item = item;
    }

    /**
     * @return The {@link ClickEvent} handler
     */
    public Optional<Consumer<ClickEvent>> getClickHandler() {
        return Optional.ofNullable(clickHandler);
    }

    /**
     * @param clickHandler The new {@link ClickEvent} handler to use
     */
    public void setClickHandler(Consumer<ClickEvent> clickHandler) {
        this.clickHandler = clickHandler;
    }

    /**
     * The parameters are the {@link GUI} that the component is rendering in, and a {@link Point} representing the offset from the component's origin.
     * 
     * @return The {@link RenderHandler}
     * 
     * @see RenderHandler#render(GUI, Point)
     */
    public RenderHandler getRenderHandler() {
        return renderHandler;
    }

    /**
     * @param renderHandler The new {@link RenderHandler} to use
     * 
     * @see RenderHandler#render(GUI, Point)
     */
    public void setRenderHandler(RenderHandler renderHandler) {
        Objects.requireNonNull(renderHandler, "renderHandler cannot be null!");
        this.renderHandler = renderHandler;
    }

    /**
     * Renders this {@link Component} on the given {@link GUI}
     * 
     * @param gui The {@link GUI} to render on
     * @param position The position of this component on that {@link GUI}
     */
    public void render(GUI gui, Point position) {
        for (int x = 0; x < size.getWidth(); x++) {
            for (int y = 0; y < size.getHeight(); y++) {
                ItemFactory factory = renderHandler.render(gui, Point.at(x, y));
                if (factory == null) {
                    continue;
                }
                gui.setItem(position.add(Point.at(x, y)).toSlot(gui.getColumns()), factory);
            }
        }
    }

    @Override
    public Component clone() {
        try {
            return (Component) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    /**
     * Creates a new {@link Component} {@link Builder}
     * 
     * @return A {@link Builder} instance
     */
    public static Builder create() {
        return new Builder();
    }

    /**
     * A {@link Component} {@link Builder}
     * 
     * @author Rayzr
     */
    public static class Builder {

        private Component component = new Component();

        Builder() {
        }

        /**
         * @param item The item to set
         * @return This {@link Builder} instance
         */
        public Builder item(ItemFactory item) {
            component.setItem(item);
            return this;
        }

        /**
         * @param item The item to set
         * @return This {@link Builder} instance
         */
        public Builder item(ItemStack item) {
            return item(ItemFactory.of(item));
        }

        /**
         * @param item The item type to set
         * @return This {@link Builder} instance
         */
        public Builder type(Material type) {
            component.getItem().setType(type);
            return this;
        }

        /**
         * @param clickHandler
         * @return This {@link Builder} instance
         */
        public Builder onClick(Consumer<ClickEvent> clickHandler) {
            component.setClickHandler(clickHandler);
            return this;
        }

        /**
         * @param renderHandler
         * @return This {@link Builder} instance
         */
        public Builder onRender(RenderHandler renderHandler) {
            component.setRenderHandler(renderHandler);
            return this;
        }

        /**
         * @param size The size to set
         * @return This {@link Builder} instance
         */
        public Builder ofSize(Size size) {
            component.setSize(size);
            return this;
        }

        /**
         * @param width The width to set
         * @param height The height to set
         * @return This {@link Builder} instance
         */
        public Builder ofSize(int width, int height) {
            return ofSize(Size.of(width, height));
        }

        /**
         * @param text The display name to set
         * @return This {@link Builder} instance
         */
        public Builder named(String text) {
            component.getItem().setName(text);
            return this;
        }

        /**
         * @param lore The lore to set
         * @return This {@link Builder} instance
         */
        public Builder withLore(List<String> lore) {
            component.getItem().setLore(lore);
            return this;
        }

        /**
         * @param lore The lore to set
         * @return This {@link Builder} instance
         */
        public Builder withLore(String... lore) {
            component.getItem().setLore(lore);
            return this;
        }

        /**
         * 
         * @param amount The amount to set
         * @return This {@link Builder} instance
         */
        public Builder withAmount(int amount) {
            component.getItem().setAmount(amount);
            return this;
        }

        /**
         * @param durability The durability to set
         * @return This {@link Builder} instance
         */
        public Builder withDurability(int durability) {
            component.getItem().setDurability(durability);
            return this;
        }

        /**
         * This will error if you call it on non-colorable items
         * 
         * @param color The color to set
         * @return This {@link Builder} instance
         */
        public Builder colored(DyeColor color) {
            component.getItem().setColor(color);
            return this;
        }

        /**
         * @return The completed {@link Component}
         */
        public Component build() {
            return component.clone();
        }

    }

}
