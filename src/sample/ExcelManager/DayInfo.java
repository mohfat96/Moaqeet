package sample.ExcelManager;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class DayInfo {

    private LocalDate date;
    private LocalTime fajr;
    private LocalTime dhuhr;
    private LocalTime asr;
    private LocalTime magrib;
    private LocalTime isha;
    private LocalTime fajrIqama;
    private LocalTime dhuhrIqama;
    private LocalTime asrIqama;
    private LocalTime magribIqama;
    private LocalTime ishaIqama;
    private String hadith;


    public LocalDate getDate() {
        return date;
    }

    public LocalTime getFajr() {
        return fajr;
    }

    public LocalTime getDhuhr() {
        return dhuhr;
    }

    public LocalTime getAsr() {
        return asr;
    }

    public LocalTime getMagrib() {
        return magrib;
    }

    public LocalTime getIsha() {
        return isha;
    }

    public LocalTime getFajrIqama() {
        return fajrIqama;
    }

    public LocalTime getDhuhrIqama() {
        return dhuhrIqama;
    }

    public LocalTime getAsrIqama() {
        return asrIqama;
    }

    public LocalTime getMagribIqama() {
        return magribIqama;
    }

    public LocalTime getIshaIqama() {
        return ishaIqama;
    }

    public String getHadith() {
        return hadith;
    }

    public DayInfo(String date, String fajr, String fajrIqama, String dhuhr, String dhuhrIqama, String asr, String asrIqama, String magrib, String magribIqama, String isha, String ishaIqama, String hadith) {
        this.date = LocalDate.parse(date);
        this.fajr = LocalTime.parse(fajr);
        this.fajrIqama = LocalTime.parse(fajrIqama);
        this.dhuhr = LocalTime.parse(dhuhr);
        this.dhuhrIqama = LocalTime.parse(dhuhrIqama);
        this.asr = LocalTime.parse(asr);
        this.asrIqama = LocalTime.parse(asrIqama);
        this.magrib = LocalTime.parse(magrib);
        this.magribIqama = LocalTime.parse(magribIqama);
        this.isha = LocalTime.parse(isha);
        this.ishaIqama = LocalTime.parse(ishaIqama);
        this.hadith = hadith;
    }


    public DayInfo(Date date, Date fajr, Date fajrIqama, Date dhuhr, Date dhuhrIqama, Date asr, Date asrIqama, Date magrib, Date magribIqama, Date isha, Date ishaIqama, String hadith) {
        this.date = converter(date).toLocalDate();
        this.fajr = converter(fajr).toLocalTime();
        this.dhuhr = converter(dhuhr).toLocalTime();
        this.asr = converter(asr).toLocalTime();
        this.magrib = converter(magrib).toLocalTime();
        this.isha = converter(isha).toLocalTime();
        this.fajrIqama = converter(fajrIqama).toLocalTime();
        this.dhuhrIqama = converter(dhuhrIqama).toLocalTime();
        this.asrIqama = converter(asrIqama).toLocalTime();
        this.magribIqama = converter(magribIqama).toLocalTime();
        this.ishaIqama = converter(ishaIqama).toLocalTime();
        this.hadith = hadith;
    }

    private LocalDateTime converter(Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneOffset.systemDefault());
    }

    @Override
    public String toString() {
        return "DayInfo{" +
                "date='" + date + '\'' +
                ", fajr='" + fajr + '\'' +
                ", dhuhr='" + dhuhr + '\'' +
                ", asr='" + asr + '\'' +
                ", magrib='" + magrib + '\'' +
                ", isha='" + isha + '\'' +
                ", fajrIqama='" + fajrIqama + '\'' +
                ", dhuhrIqama='" + dhuhrIqama + '\'' +
                ", asrIqama='" + asrIqama + '\'' +
                ", magribIqama='" + magribIqama + '\'' +
                ", ishaIqama='" + ishaIqama + '\'' +
                ", hadith='" + hadith + '\'' +
                '}';
    }
}
