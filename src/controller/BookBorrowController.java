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

public class BookBorrowController {
    public TextField checkusernamefld;
    public TableView<Book> reserbookstbl;
    public TableColumn<Book,String> resbookidclm;
    public TableColumn<Book,String> resnameclm;
    public TableColumn<Book,String> reswriter1clm;
    public TableColumn<Book,String> reswriter2clm;
    public TableColumn<Book,String> reswriter3clm;
    public TableColumn<Book,String> respublisherclm;
    public TableColumn<Book,String> resshelfclm;
    public TableColumn<Book,String> resroomclm;
    public TextField dateborrowfld;
    public TextField bookidfld;
    public TextField usernamefld;
    public TextField datereturnfld;
    private String user;
    private int reqid;

    ObservableList<Book> reslist= FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        usernamefld.setEditable(false);
        bookidfld.setEditable(false);
        dateborrowfld.setEditable(false);
        datereturnfld.setEditable(false);
    }

    private void setresbookstable()
    {
        resbookidclm.setCellValueFactory(new PropertyValueFactory<Book,String>("bookid"));
        resnameclm.setCellValueFactory(new PropertyValueFactory<Book,String>("bookname"));
        reswriter1clm.setCellValueFactory(new PropertyValueFactory<Book,String>("writer1"));
        reswriter2clm.setCellValueFactory(new PropertyValueFactory<Book,String>("writer2"));
        reswriter3clm.setCellValueFactory(new PropertyValueFactory<Book,String>("writer3"));
        respublisherclm.setCellValueFactory(new PropertyValueFactory<Book,String>("publisher"));
        resshelfclm.setCellValueFactory(new PropertyValueFactory<Book,String>("shelf"));
        resroomclm.setCellValueFactory(new PropertyValueFactory<Book,String>("room"));
        reserbookstbl.setItems(reslist);
        reserbookstbl.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void searchbtnclicked(ActionEvent actionEvent) {
        DbConn oc;
        Book bk=null;
        reslist.clear();
        usernamefld.clear();
        bookidfld.clear();
        dateborrowfld.clear();
        datereturnfld.clear();
        String username=checkusernamefld.getText().trim();
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
            String query = String.format("select book_id,RequestID from RequestBorrow where username='%s' AND EndTime>SYSDATE AND IsBorrowed='N' ",username);
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                bk=new Book();
                bk.bookid=rs.getString("book_id");
                //bk.quantity=rs.getInt("Quantity");
                reqid=rs.getInt("RequestID");
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
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(getClass().getResource("../fxml/borrowwithoutreservation.fxml"));
                Parent root = loader.load();
                Stage stage=new Stage();
                stage.setX(100);
                stage.setY(100);
                stage.setScene(new Scene(root,762,570));
                BorrowwithoutreservationController cont=loader.getController();
                cont.user=username;
                stage.show();
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
        Book bk=reserbookstbl.getSelectionModel().getSelectedItem();
        reslist.remove(bk);
        bookidfld.setText(bk.bookid);
        usernamefld.setText(user);
        try {
            oc = new DbConn();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return;
        }
        try
        {
            String query="SELECT TO_CHAR\n" +
                    "    (SYSDATE, 'MM-DD-YYYY') \"NOW\"\n" +
                    "     FROM DUAL" ;
            ResultSet rs=oc.searchDB(query);
            rs.next();
            String vdateborrow=rs.getString(1);
            dateborrowfld.setText(vdateborrow);

            String query1="SELECT TO_CHAR\n" +
                    "    (SYSDATE+7, 'MM-DD-YYYY') \n" +
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
        //bookborrow table update korte hobe
        DbConn oc;
        String username=usernamefld.getText();
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
                    " SET INLIBRARY=INLIBRARY-1\n" +
                    " WHERE BOOK_ID='%s'",bookid);
            oc.updateDB(query);

            String query1=String.format("UPDATE REQUESTBORROW \n" +
                    "set isborrowed='Y' \n" +
                    "where requestid=%d",reqid);
            oc.updateDB(query1);

            String querycount ="SELECT count(*) count from BorrowBook";
            ResultSet rs = oc.searchDB(querycount);
            rs.next();
            int count1=rs.getInt("count");
            count1++;

            String query2=String.format("INSERT INTO BorrowBook(username,book_id,BorrowID,BorrowDate,ExpReturnDate)\n" +
                    "values('%s','%s',%d,sysdate,sysdate+7)",username,bookid,count1);
            oc.updateDB(query2);


            checkusernamefld.clear();
            bookidfld.clear();
            usernamefld.clear();
            dateborrowfld.clear();
            datereturnfld.clear();

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Successfully book issued");
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

    public void homebtnclicked(ActionEvent actionEvent) throws Exception {
        Stage stage= (Stage) usernamefld.getScene().getWindow();
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
