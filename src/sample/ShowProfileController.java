package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;



import java.sql.ResultSet;

public class ShowProfileController {

    public Main main;
    public String username;


    @FXML
    public TextField namefld;

    public TextField usernamefld;
    public TextField peopleidfld, emailfld, addressfld,
    phonefld, dobfld, activefld, borrowfld;

    @FXML
    public TextField finefld;
    public TextField expiredatefld;
    public PasswordField passwordfld;

    public void setMain(Main main) {
        this.main = main;
    }

    public void load(String s)
    {
        //s hocche username

        username=s;

        OracleConnect oc = null;
        try {
            oc = new OracleConnect();
            String query = String.format("select peopleID, username, password, to_char(expiredate, 'DD-MM-YYYY') expiredate from users where username = '%s'", username);
            ResultSet rs = oc.searchDB(query);
            rs.next();
            String peopleID=rs.getString("peopleid");
            passwordfld.setText(rs.getString("password"));
            query = String.format("select name, email, address, phone, to_char(dateofbirth, 'DD-MM-YYYY') dateofbirth from people where id = '%s'", peopleID);
            ResultSet rs2 = oc.searchDB(query);
            rs2.next();






            peopleidfld.setText(peopleID);

            namefld.setText(rs2.getString("name"));
            emailfld.setText(rs2.getString("email"));
            addressfld.setText(rs2.getString("address"));
            phonefld.setText(rs2.getString("phone"));
            usernamefld.setText(rs.getString("username"));
            expiredatefld.setText(rs.getString("expiredate"));
            dobfld.setText(rs2.getString("dateofbirth"));



            query = String.format("select isactive FROM users where username='%s'", username);
            rs2 = oc.searchDB(query);
            rs2.next();
            String isActive=rs2.getString("isactive");

            if (isActive.equals("n"))
            {
                activefld.setText("You are not active. Come library with to be active.");
                finefld.setText("0");
                borrowfld.setText("You can't borrow a book now.");
            }
            else
            {
                activefld.setText("You are active.");
                String fine="0";
                query = String.format("select * FROM Borrowbook where username='%s' AND expreturndate<sysdate AND returndate is null", username);
                rs2 = oc.searchDB(query);
                if (rs2.next())
                    fine="20";

                finefld.setText(fine);


                query = String.format("select * FROM Borrowbook where username='%s' AND  returndate is null", username);
                rs2 = oc.searchDB(query);
                if(rs2.next())
                    borrowfld.setText("You can't borrow a book now.");
                else
                {
                    query = String.format("select * FROM users where username='%s' AND expiredate>sysdate", username);
                    rs2 = oc.searchDB(query);
                    if(rs2.next())
                        borrowfld.setText("You can borrow book! ");
                    else
                        borrowfld.setText("You can't borrow!");

                }

            }








        } catch (Exception e) {
            System.out.println("Exception in load of showProfileController " + e);
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void back(javafx.event.ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("logged-in.fxml"));
        Parent root = loader.load();
        LoggedInController controller=loader.getController();

        controller.setMain(this.main);
        controller.load(username);



        main.stage.setTitle("Login");
        main.stage.setScene(new Scene(root, 800, 680));
        main.stage.show();
    }

    public void update(javafx.event.ActionEvent event) throws Exception{
        //only email address phone

        OracleConnect oc = null;
        try {
            oc = new OracleConnect();
            String query = String.format("select * from users where username = '%s'", username);
            ResultSet rs = oc.searchDB(query);
            rs.next();
            String peopleID=rs.getString("peopleid");
            rs.next();
            if (emailfld.getText().compareTo("")!=0 && addressfld.getText().compareTo("")!=0 && phonefld.getText().compareTo("")!=0 &&passwordfld.getText().compareTo("")!=0)
            {
                String updatequery=String.format("update people set email='%s', address='%s', phone='%s' where id='%s'", emailfld.getText(), addressfld.getText(), phonefld.getText(), peopleID);
                oc.updateDB(updatequery);

                updatequery=String.format("update users set password='%s' where username='%s'", passwordfld.getText(), username);
                oc.updateDB(updatequery);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Successfully Updated");
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




        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("showProfile.fxml"));
        Parent root = loader.load();
        ShowProfileController controller=loader.getController();

        controller.setMain(this.main);
        controller.load(username);



        main.stage.setTitle("Your Profile");
        main.stage.setScene(new Scene(root, 800, 680));
        main.stage.show();
    }

    public void logout(javafx.event.ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Controller controller=loader.getController();

        controller.setMain(this.main);
        //controller.load(username);



        main.stage.setTitle("Login");
        main.stage.setScene(new Scene(root, 800, 680));
        main.stage.show();
    }

    public void history(javafx.event.ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("history.fxml"));
        Parent root = loader.load();
        HistoryController controller=loader.getController();

        controller.setMain(this.main);
        controller.load(username);



        main.stage.setTitle("Login");
        main.stage.setScene(new Scene(root, 900, 680));
        main.stage.show();
    }


}
