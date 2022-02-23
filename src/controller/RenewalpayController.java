package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import startup.DbConn;

import java.sql.ResultSet;

public class RenewalpayController {
    public TextField monthfld;
    public TextField amountfld;
    public PaymentpageController parent;
    public String user;
    public int months;

    public void confirmbtnclicked(ActionEvent actionEvent) {
        DbConn oc;
        try {
            oc = new DbConn();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return;
        }
        try {
            //String query1 = String.format("Select expiredate from users where username is '%s'", user);
            //ResultSet rs=oc.searchDB(query1);
            //rs.next();


            String query = String.format("UPDATE Users \n" +
                    "SET expiredate=expiredate+%d*30 WHERE username='%s'",months, user);
            oc.updateDB(query);

            String query1=String.format("INSERT into PAYMENTS values('%s',%d,sysdate)",user,months*100);
            oc.updateDB(query1);

            Stage stage= (Stage)monthfld.getScene().getWindow();
            stage.hide();
            parent.validexpiredlbl.setVisible(false);
            parent.validnotexpiredlbl.setVisible(true);

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Expiredate has been modified");
            a.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void okbtnclicked(ActionEvent actionEvent) {
        months=Integer.parseInt(monthfld.getText().trim());
        amountfld.setText(String.valueOf(months*100));
    }
}
