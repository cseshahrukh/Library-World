package controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.signUpController;
import startup.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.ResultSet;

public class adminLoginController {
    public TextField tf_user;
    public PasswordField tf_password;


    public static boolean validUser(String user_name)
    {
        System.out.println("inside valid user printing user_name "+user_name);
        String password; boolean flag=false;
        DbConn oc = null;
        try {
            //System.out.println("Inside try89");
            oc = new DbConn();
            //System.out.println("b");
            String query = String.format("select loginid from admin");
            //System.out.println("c");
            ResultSet rs = oc.searchDB(query);
            //System.out.println("d");
            while (rs.next()) {
                System.out.println(rs.getString("loginid"));
                System.out.println("printing under valid user" +user_name);
                if (user_name.equals(rs.getString("loginid")))
                {
                    flag=true;
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("Exception in Oracle Connect: " + e);
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (flag)
            System.out.println("this is valid admin ");

        else
            System.out.println("this is not valid admin ");
        return flag;
    }

    public static String getPassword(String user_id)
    {
        //System.out.println("Inside getPassword user_id is "+ user_id);
        String password="";
        DbConn oc = null;
        try {
            oc = new DbConn();
            String query = String.format("select password from admin where loginid='%s'", user_id);
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                password=rs.getString("password");
            }
        } catch (Exception e) {
            System.out.println("Exception in password Search: " + e);
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return password;
    }

    public void loginAction(ActionEvent actionEvent) throws Exception{
        String username=tf_user.getText();
        String password=tf_password.getText();
        System.out.println("inside loginUser printing username"+ username);

        if (!validUser(username))
        {
            System.out.println("AdminUserName Not Found");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("AdminIDNotFound");
            alert.show();

        }
        String actualPass=getPassword(username);
        if (actualPass.compareTo(password)!=0)
        {
            System.out.println("Password Not Found");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("PasswordNotFound");
            alert.show();
        }

        else
        {
            Stage stage= (Stage) tf_user.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/adminhomepage.fxml"));
            stage.setTitle("Library Management System");
            stage.setScene(new Scene(root, 723,505));
            //stage.setScene(new Scene(root, 900,700));
            stage.setResizable(false);
            stage.show();

        }
    }

    public void back(ActionEvent actionEvent) throws Exception
    {
        Stage stage= (Stage) tf_user.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../sample/sample.fxml"));
        stage.setTitle("Library Management System");
        stage.setScene(new Scene(root, 600,400));
        //stage.setScene(new Scene(root, 900,700));
        stage.setResizable(false);
        stage.show();
    }
}
