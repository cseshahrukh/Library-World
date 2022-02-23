package sample;


import javafx.fxml.FXML;

import javafx.scene.control.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class signUpController {


    private Main main;

    @FXML
    private TextField namefld;

    @FXML
    private TextField passwordfld;

    @FXML
    private TextField usernamefld;

    @FXML
    private TextField emailfld;

    @FXML
    private TextField peopleidfld;

    @FXML
    private TextField addressfld;

    @FXML
    private TextField phonefld;

    @FXML
    private DatePicker dobfld;








    public void showHome(javafx.event.ActionEvent event) throws Exception{
        main.home("sample.sample.fxml", null);
    }

    public void setMain(Main main) {
            this.main = main;
        }

    public void signupAction(javafx.event.ActionEvent actionEvent) throws Exception {
        if (peopleidfld.getText().trim().isEmpty() && (addressfld.getText().trim().isEmpty() ||
                phonefld.getText().trim().isEmpty() || dobfld.toString().trim().isEmpty()))
        {
            System.out.println("Please fill in all information");
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setContentText("Please fill in all information to sign up");
            alert.show();
        }


        else if (!peopleidfld.getText().trim().isEmpty())
        {
                OracleConnect oc = null;
                try {
                    oc = new OracleConnect();
                    String query = String.format("select * from people where id = '%s'", peopleidfld.getText());
                    ResultSet rs = oc.searchDB(query);

                    //People table e er row ta ache
                    if (rs.next()) {

                        //System.out.println("Password Not Found");

                        String name, email;
                        name=rs.getString("name");
                        name=name.toLowerCase();
                        email=rs.getNString("email");

                        //name or email ta faka rekheche
                        if (name.compareTo(namefld.getText().toLowerCase())!=0 || email.compareTo(emailfld.getText())!=0)
                        {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Name or Email doesn't match with our database");
                            alert.show();
                            return;
                        }


                        //People ID ta diye already ekta user create kora ache
                        query = String.format("select * from users where peopleid = '%s'", peopleidfld.getText());
                        rs=oc.searchDB(query);
                        if (rs.next())
                        {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("You are already registered with this peopleID.");
                            alert.show();
                            return;
                        }


                        //Same username diye onno keu khule feleche
                        query = String.format("select * from users where username = '%s'", usernamefld.getText());
                        rs = oc.searchDB(query);

                        if (rs.next())
                        {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("The username is already taken!");
                            alert.show();
                        }


                        //Creating new user with this people ID
                        else
                        {
                                //done

                                System.out.println("Before insert into users");
                                String query1 = String.format("insert into users (username,password, peopleid, expiredate) " +
                                        "values ('%s', '%s', '%s', sysdate)", usernamefld.getText(), passwordfld.getText(), peopleidfld.getText());
                                oc.updateDB(query1);
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setContentText("Success!!");
                                alert.show();
                        }

                    }

                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("No man with this people id");
                        alert.show();
                    }

                    System.out.println("Product with this Id already exisits");
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




        //We need a new peopleID
        else if (!usernamefld.getText().trim().isEmpty()
                && !passwordfld.getText().trim().isEmpty()
        && !namefld.getText().trim().isEmpty()
                && !emailfld.getText().trim().isEmpty()
        && !addressfld.getText().trim().isEmpty()
                && !phonefld.getText().trim().isEmpty()
                && dobfld!=null
                && !dobfld.toString().trim().isEmpty()
                && !dobfld.getEditor().getText().isEmpty()
                && !dobfld.getValue().toString().isEmpty())
        {

            System.out.println("Eibar kaj kora uchit ");
            //main.signUpUser(username.getText(), password.getText());


            OracleConnect oc = null;
            try {
                oc = new OracleConnect();
                String query = String.format("select * from users where username = '%s'", usernamefld.getText());
                ResultSet rs = oc.searchDB(query);
                if (rs.next()) {
                    //System.out.println("Password Not Found");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("UserName Already in Database");
                    alert.show();
                } else {
                    System.out.println("Inside else caluse ");
                    query = String.format("SELECT NVL(max(TO_NUMBER(ID))+1, 1) hey from PEOPLE");
                    rs = oc.searchDB(query);
                    rs.next();
                    System.out.println("After query search");
                    String peopleID=rs.getString("HEY");
                    System.out.println("Before date initializer");
                    //System.out.println("insdie else of addUser");
                    String date=dobfld.getValue().toString();
                    System.out.println("printing date format "+date);
                    System.out.println("Date is "+date);
                    String insertQuery = String.format(
                            "insert into users (username, password, peopleID, isactive, expiredate) values ('%s', '%s', '%s', 'n', sysdate)", usernamefld.getText(), passwordfld.getText(), peopleID);

                    oc.updateDB(insertQuery);

                    insertQuery = String.format(
                            "insert into People  values ('%s', '%s', '%s', '%s', '%s', TO_DATE('%s', 'YYYY-MM-DD'))", peopleID, usernamefld.getText(), emailfld.getText(), addressfld.getText(), phonefld.getText(), date);

                    oc.updateDB(insertQuery);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("UserName added");
                    alert.show();
                }
                System.out.println("Product with this Id already exisits");
            }
            catch (SQLException sql)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Input a valid date. Example 18-02-2022");
                alert.show();
            }
            catch (Exception e) {
                System.out.println("Exception in addProduct: " + e);
            } finally {
                try {
                    oc.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        else if (dobfld.toString().compareTo("")==0)
        {
            System.out.println("Vai eita keno jeno empty string hoye gelo ");
        }


        else
        {
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setContentText("Please fill in all information to sign up");
            alert.show();
        }
    }
}
