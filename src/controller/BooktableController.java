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

import java.io.IOException;
import java.util.*;
import java.sql.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import startup.*;

public class BooktableController {
    public TableView<Book> bookstable;
    public TableColumn<Book,String> bookidclm;
    public TableColumn<Book,String> booknameclm;
    public TableColumn<Book,String> publisherclm;
    public TableColumn<Book,String> writer1clm;
    //public TableColumn<Car,String>  color3clm;
    // public TableColumn<Car,String>  makeclm;
    ///public TableColumn<Car,String>  modelclm;
    //public TableColumn<Car,Integer> priceclm;
    //ObservableList<Book> list= FXCollections.observableArrayList();


    public void setTable(ObservableList<Book> books)
    {
        bookidclm.setCellValueFactory(new PropertyValueFactory<Book,String>("bookid"));
        booknameclm.setCellValueFactory(new PropertyValueFactory<Book,String>("bookname"));
        publisherclm.setCellValueFactory(new PropertyValueFactory<Book,String>("publisher"));
        /*color2clm.setCellValueFactory(new PropertyValueFactory<Car,String>("colour2"));
        color3clm.setCellValueFactory(new PropertyValueFactory<Car,String>("colour3"));
        makeclm.setCellValueFactory(new PropertyValueFactory<Car,String>("carmake"));
        modelclm.setCellValueFactory(new PropertyValueFactory<Car,String>("carmodel"));
        priceclm.setCellValueFactory(new PropertyValueFactory<Car,Integer>("price"));*/
        bookstable.setItems(books);
        bookstable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

}
