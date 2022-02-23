package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import startup.Book;
import startup.DbConn;
import startup.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class showuserController {
    public TextField usernamefld;
    public TableView<User> usertbl;
    public TableColumn<User,String> usernameclm;
    public TableColumn<User,String> nameclm;
    public TableColumn<User,String> peopleidclm;
    public TableColumn<User,String> emailclm;
    public TableColumn<User,String> phoneclm;
    public TableColumn<User,String> addressclm;
    public TableColumn<User,String> expireclm;

    ObservableList<User> list= FXCollections.observableArrayList();

    private void setTable()
    {
        usernameclm.setCellValueFactory(new PropertyValueFactory<User,String>("username"));
        nameclm.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
        peopleidclm.setCellValueFactory(new PropertyValueFactory<User,String>("peopleid"));
        emailclm.setCellValueFactory(new PropertyValueFactory<User,String>("email"));
        phoneclm.setCellValueFactory(new PropertyValueFactory<User,String>("phone"));
        addressclm.setCellValueFactory(new PropertyValueFactory<User,String>("address"));
        expireclm.setCellValueFactory(new PropertyValueFactory<User,String>("expiredate"));
        usertbl.setItems(list);

    }

    public void okbtnclicked(ActionEvent actionEvent) {
        DbConn oc;
        User us=null;
        list.clear();
        try {
            oc = new DbConn();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return;
        }
        String searchname = usernamefld.getText().trim();
        searchname = "%" + searchname + "%";

        try {
            String query = String.format("SELECT username,peopleid,to_char(expiredate,'DD-MM-YYYY') expire FROM USERS \n" +
                    "where username LIKE '%s'", searchname);
            ResultSet rs = oc.searchDB(query);
            int count=0;
            while (rs.next()) {
                us=new User();
                us.username=rs.getString("username");
                us.peopleid=rs.getString("peopleid");
                us.expiredate=rs.getString("expire");

                String query1 = String.format("select name,email,address,phone from people where id='%s'",us.peopleid);
                ResultSet rs1 = oc.searchDB(query1);
                rs1.next();
                us.name=rs1.getString("name");
                us.email=rs1.getString("email");
                us.address=rs1.getString("address");
                us.phone=rs1.getString("phone");
                list.add(us);
                count++;
            }
            if(count==0)
            {
                System.out.println("No user found");

            }
            else
            {
                setTable();

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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

    public void backbtnclicked(ActionEvent actionEvent) throws Exception {
        Stage stage= (Stage) usernamefld.getScene().getWindow();
        //stage.hide();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/getinfopage.fxml"));
        Parent root = loader.load();
        //stage.setTitle("Add books Page");
        stage.setScene(new Scene(root,665,482));
        stage.show();
    }
}
