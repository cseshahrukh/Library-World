package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import startup.DbConn;
import java.sql.ResultSet;

public class SecuritymoneyController {
    public TextField usernamefld;
    public void okbtnclicked(ActionEvent actionEvent) {
        if(usernamefld.getText().compareTo("")!=0)
        {
            String query1=String.format("Update users SET isactive='y' WHERE username='%s'", usernamefld.getText());
            String query2=String.format("Select * from users where username='%s'", usernamefld.getText());
            DbConn oc=null;
            try
            {
                oc=new DbConn();
                ResultSet rs=oc.searchDB(query2);
                if(rs.next())
                {
                    String query3=String.format("Select isactive from users where username='%s'", usernamefld.getText());
                    ResultSet rs2=oc.searchDB(query3);
                    rs2.next();
                    String isActive=rs2.getNString("isactive");
                    if (isActive.equals("y"))
                    {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("Already activated");
                        alert.show();
                    }
                    else
                    {
                        oc.updateDB(query1);
                        String query4=String.format("INSERT into PAYMENTS values('%s',%d,sysdate)",usernamefld.getText(),500);
                        oc.updateDB(query4);

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("Successfully Activated");
                        alert.show();
                    }

                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("No user with this username");
                    alert.show();
                }
            }
            catch (Exception e)
            {
                System.out.println("problem in connection");
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please give username");
            alert.show();
        }
    }

    public void homebtnclicked(ActionEvent actionEvent) throws Exception{
        Stage stage= (Stage) usernamefld.getScene().getWindow();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/adminhomepage.fxml"));
        Parent root = loader.load();
        stage.setTitle("Library Management System");
        stage.setScene(new Scene(root,723,505));
        stage.setResizable(false);
        stage.show();
    }
}
