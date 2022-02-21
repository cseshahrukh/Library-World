package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import startup.Book;
import startup.DbConn;
import startup.OtherInfos;

import java.sql.ResultSet;

public class ReturnbookController {
    public TextField userid1fld;
    public TableView<Book> borrowedbookstbl;
    public TableColumn<Book,String> bookidclm;
    public TableColumn<Book,String> booknameclm;
    public TableColumn<Book,String> writer1clm;
    public TableColumn<Book,String> writer2clm;
    public TableColumn<Book,String> writer3clm;
    public TableColumn<Book,String> publisherclm;
    public TableColumn<Book,String> shelfclm;
    public TableColumn<Book,String> roomclm;
    public TextField dateborrowfld;
    public TextField bookidfld;
    public TextField userid2fld;
    public TextField datereturnfld;
    private int borrid;
    private String user;

    ObservableList<Book> reslist= FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        userid2fld.setEditable(false);
        bookidfld.setEditable(false);
        dateborrowfld.setEditable(false);
        datereturnfld.setEditable(false);
    }

    private void setresbookstable()
    {
        bookidclm.setCellValueFactory(new PropertyValueFactory<Book,String>("bookid"));
        booknameclm.setCellValueFactory(new PropertyValueFactory<Book,String>("bookname"));
        writer1clm.setCellValueFactory(new PropertyValueFactory<Book,String>("writer1"));
        writer2clm.setCellValueFactory(new PropertyValueFactory<Book,String>("writer2"));
        writer3clm.setCellValueFactory(new PropertyValueFactory<Book,String>("writer3"));
        publisherclm.setCellValueFactory(new PropertyValueFactory<Book,String>("publisher"));
        shelfclm.setCellValueFactory(new PropertyValueFactory<Book,String>("shelf"));
        roomclm.setCellValueFactory(new PropertyValueFactory<Book,String>("room"));
        borrowedbookstbl.setItems(reslist);
        borrowedbookstbl.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void searchbtnclicked(ActionEvent actionEvent) {
        DbConn oc;
        Book bk=null;
        reslist.clear();
        userid2fld.clear();
        bookidfld.clear();
        dateborrowfld.clear();
        datereturnfld.clear();
        String username=userid1fld.getText().trim();
        user=username;
        try {
            oc = new DbConn();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return;
        }
        try
        {
            int count=0;
            String query = String.format("select book_id,BorrowID from BorrowBook where username='%s' AND returndate is null ",username);
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                bk=new Book();
                bk.bookid=rs.getString("book_id");
                //bk.quantity=rs.getInt("Quantity");
                borrid=rs.getInt("BorrowID");
                //bk.bookname=rs.getString("name");
                //bk.shelf=rs.getString("ShelfNo");
                //bk.room=rs.getString("RoomNo");
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
                String query2 = String.format("SELECT name,ShelfNo,RoomNo,publisher FROM BOOKS \n" +
                        "where book_id='%s' ",bk.bookid);
                ResultSet rs2 = oc.searchDB(query2);
                rs2.next();
                bk.bookname=rs2.getString("name");
                bk.shelf=rs2.getString("ShelfNo");
                bk.room=rs2.getString("RoomNo");
                bk.publisher=rs2.getString("publisher");
                reslist.add(bk);
                count++;
            }
            if(count==0)
            {
                System.out.println("No books found");
            }
            else
            {
                setresbookstable();

            }

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
        DbConn oc;
        Book bk=borrowedbookstbl.getSelectionModel().getSelectedItem();
        reslist.remove(bk);
        bookidfld.setText(bk.bookid);
        userid2fld.setText(user);
        try {
            oc = new DbConn();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return;
        }
        try
        {
            String query=String.format("SELECT TO_CHAR(borrowdate,'MM-DD-YYYY') from BorrowBook\n" +
                    "where borrowid=%d",borrid);
            ResultSet rs=oc.searchDB(query);
            rs.next();
            String vdateborrow=rs.getString(1);
            dateborrowfld.setText(vdateborrow);

            String query1="SELECT TO_CHAR\n" +
                    "    (SYSDATE, 'MM-DD-YYYY') \n" +
                    "     FROM DUAL" ;
            ResultSet rs1=oc.searchDB(query1);
            rs1.next();
            String vdateofreturn=rs1.getString(1);
            datereturnfld.setText(vdateofreturn);

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

    public void confirmbtnclicked(ActionEvent actionEvent) {
        DbConn oc;
        String username=userid2fld.getText();
        String bookid=bookidfld.getText();
        try {
            oc = new DbConn();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return;
        }
        try
        {
            String query=String.format("  UPDATE BORROWTYPE\n" +
                    " SET INLIBRARY=INLIBRARY+1\n" +
                    " WHERE BOOK_ID='%s'",bookid);
            oc.updateDB(query);

            String query1=String.format("UPDATE BorrowBook \n" +
                    "set returndate=sysdate \n" +
                    "where borrowid=%d",borrid);
            oc.updateDB(query1);


            userid1fld.clear();
            bookidfld.clear();
            userid2fld.clear();
            dateborrowfld.clear();
            datereturnfld.clear();

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Successfully book returned");
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

    public void homebtnclicked(ActionEvent actionEvent) throws Exception{
        Stage stage= (Stage) userid1fld.getScene().getWindow();
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
