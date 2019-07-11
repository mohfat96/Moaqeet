package sample.ExcelManager;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class ImporterResult {

    private Iterator<Row> iterator;
    private ArrayList<Row> rows = new ArrayList<>();

    public ImporterResult(Iterator<Row> iterator) {

        this.iterator = iterator;
        sort();
    }

    private void sort() {
        while (iterator.hasNext()) {
            Row next = iterator.next();

            if (next.getCell(0) != null && next.getCell(0).getCellType() == CellType.NUMERIC) {
                rows.add(next);
            }

        }
    }

    public ArrayList<DayInfo> getDays() {
        ArrayList<DayInfo> days = new ArrayList<>();
        for (Row row : rows) {
            Date date = row.getCell(0).getDateCellValue();
            Date fajr = row.getCell(4).getDateCellValue();
            Date fajrIqama = row.getCell(5).getDateCellValue();
            Date dhuhr = row.getCell(6).getDateCellValue();
            Date dhuhrIqama = row.getCell(7).getDateCellValue();
            Date asr = row.getCell(8).getDateCellValue();
            Date asrIqama = row.getCell(9).getDateCellValue();
            Date magrib = row.getCell(10).getDateCellValue();
            Date magribIqama = row.getCell(11).getDateCellValue();
            Date isha = row.getCell(12).getDateCellValue();
            Date ishaIqama = row.getCell(13).getDateCellValue();
            String hadith = row.getCell(14).getStringCellValue();
            days.add(new DayInfo(date, fajr, fajrIqama, dhuhr, dhuhrIqama, asr, asrIqama, magrib, magribIqama, isha, ishaIqama, hadith));

        }
        return days;
    }


}
