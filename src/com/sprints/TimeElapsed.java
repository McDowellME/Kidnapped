package com.sprints;

class TimeElapsed {
    static int runtime = 600;



    // ******** Class Singleton **********
    private static TimeElapsed timer = null;

    // ******** Fields **********
    private static final long startTime = System.currentTimeMillis();

    private TimeElapsed() {

    }

    static TimeElapsed getInstance() {
        if (timer == null) {
            timer = new TimeElapsed();
        }
        return timer;
    }

    // get the in game timer's current time remaining
    public String getTime() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        // convert milli to seconds
        elapsedTime = elapsedTime /1000;
        // update remaining game time
        String remaining = Integer.toString((int) (runtime - elapsedTime));
        if (Integer.parseInt(remaining) < 0) {
            remaining = Integer.toString(0);
        }
        return remaining;
    }

    public static void setRuntime(int runtime) {
        TimeElapsed.runtime = runtime;
    }
}