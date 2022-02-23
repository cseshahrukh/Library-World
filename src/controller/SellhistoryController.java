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

public class SellhistoryController {
    public DatePicker fromdatepicker;
    public DatePicker todatepicker;
    public TextField useridfld;
    public TableView<Book> selltbl;
    public TableColumn<Book,String> userid;
    public TableColumn<Book,String> bookid;
    public TableColumn<Book,String> bookname;
    public TableColumn<Book,Integer> quantity;
    public TableColumn<Book,Integer> pbprice;
    public TableColumn<Book,Integer> subtotal;
    public TableColumn<Book,String> buydate;
    public TextField totalamountfld;

    ObservableList<Book> list= FXCollections.observableArrayList();
    public String user;

    private void setbookstable()
    {
        bookid.setCellValueFactory(new PropertyValueFactory<Book,String>("bookid"));
        bookname.setCellValueFactory(new PropertyValueFactory<Book,String>("bookname"));
        quantity.setCellValueFactory(new PropertyValueFactory<Book,Integer>("quantity"));
        pbprice.setCellValueFactory(new PropertyValueFactory<Book,Integer>("price"));
        subtotal.setCellValueFactory(new PropertyValueFactory<Book,Integer>("subtotal"));
        buydate.setCellValueFactory(new PropertyValueFactory<Book,String>("datebuy"));
        userid.setCellValueFactory(new PropertyValueFactory<Book,String>("userid"));
        selltbl.setItems(list);

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
                query = String.format("SELECT username,book_id,quantity,to_char(buydate,'DD-MM-YYYY') from BUYBOOK\n" +
                        "where buydate>to_date('%s','DD-MM-YYYY') AND buydate<to_date('%s','DD-MM-YYYY')",from,to);

            }
            else
            {
                query = String.format("SELECT username,book_id,quantity,to_char(buydate,'DD-MM-YYYY') from BUYBOOK\n" +
                        "where username='%s' AND buydate>to_date('%s','DD-MM-YYYY') AND buydate<to_date('%s','DD-MM-YYYY')",user,from,to);

            }
            ResultSet rs = oc.searchDB(query);
            //System.out.println(user);
            while (rs.next()) {
                String userid=rs.getString(1);
                String bookid=rs.getString(2);
                int quantity=rs.getInt(3);
                String buydate=rs.getString(4);

                String query3=String.format("select name,price from books where book_id='%s'",bookid);
                ResultSet rs3=oc.searchDB(query3);
                rs3.next();
                String bookname=rs3.getString(1);
                int price=rs3.getInt(2);

                bk=new Book();
                bk.userid=userid;
                bk.bookid=bookid;
                bk.bookname=bookname;
                bk.datebuy=buydate;
                bk.quantity=quantity;
                bk.price=price;
                bk.subtotal=price*quantity;
                list.add(bk);
                count++;
            }
            if(count==0)
            {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("No sell found during this period");
                a.showAndWait();
            }
            else
            {
                setbookstable();
                int totalamount=0;
                for(int i=0;i<list.size();i++)
                {
                    totalamount=totalamount+list.get(i).subtotal;
                }
                totalamountfld.setText(String.valueOf(totalamount));
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
        Stage stage= (Stage) useridfld.getScene().getWindow();
        //stage.hide();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/getinfopage.fxml"));
        Parent root = loader.load();
        //stage.setTitle("Add books Page");
        stage.setScene(new Scene(root,665,482));
        stage.show();
    }
}
