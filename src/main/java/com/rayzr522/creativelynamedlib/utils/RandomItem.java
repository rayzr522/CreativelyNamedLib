/**
 * 
 */
package com.rayzr522.creativelynamedlib.utils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }

}
