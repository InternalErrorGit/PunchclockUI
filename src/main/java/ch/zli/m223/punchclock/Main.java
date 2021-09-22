package ch.zli.m223.punchclock;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author P. Gatzka
 * @version 22.09.2021
 * Project: PunchclockUI
 * Class: App
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        RequestHandler.getAll();

        Parent root = new PunchclockUI();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("PunchclockUI");
        stage.show();


    }
}
