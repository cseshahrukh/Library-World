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

import java.sql.ResultSet;

public class FinescollectionController {
    public TableView<Book> booktbl;
    public TableColumn<Book,String> bookidclm;
    public TableColumn<Book,String> nameclm;
    public TableColumn<Book,String> dateborrowclm;
    public TableColumn<Book,String> datetoreturnclm;
    public TableColumn<Book,String> datereturnclm;
    public TableColumn<Book,Integer> fineclm;
    public TextField amountfld;
    public PaymentpageController parent;
    public String user;
    ObservableList<Book> list= FXCollections.observableArrayList();


    private void setbookstable()
    {
        bookidclm.setCellValueFactory(new PropertyValueFactory<Book,String>("bookid"));
        nameclm.setCellValueFactory(new PropertyValueFactory<Book,String>("bookname"));
        dateborrowclm.setCellValueFactory(new PropertyValueFactory<Book,String>("dateborrow"));
        datetoreturnclm.setCellValueFactory(new PropertyValueFactory<Book,String>("datetoreturn"));
        datereturnclm.setCellValueFactory(new PropertyValueFactory<Book,String>("datereturn"));
        fineclm.setCellValueFactory(new PropertyValueFactory<Book,Integer>("fines"));
        booktbl.setItems(list);
    }

    @FXML
    public void initialize() {
        amountfld.setEditable(false);

    }

    public void setData()
    {
        DbConn oc;
        Book bk=null;
        try {
            oc = new DbConn();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return;
        }
        try
        {
            int count=0;
            String query = String.format("SELECT borrowid from FinesPayment where userid='%s' and paymentdate is null",user);
            ResultSet rs = oc.searchDB(query);
            System.out.println(user);
            while (rs.next()) {
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
                String query4=String.format("select name from books where book_id='%s'",bookid);
                ResultSet rs4=oc.searchDB(query4);
                rs4.next();
                String bookname=rs4.getString("name");

                bk=new Book();
                bk.bookid=bookid;
                bk.bookname=bookname;
                bk.dateborrow=borrdate;
                bk.datetoreturn=expretdate;
                bk.borrowid=borrid;
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
                System.out.println("something error occurred");
            }
            else
            {
                setbookstable();
                int totalfines=0;
                for(int i=0;i<list.size();i++)
                {
                    Book temp=list.get(i);
                    totalfines=totalfines+temp.fines;
                }
                amountfld.setText(String.valueOf(totalfines));
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

    public void confirmbtnclicked(ActionEvent actionEvent) {
        DbConn oc;
        try {
            oc = new DbConn();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return;
        }

        try
        {
            for(int i=0;i<list.size();i++)
            {
                Book temp=list.get(i);
                String query=String.format("UPDATE FinesPayment\n" +
                        "SET PAYMENTAMOUNT=%d,PaymentDate=SYSDATE\n" +
                        "WHERE BorrowID=%d",temp.fines,temp.borrowid);
                oc.updateDB(query);
            }

            Stage stage= (Stage)amountfld.getScene().getWindow();
            stage.hide();
            parent.fines1lbl.setVisible(false);
            parent.fine2lbl.setVisible(false);
            parent.finesbtn.setVisible(false);

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Fines payment collection successful");
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
}
