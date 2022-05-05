package com.sprints;

public class TimeElapsed {
    private static long startTime = System.currentTimeMillis();
    private static int runtime = 300;
    private static long elapsedTime;

    // get the in game timer's current time remaining
    public static String getTime() {
        elapsedTime = System.currentTimeMillis() - startTime;
        // convert milli to seconds
        elapsedTime = elapsedTime/1000;
        // update remaining game time
        String remaining = Integer.toString((int) (runtime - elapsedTime));
        if (Integer.parseInt(remaining) < 0) {
            remaining = Integer.toString(0);
        }
        return remaining;
    }
}