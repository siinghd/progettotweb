package utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Utils {
    public static ArrayList<String> getDates (){
        ArrayList<String> days = new ArrayList<>();
        Calendar now = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("EEEE");
        now.add(Calendar.DAY_OF_MONTH, 1);
        for (int i = 0; i < 5; i++)
        {
            if(!format2.format(now.getTime()).equalsIgnoreCase(
                    "sabato")
                    && !format2.format(now.getTime()).equalsIgnoreCase("domenica")){
                days.add(format.format(now.getTime()));
            }
            now.add(Calendar.DAY_OF_MONTH, 1);
        }
        return days;
    }
}
