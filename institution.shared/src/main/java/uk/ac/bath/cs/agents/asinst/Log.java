package uk.ac.bath.cs.agents.asinst;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Log {
    public static void message (String message) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        org.iids.aos.log.Log.console(String.format("[%s] %s", sdf.format(cal.getTime()), message));
    }
}