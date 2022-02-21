package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminhomepageController {
    public Button bookborrowbtn;
    public Button returnbookbtn;
    public Button sellbookbtn;
    public Button addbookbtn;

    public void bookborrowbtnclicked(ActionEvent actionEvent) throws Exception {
        Stage stage= (Stage) bookborrowbtn.getScene().getWindow();
        //stage.hide();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/bookborrow.fxml"));
        Parent root = loader.load();
        stage.setTitle("Book Borrow Page");
        stage.setScene(new Scene(root,762,570));
        stage.show();
        //ManufLoginController manufcontrol=loader.getController();
       // manufcontrol.setClient(c);

    }

    public void returnbooksbtnclicked(ActionEvent actionEvent) throws Exception{
        Stage stage= (Stage) bookborrowbtn.getScene().getWindow();
        //stage.hide();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/returnbook.fxml"));
        Parent root = loader.load();
        stage.setTitle("Book Return Page");
        stage.setScene(new Scene(root,762,570));
        stage.show();
    }

    public void sellbooksbtnclicked(ActionEvent actionEvent) throws Exception {
        Stage stage= (Stage) bookborrowbtn.getScene().getWindow();
        //stage.hide();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/sellbooks.fxml"));
        Parent root = loader.load();
        stage.setTitle("Book Sell Page");
        stage.setScene(new Scene(root,762,599));
        stage.show();
    }

    public void addbooksbtnclicked(ActionEvent actionEvent) throws Exception {
        Stage stage= (Stage) bookborrowbtn.getScene().getWindow();
        //stage.hide();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/addbooks2.fxml"));
        Parent root = loader.load();
        stage.setTitle("Add books Page");
        stage.setScene(new Scene(root,760,581));
        stage.show();
    }

    public void takepaymentsbtnclicked(ActionEvent actionEvent) throws Exception {
        Stage stage= (Stage) bookborrowbtn.getScene().getWindow();
        //stage.hide();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/paymentpage.fxml"));
        Parent root = loader.load();
        //stage.setTitle("Add books Page");
        stage.setScene(new Scene(root,676,400));
        stage.show();

    }

    public void getinfobtnclicked(ActionEvent actionEvent) throws Exception {
        Stage stage= (Stage) bookborrowbtn.getScene().getWindow();
        //stage.hide();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/getinfopage.fxml"));
        Parent root = loader.load();
        //stage.setTitle("Add books Page");
        stage.setScene(new Scene(root,665,482));
        stage.show();

    }
}
