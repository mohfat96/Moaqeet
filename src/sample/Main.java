package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.GUI.Controller;
import sample.LoggingManager.Logger;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Font.loadFont(getClass().getResource("resources/Fonts/DecoType Naskh Extensions.ttf").toExternalForm() , 10);
        Font.loadFont(getClass().getResource("resources/Fonts/DecoType Naskh Swashes.ttf").toExternalForm() , 10);
        Font.loadFont(getClass().getResource("resources/Fonts/DTNASKH2.TTF").toExternalForm() , 10);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GUI/Slideshow.fxml"));
        Parent root = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        controller.setStage(primaryStage);
        primaryStage.setTitle("IGW");
        Scene scene = new Scene(root, 600, 338);
        scene.getStylesheets().add("sample/GUI/style.css");
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F11) {
                controller.fullScreen();
            }
        });
        primaryStage.setScene(scene);
        controller.fullScreen();
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            Logger.close(true);
        });


    }


    public static void main(String[] args) {
        launch(args);
    }

}
