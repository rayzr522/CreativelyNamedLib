/**
 * 
 */
package com.rayzr522.creativelynamedlib.utils;

import java.util.List;
import java.util.Random;

/**
 * @author Rayzr
 *
 */
public class RandomItem {

    /**
     * Returns a random item for a list
     * 
     * @param <T> The type of the {@link List}
     * @param list The list to get the item from
     * @return The item
     */
    public static <T> T fromList(List<T> list) {
        return list.get(new Random().nextInt(list.size()));
    }

}
