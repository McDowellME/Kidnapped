package com.sprints;

public class TimeElapsed {
        private static TimeElapsed timer = null;
        private static long startTime = System.currentTimeMillis();
        private static int runtime = 300;


        public static TimeElapsed getInstance() {
            if (timer == null) {
                timer = new TimeElapsed();
            }
            return timer;
        }

        public String getTime() {
            long elapsedTime = System.currentTimeMillis() - startTime;
            long gamePlay = 300000;
            elapsedTime = elapsedTime/1000;

            long sec = elapsedTime % 60;
            long playSec = gamePlay % 60;

            long elapsedSeconds = sec - playSec;
            String remaining = Integer.toString((int) (runtime - elapsedSeconds));

            return "Time Remaining: " + remaining;
        }


}