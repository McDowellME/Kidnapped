package com.sprints;

public class TimeElapsed {
    //
    private static long startTime = System.currentTimeMillis();
    // time game will run in seconds (5 mins)
    private static int runtime = 300;

    public static String getTime() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        // convert milli to seconds
        elapsedTime = elapsedTime/1000;

        // update remaining game time
        String remaining = Integer.toString((int) (runtime - elapsedTime));

        return "Time Remaining: " + remaining;
    }
}