package com.sprints;

public class TimeElapsed {
    private static long startTime = System.currentTimeMillis();
    private static int runtime = 300;

    public static String getTime() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        elapsedTime = elapsedTime/1000;

        String remaining = Integer.toString((int) (runtime - elapsedTime));

        return "Time Remaining: " + remaining;
    }
}