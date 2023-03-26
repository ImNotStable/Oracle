package com.imnotstable.oracle.utils;

import java.time.Duration;

public class TimeUtils {
    public static String formatTime(long unixTimestamp) {
        Duration duration = Duration.ofSeconds(unixTimestamp);
        long weeks = duration.toDays() / 7;
        long days = duration.toDays() % 7;
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        StringBuilder sb = new StringBuilder();
        if (weeks > 0) {
            sb.append(weeks).append("w ");
        }
        if (days > 0) {
            sb.append(days).append("d ");
        }
        if (hours > 0) {
            sb.append(hours).append("h ");
        }
        if (minutes > 0) {
            sb.append(minutes).append("m ");
        }
        sb.append(seconds).append("s");

        return sb.toString().trim();
    }
}
