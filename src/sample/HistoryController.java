package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.ResultSet;

public class HistoryController {
    public String username;


    public TableView<HistoryBook> borrowtbl;
    public TableView<HistoryBook> buytbl;
    public TableView<HistoryBook> buytbl1;
    public TableColumn<HistoryBook,String> bookidclm;
    public TableColumn<HistoryBook,String> booknameclm;
    public TableColumn<HistoryBook,String> borrowdateclm;
    public TableColumn<HistoryBook,String> returndateclm;
    public TableColumn<HistoryBook, Integer> quantityclm;
    public TableColumn<HistoryBook, Integer> quantityclm1;

    public TableColumn<HistoryBook,String> bookidclm1;
    public TableColumn<HistoryBook,String> booknameclm1;
    public TableColumn<HistoryBook,String> dateclm;
    public TableColumn<HistoryBook,String> bookidclm11;
    public TableColumn<HistoryBook,String> booknameclm11;
    public TextField requestfld;



    ObservableList<HistoryBook> list= FXCollections.observableArrayList();
    ObservableList<HistoryBook> list1= FXCollections.observableArrayList();
    ObservableList<HistoryBook> list2= FXCollections.observableArrayList();




    public void setMain(Main main) {
        this.main = main;
    }

    public Main main;


    public void load(String s)
    {
        //book_idd=s;
        username=s;
        //System.out.println("inside showreview controller bookid is "+book_idd);
        //bookidfld.setEditable(false);

        OracleConnect oc;
        HistoryBook bk;
        list.clear();
        list1.clear();
        list2.clear();

        try {
            oc = new OracleConnect();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return;
        }

        try {
            String query="";

            query = String.format("SELECT book_id, to_char(borrowdate, 'DD-MM-YYYY') borrowdate, to_char(returndate,'DD-MM-YYYY') returndate, " +
                    "(SELECT name from books where book_id=BorrowBook.book_id) bookname"  +
                    " FROM BorrowBook WHERE username = '%s'", username);

            ResultSet rs = oc.searchDB(query);


            System.out.println("After query search");
            int count=0;
            while (rs.next()) {
                System.out.println(rs);
                bk=new HistoryBook();
                bk.book_id=rs.getString("book_id");
                bk.bookname=rs.getString("bookname");
                bk.setDateborrow(rs.getString("borrowdate"));
                bk.setDatereturn(rs.getString("returndate"));
                //bk.username=rs.getString("username");
                //bk.Star=rs.getInt("star");
                //bk.Comment=rs.getString("commont");



                list.add(bk);
                count++;
            }
            if(count==0)
            {
                System.out.println("No books found");
            }
            else
            {
                System.out.println("Count is "+ count);
                setTable();
            }
        } catch (Exception throwables) {
            System.out.println("Show tei problem");
            throwables.printStackTrace();
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }




        try {
            oc = new OracleConnect();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return;
        }

        try {
            String query="";

            query = String.format("SELECT book_id, to_char(buydate, 'DD-MM-YYYY') datebuy,  quantity, " +
                    "(SELECT name from books where book_id=BuyBook.book_id) bookname"  +
                    " FROM BuyBook WHERE username = '%s'", username);

            ResultSet rs = oc.searchDB(query);


            System.out.println("After query search");
            int count=0;
            while (rs.next()) {
                System.out.println(rs);
                bk=new HistoryBook();
                bk.book_id=rs.getString("book_id");
                bk.bookname=rs.getString("bookname");
                bk.setDatebuy(rs.getString("datebuy"));
                bk.setQuantity(rs.getInt("quantity"));
                //bk.username=rs.getString("username");
                //bk.Star=rs.getInt("star");
                //bk.Comment=rs.getString("commont");



                list1.add(bk);
                count++;
            }
            if(count==0)
            {
                System.out.println("No books found");
            }
            else
            {
                System.out.println("Count is "+ count);
                setTable1();
            }



            query="";

            query = String.format("SELECT book_id, quantity, " +
                    "(SELECT name from books where books.book_id=requestbuy.book_id) bookname"  +
                    " FROM requestbuy WHERE username = '%s' AND isbought='N'", username);

            rs = oc.searchDB(query);


            System.out.println("After query search");
            count=0;
            while (rs.next()) {
                System.out.println(rs);
                bk=new HistoryBook();
                bk.book_id=rs.getString("book_id");
                bk.bookname=rs.getString("bookname");
                bk.setQuantity(rs.getInt("quantity"));
                list2.add(bk);
                count++;
            }
            if(count==0)
            {
                System.out.println("No books found");
            }
            else
            {
                System.out.println("request buy er count is "+count);
                System.out.println("Count is "+ count);
                setTable2();
            }



        } catch (Exception throwables) {
            System.out.println("Show tei problem");
            throwables.printStackTrace();
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        oc=null;
        try {
            oc = new OracleConnect();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return;
        }

        try {
            String query="";

            //query = String.format("SELECT * from requestborrow where username='%s' AND isborrowed='n' AND isreturned='n' AND sysdate<endtime", username);


            query = String.format("SELECT * FROM REQUESTBORRow \n" +
                    "where username = '%s' AND (SYSDATE<ENDTIME) AND isborrowed='N'" +
                    " ", username);


            ResultSet rs = oc.searchDB(query);

            if (rs.next()) {


                //System.out.println("before delete");
                requestfld.setText(rs.getString("book_id"));
                //query=String.format("DELETE from requestborrow where username='%s' AND isborrowed='n' AND isreturned='n'", username);
                //oc.updateDB(query);

            }

            else
            {
                requestfld.setText("No Book Requested");
            }
        } catch (Exception throwables) {
            System.out.println("Problem in History Controller Cancel Function");
            throwables.printStackTrace();
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
    private void setTable()
    {
        System.out.println("inside Set Table ");
        bookidclm.setCellValueFactory(new PropertyValueFactory<HistoryBook, String>("book_id"));
        booknameclm.setCellValueFactory(new PropertyValueFactory<HistoryBook, String>("bookname"));
        //usernameclm.setCellValueFactory(new PropertyValueFactory<bookReview,String>("username"));
        //starclm.setCellValueFactory(new PropertyValueFactory<bookReview,Integer>("Star"));
        //commentclm.setCellValueFactory(new PropertyValueFactory<bookReview,String>("Comment"));

        borrowdateclm.setCellValueFactory(new PropertyValueFactory<HistoryBook, String>("dateborrow"));
        returndateclm.setCellValueFactory(new PropertyValueFactory<HistoryBook, String>("datereturn"));
        System.out.println("inside set table but before set items");
        System.out.println("list size is "+list.size());
        borrowtbl.setItems(list);
        borrowtbl.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void setTable1()
    {
        System.out.println("inside Set Table ");
        bookidclm1.setCellValueFactory(new PropertyValueFactory<HistoryBook, String>("book_id"));
        booknameclm1.setCellValueFactory(new PropertyValueFactory<HistoryBook, String>("bookname"));

        dateclm.setCellValueFactory(new PropertyValueFactory<HistoryBook, String>("datebuy"));
        quantityclm.setCellValueFactory(new PropertyValueFactory<HistoryBook, Integer>("quantity"));
        buytbl.setItems(list1);
        buytbl.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void setTable2()
    {
        System.out.println("inside Set Table ");
        bookidclm11.setCellValueFactory(new PropertyValueFactory<HistoryBook, String>("book_id"));
        booknameclm11.setCellValueFactory(new PropertyValueFactory<HistoryBook, String>("bookname"));
        quantityclm1.setCellValueFactory(new PropertyValueFactory<HistoryBook, Integer>("quantity"));
        buytbl1.setItems(list2);
        buytbl1.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }


    public void back(javafx.event.ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("showProfile.fxml"));
        Parent root = loader.load();
        ShowProfileController controller=loader.getController();

        controller.setMain(this.main);
        controller.load(username);



        main.stage.setTitle("Login");
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

    public void cancel(javafx.event.ActionEvent event) throws Exception{
        OracleConnect oc=null;
        try {
            oc = new OracleConnect();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return;
        }

        try {
            String query="";

            query = String.format("SELECT * from requestborrow where username='%s' AND isborrowed='n' AND isreturned='n'", username);
            query = String.format("SELECT * from requestborrow where username='%s' AND REQUESTID not in " +
                    "(SELECT BORROWID FROM BORROWBOOK)", username);

            ResultSet rs = oc.searchDB(query);

            if (rs.next()) {
                System.out.println("before delete");
                query=String.format("DELETE from requestborrow where username='%s' AND REQUESTID not in " +
                        "(SELECT BORROWID FROM BORROWBOOK) ", username);


                oc.updateDB(query);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Successfully Deleted the request");
                alert.show();
            }

            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You have no active request");
                alert.show();
            }
        } catch (Exception throwables) {
            System.out.println("Problem in History Controller Cancel Function");
            throwables.printStackTrace();
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }




        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("history.fxml"));
        Parent root = loader.load();
        HistoryController controller=loader.getController();

        controller.setMain(this.main);
        controller.load(username);



        main.stage.setTitle("User-History");
        main.stage.setScene(new Scene(root, 800, 680));
        main.stage.show();
    }


}
