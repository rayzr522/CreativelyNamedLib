/**
 * 
 */
package com.rayzr522.creativelynamedlib.gui;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import org.bukkit.Material;

import com.rayzr522.creativelynamedlib.utils.ItemFactory;
import com.rayzr522.creativelynamedlib.utils.Point;
import com.rayzr522.creativelynamedlib.utils.Size;

/**
 * @author Rayzr
 *
 */
public class Component implements Cloneable {

    private Size size = Size.ONE;
    private ItemFactory item = ItemFactory.of(Material.STAINED_GLASS_PANE);

    private Consumer<ClickEvent> clickHandler = null;
    private RenderHandler renderHandler = (gui, offset) -> {
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

    /**
     * This method is called for each slot that the component occupies when it needs to "render"
     * 
     * @param gui The {@link GUI} that this component is in
     * @param offX the offset on the X axis from the origin of the component
     * @param offY the offset on the Y axis from the origin of the component
     * @return An {@link ItemFactory} that determines the appearance of the item
     */
    protected ItemFactory render() {
        return item;
    };

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
         * @param item
         * @see com.rayzr522.creativelynamedlib.gui.Component#setItem(com.rayzr522.creativelynamedlib.utils.ItemFactory)
         * @return This {@link Builder} instance
         */
        public Builder setItem(ItemFactory item) {
            component.setItem(item);
            return this;
        }

        /**
         * @param clickHandler
         * @see com.rayzr522.creativelynamedlib.gui.Component#setClickHandler(java.util.function.Consumer)
         * @return This {@link Builder} instance
         */
        public Builder setClickHandler(Consumer<ClickEvent> clickHandler) {
            component.setClickHandler(clickHandler);
            return this;
        }

        /**
         * @param renderHandler
         * @see com.rayzr522.creativelynamedlib.gui.Component#setRenderHandler(com.rayzr522.creativelynamedlib.gui.RenderHandler)
         * @return This {@link Builder} instance
         */
        public Builder setRenderHandler(RenderHandler renderHandler) {
            component.setRenderHandler(renderHandler);
            return this;
        }

        /**
         * @param size The size to set
         * @return This {@link Builder} instance
         */
        public Builder setSize(Size size) {
            component.setSize(size);
            return this;
        }

        /**
         * 
         * @param text The display name to set
         * @return This {@link Builder} instance
         */
        public Builder setName(String text) {
            component.getItem().setName(text);
            return this;
        }

        /**
         * 
         * @param amount The amount to set
         * @return This {@link Builder} instance
         */
        public Builder setAmount(int amount) {
            component.getItem().setAmount(amount);
            return this;
        }

        /**
         * @param durability The durability to set
         * @return This {@link Builder} instance
         */
        public Builder setDurability(int durability) {
            component.getItem().setDurability(durability);
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
