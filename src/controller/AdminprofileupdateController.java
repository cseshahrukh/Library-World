package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.OracleConnect;

import java.sql.ResultSet;

public class AdminprofileupdateController {

    public TextField namefld;
    public TextField adminidfld;
    public TextField dobfld;
    public TextField phonefld;
    public TextField addressfld;
    public TextField emailfld;
    public TextField peopleidfld;
    public TextField passswordfld;
    public String pid;

    public void load()
    {
        String adminid=adminLoginController.currentadminid;
        OracleConnect oc = null;
        try {
            oc = new OracleConnect();
            String query = String.format("select * from Admin where loginid='%s'",adminid);
            ResultSet rs = oc.searchDB(query);
            rs.next();
            String peopleID=rs.getString("peopleid");
            adminidfld.setText(adminid);
            passswordfld.setText(rs.getString("password"));
            pid=peopleID;

            query = String.format("select name,email,address,phone,to_char(dateofbirth,'DD-MM-YYYY') dbirth from people where id = '%s'", peopleID);
            ResultSet rs2 = oc.searchDB(query);
            rs2.next();

            peopleidfld.setText(peopleID);
            namefld.setText(rs2.getString("name"));
            emailfld.setText(rs2.getString("email"));
            addressfld.setText(rs2.getString("address"));
            phonefld.setText(rs2.getString("phone"));
            dobfld.setText(rs2.getString("dbirth"));


        } catch (Exception e) {
            System.out.println("Exception in addProduct: " + e);
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void back(ActionEvent actionEvent) throws Exception {
        Stage stage= (Stage) namefld.getScene().getWindow();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/adminhomepage.fxml"));
        Parent root = loader.load();
        stage.setTitle("Library Management System");
        stage.setScene(new Scene(root,723,505));
        stage.setResizable(false);
        stage.show();
    }

    public void update(ActionEvent actionEvent) {
        OracleConnect oc = null;
        String adminid=adminLoginController.currentadminid;
        try {
            oc = new OracleConnect();

            if (emailfld.getText().compareTo("")!=0 && addressfld.getText().compareTo("")!=0 && phonefld.getText().compareTo("")!=0 && passswordfld.getText().compareTo("")!=0)
            {
                String updatequery=String.format("update people set email='%s', address='%s', phone='%s' where id='%s'", emailfld.getText(), addressfld.getText(), phonefld.getText(), pid);
                oc.updateDB(updatequery);

                String updatequery1=String.format("update ADMIN set password='%s' where loginid='%s'",passswordfld.getText(),adminid);
                oc.updateDB(updatequery1);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Information updated successfully");
                alert.show();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Don't keep the fields empty");
                alert.show();
                return;
            }

        } catch (Exception e) {
            System.out.println("Exception in addProduct: " + e);
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
