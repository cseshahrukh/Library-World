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

public class SellbooksController {
    public TextField useridfld;
    public Button searchbtn;
    public TableView<Book> reserbookstbl;
    public TableColumn<Book,String> resbookidclm;
    public TableColumn<Book,String> resnameclm;
    public TableColumn<Book,String> reswriter1clm;
    public TableColumn<Book,String> reswriter2clm;
    public TableColumn<Book,String> reswriter3clm;
    public TableColumn<Book,Integer> resnumberclm;
    public TableColumn<Book,String> resshelfclm;
    public TableColumn<Book,String> resroomclm;
    public Button selectallbtn;
    public Button addcartbtn;
    public TableView<Book> selbookstbl;
    public TableColumn<Book,String> selbookidclm;
    public TableColumn<Book,String> selnameclm;
    public TableColumn<Book,String> selwriter1clm;
    public TableColumn<Book,String> selwriter2clm;
    public TableColumn<Book,String> selwriter3clm;
    public TableColumn<Book,Integer> selnumberclm;
    public TableColumn<Book,String> selshelfclm;
    public TableColumn<Book,String> selroomclm;
    public TextField payamountfld;
    public Button confirmpaybtn;
    public TableColumn<Book,Integer> respriceclm;
    public TableColumn<Book,Integer> selpriceclm;
    public String user;

    ObservableList<Book> reslist= FXCollections.observableArrayList();
    ObservableList<Book> sellist= FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        payamountfld.setEditable(false);


    }

    private void setresbookstable()
    {
        resbookidclm.setCellValueFactory(new PropertyValueFactory<Book,String>("bookid"));
        resnameclm.setCellValueFactory(new PropertyValueFactory<Book,String>("bookname"));
        reswriter1clm.setCellValueFactory(new PropertyValueFactory<Book,String>("writer1"));
        reswriter2clm.setCellValueFactory(new PropertyValueFactory<Book,String>("writer2"));
        reswriter3clm.setCellValueFactory(new PropertyValueFactory<Book,String>("writer3"));
        resshelfclm.setCellValueFactory(new PropertyValueFactory<Book,String>("shelf"));
        resroomclm.setCellValueFactory(new PropertyValueFactory<Book,String>("room"));
        resnumberclm.setCellValueFactory(new PropertyValueFactory<Book,Integer>("quantity"));
        respriceclm.setCellValueFactory(new PropertyValueFactory<Book,Integer>("price"));
        reserbookstbl.setItems(reslist);
        reserbookstbl.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void setselbookstable()
    {
        selbookidclm.setCellValueFactory(new PropertyValueFactory<Book,String>("bookid"));
        selnameclm.setCellValueFactory(new PropertyValueFactory<Book,String>("bookname"));
        selwriter1clm.setCellValueFactory(new PropertyValueFactory<Book,String>("writer1"));
        selwriter2clm.setCellValueFactory(new PropertyValueFactory<Book,String>("writer2"));
        selwriter3clm.setCellValueFactory(new PropertyValueFactory<Book,String>("writer3"));
        selshelfclm.setCellValueFactory(new PropertyValueFactory<Book,String>("shelf"));
        selroomclm.setCellValueFactory(new PropertyValueFactory<Book,String>("room"));
        selnumberclm.setCellValueFactory(new PropertyValueFactory<Book,Integer>("quantity"));
        selpriceclm.setCellValueFactory(new PropertyValueFactory<Book,Integer>("price"));
        selbookstbl.setItems(sellist);
        //selbookstbl.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void searchbtnclicked(ActionEvent actionEvent) {
        DbConn oc;
        Book bk=null;
        reslist.clear();
        sellist.clear();
        payamountfld.clear();
        String username=useridfld.getText().trim();
        this.user=username;
        try {
            oc = new DbConn();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return;
        }
        try
        {
            String query3=String.format("select count(*) count from Users where username='%s'",user);
            ResultSet rs3=oc.searchDB(query3);
            rs3.next();
            int t=rs3.getInt("count");
            if(t==0)
            {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("No such user exists");
                a.showAndWait();
                return;
            }

            int count=0;
            String query = String.format("select RequestID,book_id,Quantity from RequestBuy where username='%s' AND EndTime>SYSDATE AND ISBOUGHT='N' ",username);
            ResultSet rs = oc.searchDB(query);
            while (rs.next()) {
                bk=new Book();
                bk.bookid=rs.getString("book_id");
                bk.quantity=rs.getInt("Quantity");
                bk.requestid=rs.getInt("RequestID");
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
                String query2 = String.format("SELECT name,ShelfNo,RoomNo,price FROM BOOKS \n" +
                        "where book_id='%s' ",bk.bookid);
                ResultSet rs2 = oc.searchDB(query2);
                rs2.next();
                bk.bookname=rs2.getString("name");
                bk.shelf=rs2.getString("ShelfNo");
                bk.room=rs2.getString("RoomNo");
                bk.price=rs2.getInt("price");
                reslist.add(bk);
                count++;
            }
            if(count==0)
            {
                System.out.println("No books found");
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(getClass().getResource("../fxml/sellwithoutreserv.fxml"));
                Parent root = loader.load();
                Stage stage=new Stage();
                stage.setX(100);
                stage.setY(100);
                stage.setScene(new Scene(root,762,626));
                SellwithoutreservController cont=loader.getController();
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

    public void selectallbtnclicked(ActionEvent actionEvent) {
        int totalprice=0;
        for(int i=0;i<reslist.size();i++)
        {
            sellist.add(reslist.get(i));
        }
        reslist.clear();
        setselbookstable();
        for(int i=0;i<sellist.size();i++)
        {
            totalprice=totalprice+sellist.get(i).price * sellist.get(i).quantity;

        }
        payamountfld.setText(String.valueOf(totalprice));

    }

    public void addcartbtnclicked(ActionEvent actionEvent) {
        int totalprice=0;
        Book bk=reserbookstbl.getSelectionModel().getSelectedItem();
        reslist.remove(bk);
        sellist.add(bk);
        setselbookstable();
        for(int i=0;i<sellist.size();i++)
        {
            totalprice=totalprice+sellist.get(i).price * sellist.get(i).quantity;

        }
        payamountfld.setText(String.valueOf(totalprice));

    }

    public void confirmpaymentbtnclicked(ActionEvent actionEvent) {
        DbConn oc;
        try {
            oc = new DbConn();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return;
        }

        try
        {
            for(int i=0;i<sellist.size();i++)
            {
                int reqid=sellist.get(i).requestid;
                int quant=sellist.get(i).quantity;
                String bkid=sellist.get(i).bookid;
                String query=String.format("UPDATE REQUESTBUY \n" +
                        "set isbought='Y' \n" +
                        "where requestid=%d",reqid);
                oc.updateDB(query);

                String query1=String.format("UPDATE SELLTYPE\n" +
                        "SET \n" +
                        "Count=Count-%d\n" +
                        "WHERE BOOK_ID='%s'",quant,bkid);
                oc.updateDB(query1);

                String query2=String.format("INSERT INTO BuyBook VALUES('%s','%s',%d,sysdate)",user,bkid,quant);
                oc.updateDB(query2);
            }


            reslist.clear();
            sellist.clear();
            useridfld.clear();
            payamountfld.clear();

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Successfully payment taken");
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
        //moretodo::update buybook table

    }

    public void homebtnclicked(ActionEvent actionEvent) throws Exception{
        Stage stage= (Stage) addcartbtn.getScene().getWindow();
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
