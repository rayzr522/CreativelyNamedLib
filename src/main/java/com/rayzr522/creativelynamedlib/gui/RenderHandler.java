/**
 * 
 */
package com.rayzr522.creativelynamedlib.gui;

import com.rayzr522.creativelynamedlib.utils.ItemFactory;
import com.rayzr522.creativelynamedlib.utils.types.Point;

/**
 * A render-handler for components.
 * 
 * @see #render(GUI, Point)
 * @author Rayzr
 */
@FunctionalInterface
public interface RenderHandler {

    /**
     * @param gui The {@link GUI} that is being rendered to
     * @param offset A {@link Point} representing the offset from the origin of the component
     */
    public ItemFactory render(GUI gui, Point offset);

}
