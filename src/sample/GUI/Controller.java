package sample.GUI;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import sample.Animation.Notification;
import sample.DatabaseManager.DatabaseConnection;
import sample.ExcelManager.DayInfo;
import sample.ExcelManager.Importer;
import sample.ExcelManager.ImporterResult;
import sample.ImagesManager.Background;
import sample.ImagesManager.BackgroundType;
import sample.LoggingManager.Logger;
import sample.TimeManager.Formater;
import sample.TimeManager.Prayer;
import sample.TimeManager.PrayerType;


public class Controller implements Initializable {

    public Label fajrLabel;
    public Label dhuhrLabel;
    public Label asrLabel;
    public Label magribLabel;
    public Label ishaLabel;
    public ImageView img;
    public Label clk;
    public Label hadith;
    public Label dte;
    public GridPane gridPane;
    public AnchorPane basePane;
    public Label clkSeconds;
    public MenuBar menu;
    public MenuItem importMenu;
    public MenuItem closeMenu;
    public MenuItem aboutMenu;
    public Label fajrIqamaLabel;
    public Label dhuhrIqamaLabel;
    public Label asrIqamaLabel;
    public Label magribIqamaLabel;
    public Label ishaIqamaLabel;
    public Label dteArabic;
    public Label tagArabic;
    public Label tag;
    public Label freitagsgebetLabel1;
    public Label freitagsgebetLabel2;
    public TextField freitagsgebetField;
    public Label fajrIqamaLabel1;
    public Label dhuhrIqamaLabel1;
    public Label asrIqamaLabel1;
    public Label magribIqamaLabel1;
    public Label ishaIqamaLabel1;
    Prayer currPrayer;


    Stage stage;
    ImporterResult result;
    DayInfo today;
    ArrayList<DayInfo> dayInfos = new ArrayList<>();


    Image prayerImg = new Image("sample/resources/Images/main.jpg");
    Image asrImage = new Image("sample/resources/Images/notifyAsr.jpg");
    Image dhuhrImage = new Image("sample/resources/Images/notifyDhuhr.jpg");
    Image fajrImage = new Image("sample/resources/Images/notifyFajr.jpg");
    Image ishaImage = new Image("sample/resources/Images/notifyIsha.jpg");
    Image magribImage = new Image("sample/resources/Images/notifyMaghrib.jpg");

    ArrayList<Prayer> prayers = new ArrayList<>(5);

    Prayer fajr = new Prayer();
    Prayer dhuhr = new Prayer();
    Prayer asr = new Prayer();
    Prayer maghrib = new Prayer();
    Prayer isha = new Prayer();

    Notification notification;
    DatabaseConnection connection = new DatabaseConnection();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Logger.getLogger();

        fajr = new Prayer(PrayerType.Fajr, fajrLabel, fajrIqamaLabel1, fajrImage);
        dhuhr = new Prayer(PrayerType.Dhuhr, dhuhrLabel, dhuhrIqamaLabel1, dhuhrImage);
        asr = new Prayer(PrayerType.Asr, asrLabel, asrIqamaLabel1, asrImage);
        maghrib = new Prayer(PrayerType.Magrib, magribLabel, magribIqamaLabel1, magribImage);
        isha = new Prayer(PrayerType.Isha, ishaLabel, ishaIqamaLabel1, ishaImage);

        fajrIqamaLabel.visibleProperty().bind(fajrIqamaLabel1.visibleProperty());
        dhuhrIqamaLabel.visibleProperty().bind(dhuhrIqamaLabel1.visibleProperty());
        asrIqamaLabel.visibleProperty().bind(asrIqamaLabel1.visibleProperty());
        magribIqamaLabel.visibleProperty().bind(magribIqamaLabel1.visibleProperty());
        ishaIqamaLabel.visibleProperty().bind(ishaIqamaLabel1.visibleProperty());

        prayers.add(fajr);
        prayers.add(dhuhr);
        prayers.add(asr);
        prayers.add(maghrib);
        prayers.add(isha);

        notification = new Notification(img, new Background(prayerImg, BackgroundType.PrayerBackground));
        img.setImage(prayerImg);


        refreshValues();


        Timeline clockTimer = new Timeline(

                new KeyFrame(Duration.seconds(0), new EventHandler<javafx.event.ActionEvent>() {
                    @Override
                    public void handle(javafx.event.ActionEvent event) {

                        LocalDateTime localDateTime = LocalDateTime.now();
                        LocalDate localDate = localDateTime.toLocalDate();
                        LocalTime localTime = localDateTime.toLocalTime();
                        String digitalClock = localTime.format(DateTimeFormatter.ofPattern("HH:mm"));
                        String seconds = localTime.format(DateTimeFormatter.ofPattern("ss"));
                        clkSeconds.setText(seconds);
                        LocalDate currDay = localDate;
                        clk.setText(digitalClock);
                        if (today != null) {
                            findPrayer(localTime);
                            if (!currDay.equals(today.getDate())) {
                                refreshValues();
                            }
                        }
                    }
                })

                , new KeyFrame(Duration.seconds(1))

        );
        clockTimer.setCycleCount(Animation.INDEFINITE);

        if (today != null) {

            clockTimer.play();

        } else {
            AlertWindow.display("Error", "Today is not found in the database, please import an excel file with the right data!");
        }


        importMenu.setOnAction(e -> {
            try {
                Importer fileImporter = new Importer();
                result = fileImporter.importFromFile(stage);
                if (result != null){
                    dayInfos = result.getDays();
                    connection.open();
                    connection.deleteAll();
                    connection.writeDays(dayInfos);
                    connection.close();
                    refreshValues();
                }

                if (clockTimer.getStatus() != Animation.Status.RUNNING)
                    clockTimer.play();
            } catch (Exception ex) {
                AlertWindow.display("Error", ex.getMessage());
                Logger.Error(Controller.class.getName(), "Import from file", ex.getMessage(), ex);
            }
        });

        closeMenu.setOnAction(event -> {
            stage.close();
            Logger.close(true);
        });


        aboutMenu.setOnAction(event -> {
                    AlertWindow.display("Help", " Press F11 for fullscreen." +
                            "\n Press Esc to exit fullscreen." +
                            "\n To import an excel file go to file menu, press import and choose the file." +
                            "\n To change friday prayer's time go to Edit menu and Type the new time in the text field in HH:mm format" +
                            "\n For help and support please contact me Tel: 015175627096 E-mail: m.alkhufash@gmail.com ");
                }
        );

        freitagsgebetField.setOnAction(event -> {
            try {
                String freitagsgebet = freitagsgebetField.getText();
                DateTimeFormatter.ofPattern("HH:mm").parse(freitagsgebet);
                freitagsgebetLabel1.setText(freitagsgebet);
                freitagsgebetLabel2.setText(freitagsgebet);
                connection.open();
                connection.setFriday(freitagsgebet);
                connection.close();

            } catch (DateTimeParseException e) {
                AlertWindow.display("Error", " Bitte eine gultige Zeit im Format \"HH:mm\" eingeben! " +
                        "\n Please insert a valid time of Format \"HH:MM\"!");
                Logger.info(Controller.class.getName(), "setfreitagsgebet", e.getMessage());
            }

        });


    }


    private void findPrayer(LocalTime time) { //finds the right prayer and returns it back
        int hours = time.getHour();
        int min = time.getMinute();
        boolean found = false;

        for (Prayer prayer : prayers) { // goes through the prayers and checks the time
            if (prayer.isSet() && prayer.getHour() == hours && prayer.getMinute() == min) {
                found = true;
                if ((currPrayer == null || prayer.getHour() != currPrayer.getHour()
                        && prayer.getMinute() != currPrayer.getMinute())) {
                    currPrayer = prayer;
                    notification.setPrayer(currPrayer);
                    notification.play();
                }

            }
        }

        if (!found) {
            currPrayer = null;
        }
    }


    private void refreshValues() { //gets the current day and refreshes the labels

        try {
            connection.open();
            today = connection.findDay(LocalDate.now());
            String freitagsgebet = connection.getFriday();
            connection.close();
            if (today != null) {
                LocalDate date = today.getDate();
                fajr.setPrayerTime(today.getFajr(), today.getFajrIqama());
                dhuhr.setPrayerTime(today.getDhuhr(), today.getDhuhrIqama());
                asr.setPrayerTime(today.getAsr(), today.getAsrIqama());
                maghrib.setPrayerTime(today.getMagrib(), today.getMagribIqama());
                isha.setPrayerTime(today.getIsha(), today.getIshaIqama());
                hadith.setText(today.getHadith());
                dte.setText(date.format(DateTimeFormatter.ofPattern("dd.MM.YYYY")));
                dteArabic.setText(Formater.getHijriDate());
                tag.setText(Formater.getGermanDay());
                tagArabic.setText(Formater.getHijriDay());
                freitagsgebetLabel1.setText(freitagsgebet);
                freitagsgebetLabel2.setText(freitagsgebet);
            }
        } catch (IllegalStateException e) {
            connection.close();
            AlertWindow.display("Error", e.getMessage());
            Logger.Error(Controller.class.getName(), "refreshvalues", e.getMessage(), e);

        }

    }

    public void fullScreen() {
        stage.setFullScreen(true);
    }

    public void setStage(Stage s) {
        stage = s;
        img.fitWidthProperty().bind(stage.widthProperty());
        stage.fullScreenProperty().addListener( // auto-hide menu bar when full screen
                e -> {
                    menu.setVisible(!stage.isFullScreen());
                }
        );
    }
}
