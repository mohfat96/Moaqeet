package sample.DatabaseManager;

import sample.ExcelManager.DayInfo;
import sample.GUI.AlertWindow;
import sample.LoggingManager.Logger;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DatabaseConnection implements java.lang.AutoCloseable {
    private boolean isOpen = false;
    private Connection conn = null;
    private String url;


    public DatabaseConnection() {

        File parent = new File(System.getProperty("user.home"), "Moaqeet User Files");
        File child = new File(parent.getAbsolutePath(), "Database");
        File db = new File(child.getAbsolutePath(), "Database");
        boolean success = true;

        if (!db.exists()) {
            if (!parent.exists()) {
                parent.mkdirs();
                child.mkdirs();
            } else if (!child.exists()) {
                child.mkdirs();
            }

            success = createDatabase(child);
        }

        if (success) {
            url = "jdbc:sqlite:" + child.getAbsolutePath() + "/" + "database.db";
        }
    }

    private boolean createDatabase(File child) {

        boolean success = false;
        String urltest = "jdbc:sqlite:" + child.getAbsolutePath() + "/" + "database.db";

        try (Connection conn = DriverManager.getConnection(urltest)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
                createTables(conn);
                success = true;
            }

        } catch (SQLException e) {
            Logger.Error(DatabaseConnection.class.getName() , "createDatabas" , e.getMessage() , e);
        }

        return success;
    }

    private void createTables(Connection conn) {
        String sql1 = "CREATE TABLE \"prayer\" (\n" +
                "\t\"day\"\tDate,\n" +
                "\t\"fajr\"\tTime,\n" +
                "\t\"fajrIqama\"\tTime,\n" +
                "\t\"dhuhr\"\tTime,\n" +
                "\t\"dhuhrIqama\"\tTime,\n" +
                "\t\"asr\"\tTime,\n" +
                "\t\"asrIqama\"\tTime,\n" +
                "\t\"maghrib\"\tTime,\n" +
                "\t\"maghribIqama\"\tTime,\n" +
                "\t\"isha\"\tTime,\n" +
                "\t\"ishaIqama\"\tTime,\n" +
                "\t\"hadith\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"day\")\n" +
                ")";
        String sql2 = "CREATE TABLE \"Friday\" (\n" +
                "\t\"time\"\tTEXT,\n" +
                "\t\"id\"\tINTEGER NOT NULL DEFAULT 0\n" +
                ")";
        String defalutValues = "INSERT INTO Friday VALUES (\"13:00\" , 0);";
        try {
            PreparedStatement psmt = conn.prepareStatement(sql1);
            psmt.executeUpdate();
            psmt = conn.prepareStatement(sql2);
            psmt.executeUpdate();
            psmt = conn.prepareStatement(defalutValues);
            psmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
           Logger.Error(DatabaseConnection.class.getName() , "creatTabeles" , e.getMessage() , e);
        }
    }


    public String getURL() {
        return url;
    }
//    Gibt eine String-Repräsentation der URL zur Datenbank zurück. Die URL zur Datenbank besteht aus dem Präfix "jdbc:sqlite:" und dem im Konstruktor festgelegten Pfad.

    public String toString() {
        return "DatabaseConnection to " + url;
    }
//    Gibt eine String-Repräsentation der folgenden Form zurück:
//    DatabaseConnection to <URL zur Datenbank>


    public void open() {

        if (isOpen) {

            throw new IllegalStateException("is already open");
        }

        try {
            // db parameters
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            isOpen = true;
            Logger.info(DatabaseConnection.class.getName(), "open", "Connection with database is Open!");

        } catch (SQLException e) {
            Logger.Error(DatabaseConnection.class.getName(), "open", e.getMessage(), e);
        }


    }

//    Öffnet eine Verbindung zur Datenbank an der durch den Pfad festgelegten URL.
//    Sollte ein Fehler beim Öffnen der Datenbank auftreten, so soll eine DatabaseOpenException geworfen werden.
//    Sollte die Datenbank bereits geöffnet sein, so soll eine IllegalStateException geworfen werden.

    public boolean isOpen() {
        return isOpen;
    }
//    Diese Methode soll true zurück geben, wenn die Verbindung zur Datenbank geöffnet ist, sonst false.


    public void writeDay(DayInfo info) {

        if (!isOpen) {
            throw new IllegalStateException("closed Connection");
        }


        String sql = "insert into prayer (day,fajr,fajriqama,dhuhr,dhuhriqama," +
                "asr,asriqama,maghrib,maghribiqama,isha,ishaiqama,hadith)" +
                " values (?,?,?,?,?,?,?,?,?,?,?,?);";


        try (
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, info.getDate().toString());
            pstmt.setString(2, (info.getFajr().toString()));
            pstmt.setString(3, (info.getFajrIqama().toString()));
            pstmt.setString(4, (info.getDhuhr().toString()));
            pstmt.setString(5, (info.getDhuhrIqama().toString()));
            pstmt.setString(6, (info.getAsr().toString()));
            pstmt.setString(7, (info.getAsrIqama().toString()));
            pstmt.setString(8, (info.getMagrib().toString()));
            pstmt.setString(9, (info.getMagribIqama().toString()));
            pstmt.setString(10, (info.getIsha().toString()));
            pstmt.setString(11, info.getIshaIqama().toString());
            pstmt.setString(12, (info.getHadith()));

            pstmt.executeUpdate();
        } catch (SQLException e) {
            AlertWindow.display("Error" , e.getMessage());
            Logger.Error(DatabaseConnection.class.getName(), "writeDay", e.getMessage(), e);
        }
    }

    public void writeDays(ArrayList<DayInfo> days) {
        if (days == null) {
            throw new IllegalArgumentException("days is null");
        }
        for (DayInfo i : days) {
            writeDay(i);

        }
    }

    public DayInfo findDay(LocalDate date) {
        if (!isOpen) {
            throw new IllegalStateException("closed Connection");
        }

        String sql = "SELECT * FROM prayer WHERE day = ?";
        DayInfo dayInfo = null;
        try (
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, date.toString());

            ResultSet result = pstmt.executeQuery();

            dayInfo = new DayInfo(
                    result.getString("day"),
                    result.getString("fajr"),
                    result.getString("fajriqama"),
                    result.getString("dhuhr"),
                    result.getString("dhuhriqama"),
                    result.getString("asr"),
                    result.getString("asrIqama"),
                    result.getString("maghrib"),
                    result.getString("maghribiqama"),
                    result.getString("isha"),
                    result.getString("ishaiqama"),
                    result.getString("Hadith")
            );
        } catch (SQLException e) {
            AlertWindow.display("Error", e.getMessage() + " couldn't find day in the Database!");
            Logger.Error("DatabaseConnencton", "findday", e.getMessage(), e);
        }

        return dayInfo;
    }

    public void deleteAll() {

        if (!isOpen) {
            throw new IllegalStateException("Closed Connection");
        }
        String sql = "DELETE FROM prayer;";

        try (

                PreparedStatement pstmt = conn.prepareStatement(sql)) {


            pstmt.executeUpdate();
        } catch (SQLException e) {
            AlertWindow.display("Error" , e.getMessage());
            Logger.Error(DatabaseConnection.class.getName() , "deleteAll" , e.getMessage() ,e);
        }
    }

    public void setFriday(String time) {
        if (!isOpen) {
            throw new IllegalStateException("Closed Connection!");
        } else {
            String sql = "UPDATE Friday Set time = ? where id = 0";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, time);
                pstmt.executeUpdate();
                Logger.info(DatabaseConnection.class.getName(), "freitagsgebet", "friday prayer is set!");
            } catch (SQLException e) {
                AlertWindow.display("Error" , e.getMessage());
                Logger.Error(DatabaseConnection.class.getName() , "setFriday" , "couldn't set Friday time" , e);
            }
        }
    }

    public String getFriday() {
        if (!isOpen) {
            throw new IllegalStateException("Closed Connection!");
        } else {
            String sql = "select time from Friday where id = 0";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                ResultSet result = pstmt.executeQuery();
                return result.getString("time");
            } catch (SQLException e) {
                AlertWindow.display("Error" , "friday time is not set!");
                Logger.Error(DatabaseConnection.class.getName() , "getFriday" , "friday time is not set!",e);
                return "00:00";
            }
        }
    }

    @Override
    public void close() {
        isOpen = false;
        try {
            if (conn != null) {
                conn.close();
                Logger.info(DatabaseConnection.class.getName(), "close", "connection with database is closed successfully!");
            }
        } catch (SQLException e) {
            AlertWindow.display("Error", e.getMessage());
            Logger.Error(DatabaseConnection.class.getName(), "close", e.getMessage(), e);
        }
    }
}
