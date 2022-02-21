package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import startup.Book;
import startup.DbConn;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BorrowhistoryController {
    public DatePicker fromdatepicker;
    public DatePicker todatepicker;
    public TextField useridfld;
    public TableView<Book> borrowtbl;
    public TableColumn<Book,String> userid;
    public TableColumn<Book,String> bookid;
    public TableColumn<Book,String> bookname;
    public TableColumn<Book,String> borrowdate;
    public TableColumn<Book,String> returndate;
    ObservableList<Book> list= FXCollections.observableArrayList();
    public String user;

    private void setbookstable()
    {
        bookid.setCellValueFactory(new PropertyValueFactory<Book,String>("bookid"));
        bookname.setCellValueFactory(new PropertyValueFactory<Book,String>("bookname"));
        borrowdate.setCellValueFactory(new PropertyValueFactory<Book,String>("dateborrow"));
        returndate.setCellValueFactory(new PropertyValueFactory<Book,String>("datereturn"));
        userid.setCellValueFactory(new PropertyValueFactory<Book,String>("userid"));
        borrowtbl.setItems(list);

    }

    public void okbtnclicked(ActionEvent actionEvent) {
        String from,to;//from datepicker to string conversion
        LocalDate fromdate=fromdatepicker.getValue();
        from=fromdate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate todate=todatepicker.getValue();
        to=todate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        user=useridfld.getText();

        DbConn oc;
        Book bk=null;
        list.clear();
        try {
            oc = new DbConn();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return;
        }
        try
        {
            int count=0;
            String query;
            if(user.equals(""))
            {
                query = String.format("SELECT username,book_id,to_char(borrowdate,'DD-MM-YYYY'),to_char(returndate,'DD-MM-YYYY') from BorrowBook where borrowdate>to_date('%s','DD-MM-YYYY') AND borrowdate<to_date('%s','DD-MM-YYYY')",from,to);

            }
            else
            {
                query = String.format("SELECT username,book_id,to_char(borrowdate,'DD-MM-YYYY'),to_char(returndate,'DD-MM-YYYY') from BorrowBook where username='%s' AND borrowdate>to_date('%s','DD-MM-YYYY') AND borrowdate<to_date('%s','DD-MM-YYYY')",user,from,to);

            }
            ResultSet rs = oc.searchDB(query);
            //System.out.println(user);
            while (rs.next()) {
                String userid=rs.getString(1);
                String bookid=rs.getString(2);
                String borrdate=rs.getString(3);
                String retdate=rs.getString(4);
                String query3=String.format("select name from books where book_id='%s'",bookid);
                ResultSet rs3=oc.searchDB(query3);
                rs3.next();
                String bookname=rs3.getString(1);

                bk=new Book();
                bk.userid=userid;
                bk.bookid=bookid;
                bk.bookname=bookname;
                bk.dateborrow=borrdate;
                if(retdate==null)
                {
                    bk.datereturn="Not returned";
                }
                else
                {
                    bk.datereturn=retdate;
                }
                list.add(bk);
                count++;
            }
            if(count==0)
            {
                System.out.println("no books borrowed during this period");
            }
            else
            {
                setbookstable();
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

    public void backbtnclicked(ActionEvent actionEvent) throws Exception {
        Stage stage= (Stage)useridfld.getScene().getWindow();
        //stage.hide();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/getinfopage.fxml"));
        Parent root = loader.load();
        //stage.setTitle("Add books Page");
        stage.setScene(new Scene(root,665,482));
        stage.show();

    }
}
