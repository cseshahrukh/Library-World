package controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import startup.*;

import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.sql.*;

public class AddbookController {
    public TextField searchbookfld;
    public Button searchbtn;
    public Button selectbtn;
    public TextField bookidfld;
    public TextField qunatityfld;
    public RadioButton selltyperdobtn;
    public RadioButton borrowtyperdobtn;
    public Button confirmbtn;
    public TableView<Book> bookstbl;
    public ToggleGroup tg;
    public TableColumn<Book,String> bookidclm;
    public TableColumn<Book,String> booknameclm;
    public TableColumn<Book,String> writer1clm;
    public TableColumn<Book,String> writer2clm;
    public TableColumn<Book,String> writer3clm;
    public TableColumn<Book,String> shelfclm;
    public TableColumn<Book,String> roomclm;
    public Label newbooklabel;

    ObservableList<Book> list= FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        tg = new ToggleGroup();
        selltyperdobtn.setToggleGroup(tg);
        borrowtyperdobtn.setToggleGroup(tg);
        bookidfld.setEditable(false);

    }

    private void setTable()
    {
        bookidclm.setCellValueFactory(new PropertyValueFactory<Book,String>("bookid"));
        booknameclm.setCellValueFactory(new PropertyValueFactory<Book,String>("bookname"));
        writer1clm.setCellValueFactory(new PropertyValueFactory<Book,String>("writer1"));
        writer2clm.setCellValueFactory(new PropertyValueFactory<Book,String>("writer2"));
        writer3clm.setCellValueFactory(new PropertyValueFactory<Book,String>("writer3"));
        shelfclm.setCellValueFactory(new PropertyValueFactory<Book,String>("shelf"));
        roomclm.setCellValueFactory(new PropertyValueFactory<Book,String>("room"));
        bookstbl.setItems(list);
        bookstbl.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private boolean takesearchinput()
    {
        //String =searchbookfld.getText();
        return true;

    }

    public void searchbtnclicked(ActionEvent actionEvent) {
        //takesearchinput();
        DbConn oc;
        Book bk=null;
        list.clear();
        bookidfld.clear();
        qunatityfld.clear();
        try {
            oc = new DbConn();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return;
        }

        String searchname=searchbookfld.getText().trim().toLowerCase();

        try {
            String query = String.format("SELECT book_id,name,ShelfNo,RoomNo FROM BOOKS \n" +
                    "where LOWER(name)='%s' ",searchname);
            ResultSet rs = oc.searchDB(query);
            int count=0;

            while (rs.next()) {
                bk=new Book();
                bk.bookid=rs.getString("book_id");
                bk.bookname=rs.getString("name");
                bk.shelf=rs.getString("ShelfNo");
                bk.room=rs.getString("RoomNo");
                bk.writer1="N/A";
                bk.writer2="N/A";
                bk.writer3="N/A";
                String query1 = String.format("SELECT WriterName from BookWriter \n" +
                        "WHERE book_id='%s' ",bk.bookid);
                ResultSet rs1 = oc.searchDB(query1);
                int count1=1;
                while (rs1.next()) {
                    if(count1==1)
                        bk.writer1=rs1.getString("WriterName");
                    else if(count1==2)
                        bk.writer2=rs1.getString("WriterName");
                    else if(count1==3)
                        bk.writer3=rs1.getString("WriterName");
                    else
                        break;
                    count1++;

                }
                list.add(bk);
                count++;
            }
            if(count==0)
            {
                System.out.println("No books found");
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(getClass().getResource("../fxml/addnewbooks.fxml"));
                Parent root = loader.load();
                Stage stage=new Stage();
                stage.setTitle("Add new book");
                stage.setX(100);
                stage.setY(100);
                stage.setScene(new Scene(root,762,570));
                stage.show();
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

    public void selectbtnclicked(ActionEvent actionEvent) {
        Book bk=bookstbl.getSelectionModel().getSelectedItem();
        bookidfld.setText(bk.bookid);
    }

    public void confirmbtnclicked(ActionEvent actionEvent) {
        DbConn oc;
        Book bk=null;
        try {
            oc = new DbConn();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return;
        }

        int quantity=Integer.parseInt(qunatityfld.getText());
        String bookid=bookidfld.getText();

        try
        {
            if(selltyperdobtn.isSelected()) {
                String query1=String.format("select * from selltype where book_id='%s' ",bookid);
                ResultSet rs = oc.searchDB(query1);
                if(!rs.next())
                {
                    String query2=String.format("INSERT INTO Selltype VALUES('%s',%d)",bookid,quantity);
                    oc.updateDB(query2);
                    System.out.println("inserted successfully");

                }
                else
                {
                    String query = String.format("UPDATE Selltype\n" +
                            "SET count=count+%d \n" +
                            "WHERE\n" +
                            "book_id='%s'", quantity, bookid);
                    int count=oc.updateDB(query);
                    System.out.println(count+ " rows updated successfully");
                }
            }
            else
            {
                String query1=String.format("select * from borrowtype where book_id='%s' ",bookid);
                ResultSet rs = oc.searchDB(query1);
                if(!rs.next())
                {
                    String query2=String.format("INSERT INTO Borrowtype VALUES('%s',%d,%d)",bookid,quantity,quantity);
                    oc.updateDB(query2);
                    System.out.println("inserted successfully");
                }
                else
                {
                    String query=String.format("UPDATE Borrowtype\n" +
                            "SET \n" +
                            "total=total+%d,\n" +
                            "inlibrary=inlibrary+%d\n" +
                            "WHERE\n" +
                            "book_id='%s'",quantity,quantity,bookid);
                    int count=oc.updateDB(query);
                    System.out.println(count+ " rows updated successfully");
                }
            }

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Successfully added the books");
            a.showAndWait();

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

    public void homebtnclicked(ActionEvent actionEvent) throws Exception {
        Stage stage= (Stage) searchbtn.getScene().getWindow();
        stage.hide();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/adminhomepage.fxml"));
        Parent root = loader.load();
        stage.setTitle("Library Management System");
        stage.setScene(new Scene(root,723,505));
        stage.setResizable(false);
        stage.show();

    }
}
