package main;

import database.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private DatabaseHandler dbHandler;

    @Override
    public void start(Stage primaryStage) {
        try {
            dbHandler = new DatabaseHandler();
            dbHandler.createTables();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            primaryStage.setTitle("JDS Car Rentals - Login");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if(dbHandler != null){
            dbHandler.closeConnection();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
