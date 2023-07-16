package uniqueimpact.discordRP.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TimeKeeper {

    private static final Timestamp midnightAnchor = Timestamp.valueOf("2023-07-06 12:00:00.0");
    private static final long slowdownFactor = 3l;

    public static String getCurrentTime() {
        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
        long difference = currentTime.getTime() - midnightAnchor.getTime();
        difference = difference / slowdownFactor;

        int day = (int) (difference / (1000 * 60 * 60 * 24)) + 1;
        int hour = (int) ((difference / (1000 * 60 * 60)) % 24);
        int minute = (int) ((difference / (1000 * 60)) % 60);

        String suffix = (hour >= 12) ? "PM" : "AM";

        return "Day " + day + " - " + (hour % 12) + ":" + (minute < 10 ? "0" : "") + minute + " " + suffix;
    }

    public static void main(String[] args) {
        String lastTime = "";
        while (true) {
            String thisTime = getCurrentTime();
            if (!thisTime.equals(lastTime)) {
                System.out.println(thisTime);
                lastTime = thisTime;
            }
        }
    }

}
