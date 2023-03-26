package com.imnotstable.oracle.utils;

import java.text.DecimalFormat;

public class NumberUtils {

    private static final String[] suffixes = { "", "k", "M", "B", "T" };
    private static final char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static String formatSuffix(double value) {
        int index;

        for (index = 0; value >= 1000; index++)
            value *= 0.001;

        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        String formattedNumber = decimalFormat.format(value);
        if (index < suffixes.length) return formattedNumber + suffixes[index];
        else {
            index -= 5;
            int num = index / 26;
            return formattedNumber + alphabet[num] + alphabet[index % 26];
        }
    }

    public static String formatCommas(double value) {
        DecimalFormat formatter = new DecimalFormat("#,###.0");
        return formatter.format(value);
    }

}