package cz.borec.stopwatch.util;

public class TimeUtils {
	
    public static String displayTime(long time) {
        long hours = time / 3600000;
        long minutes = time / 60000;
        long seconds = (time % 60000) / 1000;
        long milliseconds = time % 1000;
        return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds);
    }
}
