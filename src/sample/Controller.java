package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class Controller  {

    private Main main;
    @FXML
    private Button button_login;

    @FXML
    private Button button_sign_up;

   @FXML
   private TextField tf_user;

    @FXML
    private PasswordField tf_password;

    @FXML
    void loginAction(ActionEvent event) throws IOException {
        //tf_username=event.
        System.out.println("tf_user is "+tf_user.getText());
        if (tf_user.getText().compareTo("") == 0)
        {
            System.out.println("vai eita null");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Give A UserName");
            alert.show();
        }
        else
        {
            String user=tf_user.getText();
            System.out.println("loginAction er vitor user name ta holo "+user);
            main.logInUser(tf_user.getText(), tf_password.getText());
        }
        //System.out.println("Printing in loginAction and username");
        //System.out.println(tf_user.getText());
        //System.out.println(tf_password.getText());

    }

    public void sign_upAction() throws Exception {

        main.signUpHome();
        //main.changeScene("sign-up.sample.fxml", "Sign Up!", null);
    }

    public void admin(ActionEvent event) throws IOException
    {
        Stage stage=(Stage)button_login.getScene().getWindow();
        //main.stage=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/login.fxml"));
        stage.setTitle("Library Management System");
        stage.setScene(new Scene(root, 600,400));
        //stage.setScene(new Scene(root, 900,700));
        stage.setResizable(false);
        stage.show();
    }




    void setMain(Main main) {
        this.main = main;
    }
}
