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
    public static <T> T from(List<T> list) {
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }

    /**
     * Returns a random item from an array
     * 
     * @param <T> The type of the array
     * @param array The array to get the item from
     * @return The item
     */
    public static <T> T from(T[] array) {
        return array[ThreadLocalRandom.current().nextInt(array.length)];
    }

}
