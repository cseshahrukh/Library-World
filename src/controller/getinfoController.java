package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class getinfoController {
    public Button borrowhistorybtn;

    public void earninginfobtnclicked(ActionEvent actionEvent) throws Exception {
        Stage stage= (Stage) borrowhistorybtn.getScene().getWindow();
        //stage.hide();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/earninginfo.fxml"));
        Parent root = loader.load();
        //stage.setTitle("Add books Page");
        stage.setScene(new Scene(root,768,702));
        stage.show();


    }

    public void borrowhistorybtnclicked(ActionEvent actionEvent) throws Exception {
        Stage stage= (Stage) borrowhistorybtn.getScene().getWindow();
        //stage.hide();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/borrowhistory.fxml"));
        Parent root = loader.load();
        //stage.setTitle("Add books Page");
        stage.setScene(new Scene(root,727,546));
        stage.show();
    }

    public void reviewsbtnclicked(ActionEvent actionEvent) {
    }

    public void bookinfobtnclicked(ActionEvent actionEvent) {
    }

    public void buyhistorybtnclicked(ActionEvent actionEvent) throws Exception {
        Stage stage= (Stage) borrowhistorybtn.getScene().getWindow();
        //stage.hide();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/sellhistory.fxml"));
        Parent root = loader.load();
        //stage.setTitle("Add books Page");
        stage.setScene(new Scene(root,727,546));
        stage.show();
    }

    public void userinfobtnclicked(ActionEvent actionEvent) throws Exception {
    }

    public void homebtnclicked(ActionEvent actionEvent) throws Exception{
        Stage stage= (Stage) borrowhistorybtn.getScene().getWindow();
        //stage.hide();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/adminhomepage.fxml"));
        Parent root = loader.load();
        //stage.setTitle("Add books Page");
        stage.setScene(new Scene(root,723,505));
        stage.show();

    }

}
