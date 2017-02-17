/**
 * 
 */
package com.rayzr522.creativelynamedlib.utils;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
    @SafeVarargs
    public static <T> T from(T... array) {
        return array[ThreadLocalRandom.current().nextInt(array.length)];
    }

    /**
     * Returns a random item from a map of weights (stored as doubles)
     * 
     * @param weights The weights
     * @return The item
     */
    public static <T> T fromWeights(Map<T, Double> weights) {
        double total = weights.values().stream().reduce(0.0, (a, b) -> a + b);
        double number = ThreadLocalRandom.current().nextDouble(total);

        for (Entry<T, Double> entry : weights.entrySet()) {
            if (number < entry.getValue()) {
                return entry.getKey();
            }
            number -= entry.getValue();
        }

        return null;
    }

}
