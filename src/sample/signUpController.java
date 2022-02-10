package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class signUpController {


    private Main main;

    @FXML
    private TextField name;

    @FXML
    private TextField password;

    @FXML
    private TextField username;

    @FXML
    private TextField email;








        public void showHome(javafx.event.ActionEvent event) throws Exception{
            main.home("sample.sample.fxml", null);
        }

        void setMain(Main main) {
            this.main = main;
        }

    public void signupAction(javafx.event.ActionEvent actionEvent) throws Exception {
        System.out.println("Name is "+name.getText());
        System.out.println("username is "+username.getText());
        System.out.println("password is "+password.getText());
        if (!username.getText().trim().isEmpty() && !password.getText().trim().isEmpty())
        {
            main.signUpUser(username.getText(), password.getText());
        }
        else
        {
            System.out.println("Please fill in all information");
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setContentText("Please fill in all information to sign up");
            alert.show();
        }
    }
}
