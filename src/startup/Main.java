package startup;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        /*stage=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/adminhomepage.fxml"));
        stage.setTitle("Library Management System");
        stage.setScene(new Scene(root, 723,505));
        //stage.setScene(new Scene(root, 900,700));
        stage.setResizable(false);
        stage.show();*/
        stage=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/login.fxml"));
        //stage.setTitle("Library Management System");
        stage.setScene(new Scene(root, 600,400));
        //stage.setScene(new Scene(root, 900,700));
        stage.setResizable(false);
        stage.show();

    }


    public static void main(String[] args) {
        launch(args);

    }


}
