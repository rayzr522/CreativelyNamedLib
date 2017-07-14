package me.rayzr522.cnl.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TextUtils {
    public static String enumFormat(String input) {
        Objects.requireNonNull(input, "input cannot be null!");

        return input.toUpperCase().replaceAll("[^0-9A-Z$_]", "_");
    }

    public static String convertCamelCaseToHyphens(String input) {
        Objects.requireNonNull(input, "input cannot be null!");

        return Arrays.stream(wordsFromCamelCase(input)).collect(Collectors.joining("-"));
    }

    private static String[] wordsFromCamelCase(String input) {
        Objects.requireNonNull(input, "input cannot be null!");

        // Wouldn't want to accidentally change this :p
        final int len = input.length();

        if (len < 1) {
            return new String[0];
        }

        List<String> output = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();

        boolean lastUppercase = false;
        int i = 0;
        while (i < len) {
            char next = input.charAt(i);
            boolean uppercase = next >= 'A' && next <= 'Z';

            if (!lastUppercase && uppercase && buffer.length() > 0) {
                output.add(buffer.toString().toLowerCase());
                buffer = new StringBuilder();
            }

            lastUppercase = uppercase;

            buffer.append(next);
            i++;
        }

        if (buffer.length() > 0) {
            output.add(buffer.toString().toLowerCase());
        }

        return output.toArray(new String[0]);
    }

}
