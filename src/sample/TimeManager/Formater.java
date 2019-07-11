package sample.TimeManager;

import java.time.LocalDate;
import java.time.chrono.HijrahDate;
import java.time.temporal.ChronoField;

public class Formater {
    public static String getHijriDate(){
        HijrahDate hijrahDate = HijrahDate.now();
      return "" +  hijrahDate.get(ChronoField.DAY_OF_MONTH) + " " + monthConverter(hijrahDate.get(ChronoField.MONTH_OF_YEAR));
    }
    public static String getHijriDay(){
       LocalDate date = LocalDate.now();
      return   Formater.dayConverter(date.getDayOfWeek().getValue());
    }

    public static String getGermanDay(){
        LocalDate date = LocalDate.now();
        switch (date.getDayOfWeek().getValue()){
            case 1: return "Montag";
            case 2: return "Dienstag";
            case 3: return "Mittwoch";
            case 4: return "Donnerstag";
            case 5: return "Freitag";
            case 6: return "Samstag";
            default: return "Sonntag";
        }
    }
    public static String dayConverter(int dayOfTheWeek){
        switch (dayOfTheWeek){
            case 1: return "الإثنين";
            case 2: return "الثلاثاء";
            case 3: return "الأربعاء";
            case 4: return "الخميس";
            case 5: return "الجمعة";
            case 6: return "السبت";
            default: return "الأحد";
        }
    }

    private static String monthConverter(int monthOfTheYear){
        switch (monthOfTheYear){
            case 1: return "مـــــحـــرم";
            case 2: return "صــــفــــر";
            case 3: return "ربيع الأول";
            case 4: return "ربيع الثاني";
            case 5: return "جمادى الآولى";
            case 6: return "جمادى الآخرة";
            case 7: return "رجـــــــب";
            case 8: return "شعـــبـــان";
            case 9: return "رمـــــضــان";
            case 10: return "شــــــوال";
            case 11: return "ذو القـعدة";
            default: return "ذو الحــجة";
        }
    }
}
