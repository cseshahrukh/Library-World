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
import java.time.*;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;

public class earninginfoController {

    public TextField finesfld;
    public TextField upfld;
    public TextField totalpaymentfld;
    public TableColumn<Book,String> useridclm;
    public TableView<Book> finestbl;
    public TableColumn<Book,String> bookidclm;
    public TableColumn<Book,String> dateborrowclm;
    public TableColumn<Book,String> datetoreturnclm;
    public TableColumn<Book,String> datereturnclm;
    public TableColumn<Book,Integer> fineclm;
    public TableColumn<Book,String> paydate1clm;

    public TableView<Book> userpaytbl;
    public TableColumn<Book,String> userid2clm;
    public TableColumn<Book,Integer> payamountclm;
    public TableColumn<Book,String> paydate2clm;
    public int totalfines,userpayments,totalpayments;
    public DatePicker fromdatepicker;
    public DatePicker todatepicker;
    public TextField useridfld;

    ObservableList<Book> list= FXCollections.observableArrayList();
    ObservableList<Book> list2= FXCollections.observableArrayList();

    private void setfinestable()
    {
        useridclm.setCellValueFactory(new PropertyValueFactory<Book,String>("userid"));
        bookidclm.setCellValueFactory(new PropertyValueFactory<Book,String>("bookid"));
        dateborrowclm.setCellValueFactory(new PropertyValueFactory<Book,String>("dateborrow"));
        datetoreturnclm.setCellValueFactory(new PropertyValueFactory<Book,String>("datetoreturn"));
        datereturnclm.setCellValueFactory(new PropertyValueFactory<Book,String>("datereturn"));
        fineclm.setCellValueFactory(new PropertyValueFactory<Book,Integer>("fines"));
        paydate1clm.setCellValueFactory(new PropertyValueFactory<Book,String>("paydate"));
        finestbl.setItems(list);
    }
    private void setuserpaymenttable()
    {
        userid2clm.setCellValueFactory(new PropertyValueFactory<Book,String>("userid"));
        payamountclm.setCellValueFactory(new PropertyValueFactory<Book,Integer>("payamount"));
        paydate2clm.setCellValueFactory(new PropertyValueFactory<Book,String>("paydate"));
        userpaytbl.setItems(list2);

    }

    private void setfinedata(String user)
    {
        String from,to;//from datepicker to string conversion
        LocalDate fromdate=fromdatepicker.getValue();
        from=fromdate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate todate=todatepicker.getValue();
        to=todate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

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
                query = String.format("SELECT userid,borrowid,paymentamount,to_char(paymentdate,'DD-MM-YYYY') paymentdate from FinesPayment where paymentdate>to_date('%s','DD-MM-YYYY') AND paymentdate<to_date('%s','DD-MM-YYYY')",from,to);
            }
            else
            {
                query= String.format("SELECT userid,borrowid,paymentamount,to_char(paymentdate,'DD-MM-YYYY') paymentdate from FinesPayment where userid='%s' AND paymentdate>to_date('%s','DD-MM-YYYY') AND paymentdate<to_date('%s','DD-MM-YYYY')",user,from,to);
            }

            ResultSet rs = oc.searchDB(query);
            //System.out.println(user);
            while (rs.next()) {
                String userid=rs.getString("userid");
                int payamount=rs.getInt("paymentamount");
                String paymentdate=rs.getString("paymentdate");
                int borrid=rs.getInt("borrowid");
                String query3=String.format("SELECT book_id,TO_CHAR(BorrowDate,'DD-MM-YYYY'),TO_CHAR(ExpReturnDate,'DD-MM-YYYY'),TO_CHAR(ReturnDate,'DD-MM-YYYY')\n" +
                        "from BORROWBOOK\n" +
                        "where BorrowID=%d",borrid);
                ResultSet rs3=oc.searchDB(query3);
                rs3.next();
                String bookid=rs3.getString(1);
                String borrdate=rs3.getString(2);
                String expretdate=rs3.getString(3);
                String retdate=rs3.getString(4);

                bk=new Book();
                bk.userid=userid;
                bk.bookid=bookid;
                bk.dateborrow=borrdate;
                bk.datetoreturn=expretdate;
                bk.borrowid=borrid;
                bk.fines=payamount;
                bk.paydate=paymentdate;
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
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("No finespayment during this time");
                alert.show();
            }
            else
            {
                setfinestable();
                int totalfines=0;
                for(int i=0;i<list.size();i++)
                {
                    Book temp=list.get(i);
                    totalfines=totalfines+temp.fines;
                }
                this.totalfines=totalfines;
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

    private void setuserpaymentdata(String user)
    {
        String from,to;//from datepicker to string conversion
        LocalDate fromdate=fromdatepicker.getValue();
        from=fromdate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate todate=todatepicker.getValue();
        to=todate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        DbConn oc;
        Book bk=null;
        list2.clear();
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
                query = String.format("select userid,paymentamount,to_char(paymentdate,'DD-MM-YYYY') paymentdate from payments where paymentdate>to_date('%s','DD-MM-YYYY') AND paymentdate<to_date('%s','DD-MM-YYYY') ",from,to);

            }
            else
            {
                query = String.format("select userid,paymentamount,to_char(paymentdate,'DD-MM-YYYY') paymentdate from payments where userid='%s' AND paymentdate>to_date('%s','DD-MM-YYYY') AND paymentdate<to_date('%s','DD-MM-YYYY') ",user,from,to);

            }

            ResultSet rs = oc.searchDB(query);
            //System.out.println(user);
            while (rs.next()) {
                String userid=rs.getString("userid");
                int payamount=rs.getInt("paymentamount");
                String paymentdate=rs.getString("paymentdate");

                bk=new Book();
                bk.userid=userid;
                bk.payamount=payamount;
                bk.paydate=paymentdate;
                list2.add(bk);
                count++;
            }
            if(count==0)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("No payment during this time");
                alert.show();
            }
            else
            {
                //start from here tonight
                setuserpaymenttable();
                int totalup=0;
                for(int i=0;i<list2.size();i++)
                {
                    Book temp=list2.get(i);
                    totalup=totalup+temp.payamount;
                }
                this.userpayments=totalup;
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

    public void okbtnclicked(ActionEvent actionEvent) {
        String us=useridfld.getText().trim();
        setfinedata(us);
        setuserpaymentdata(us);
        totalpayments=totalfines+userpayments;
        finesfld.setText(String.valueOf(totalfines));
        upfld.setText(String.valueOf(userpayments));
        totalpaymentfld.setText(String.valueOf(totalpayments));
    }

    public void backbtnclicked(ActionEvent actionEvent) throws Exception {
        Stage stage= (Stage) finesfld.getScene().getWindow();
        //stage.hide();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/getinfopage.fxml"));
        Parent root = loader.load();
        //stage.setTitle("Add books Page");
        stage.setScene(new Scene(root,665,482));
        stage.show();

    }
}
