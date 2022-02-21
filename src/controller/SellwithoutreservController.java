package controller;

import com.sun.javafx.binding.StringFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import startup.*;
import java.sql.Date;
import java.sql.ResultSet;

public class SellwithoutreservController {
    //private Main main;

    @FXML
    private TextField writer;

    @FXML
    private TextField bookName;

    @FXML
    private TextField category;

    public TableView<Book> reserbookstbl;
    public TableColumn<Book,String> resbookidclm;
    public TableColumn<Book,String> resnameclm;
    public TableColumn<Book,String> reswriter1clm;
    public TableColumn<Book,String> reswriter2clm;
    public TableColumn<Book,String> reswriter3clm;
    public TableColumn<Book,String> resshelfclm;
    public TableColumn<Book,String> resroomclm;
    public TableView<Book> selbookstbl;
    public TableColumn<Book,String> selbookidclm;
    public TableColumn<Book,String> selnameclm;
    public TableColumn<Book,String> selwriter1clm;
    public TableColumn<Book,String> selwriter2clm;
    public TableColumn<Book,String> selwriter3clm;
    public TableColumn<Book,Integer> selnumberclm;
    public TableColumn<Book,String> selshelfclm;
    public TableColumn<Book,String> selroomclm;
    public TableColumn<Book,Integer> respriceclm;
    public TableColumn<Book,Integer> selpriceclm;
    public TextField payamountfld;
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



    @FXML
    public void search(javafx.event.ActionEvent event) {
        DbConn oc;
        Book bk=null;
        reslist.clear();
        //sellist.clear();
        //payamountfld.clear();

        if (true) {

            try {
                oc = new DbConn();
            } catch (Exception e) {
                System.out.println("problem in connection");
                return;
            }

            try {
                String query = "";


                //only book name
                if (bookName != null && (writer == null || writer.getText().compareTo("") == 0) && (category == null || category.getText().compareTo("") == 0)) {
                    String searchname = bookName.getText().trim().toLowerCase();
                    searchname = "%" + searchname + "%";
                    System.out.println("Search name is " + searchname);
                    query = String.format("SELECT book_id,name,ShelfNo,RoomNo,price FROM BOOKS \n" +
                            "where LOWER(name) LIKE '%s'", searchname);
                }

                //only writer
                else if ((bookName == null || bookName.getText().compareTo("") == 0) && (writer != null && writer.getText().compareTo("") != 0) && (category == null || category.getText().compareTo("") == 0)) {
                    System.out.println("jeta chacchi shetai1");
                    String writername = writer.getText().trim().toLowerCase();
                    writername = "%" + writername + "%";

                    query = String.format("SELECT book_id,name,ShelfNo,RoomNo,price FROM BOOKS WHERE book_id IN(" +

                            "SELECT book_id from bookwriter where LOWER(writername) LIKE '%s')", writername);
                }


                //only category
                else if ((bookName == null || bookName.getText().compareTo("") == 0) && (writer == null || writer.getText().compareTo("") == 0) && (category != null && category.getText().compareTo("") != 0)) {

                    System.out.println("jeta chacchi shetai2");
                    String catname = category.getText().trim().toLowerCase();
                    catname = "%" + catname + "%";
                    System.out.println("catname is " + catname);
                    query = String.format("SELECT book_id,name,ShelfNo,RoomNo,price FROM BOOKS WHERE book_id IN(" +

                            "SELECT book_id from CATEGORY where LOWER(category) LIKE '%s')", catname);
                }

                //name and writer
                else if ((bookName != null && bookName.getText().compareTo("") != 0) && (writer != null && writer.getText().compareTo("") != 0) && (category == null || category.getText().compareTo("") == 0)) {
                    String searchname = bookName.getText().trim().toLowerCase();
                    searchname = "%" + searchname + "%";
                    String query1 = "", query2 = "";
                    query1 = String.format("SELECT book_id,name,ShelfNo,RoomNo,price FROM BOOKS \n" +
                            "where LOWER(name) LIKE '%s'", searchname);
                    System.out.println("jeta chacchi shetai1");
                    String writername = writer.getText().trim().toLowerCase();
                    writername = "%" + writername + "%";

                    query2 = String.format(" SELECT book_id,name,ShelfNo,RoomNo,price FROM BOOKS WHERE book_id IN(" +

                            "SELECT book_id from bookwriter where LOWER(writername) LIKE '%s')", writername);
                    query = query1 + " INTERSECT " + query2;

                }
                //name and category
                else if ((bookName != null && bookName.getText().compareTo("") != 0) && (writer == null || writer.getText().compareTo("") == 0) && (category != null && category.getText().compareTo("") != 0)) {
                    String searchname = bookName.getText().trim().toLowerCase();
                    searchname = "%" + searchname + "%";
                    String query1 = "", query2 = "";
                    query1 = String.format("SELECT book_id,name,ShelfNo,RoomNo,price FROM BOOKS \n" +
                            "where LOWER(name) LIKE '%s'", searchname);
                    String catname = category.getText().trim().toLowerCase();
                    catname = "%" + catname + "%";
                    System.out.println("catname is " + catname);
                    query2 = String.format("SELECT book_id,name,ShelfNo,RoomNo,price FROM BOOKS WHERE book_id IN(" +

                            "SELECT book_id from CATEGORY where LOWER(category) LIKE '%s')", catname);
                    query = query1 + " INTERSECT " + query2;

                }

                //Category and Writer
                else if ((bookName == null || bookName.getText().compareTo("") == 0) && (writer != null && writer.getText().compareTo("") != 0) && (category != null && category.getText().compareTo("") != 0)) {
                    String query1 = "", query2 = "";

                    String writername = writer.getText().trim().toLowerCase();
                    writername = "%" + writername + "%";

                    query1 = String.format("SELECT book_id,name,ShelfNo,RoomNo,price FROM BOOKS WHERE book_id IN(" +

                            "SELECT book_id from bookwriter where LOWER(writername) LIKE '%s')", writername);

                    String catname = category.getText().trim().toLowerCase();
                    catname = "%" + catname + "%";
                    System.out.println("catname is " + catname);
                    query2 = String.format("SELECT book_id,name,ShelfNo,RoomNo,price FROM BOOKS WHERE book_id IN(" +

                            "SELECT book_id from CATEGORY where LOWER(category) LIKE '%s')", catname);
                    query = query1 + " INTERSECT " + query2;

                }

                //BookName, Writer, Category
                else if ((bookName != null && bookName.getText().compareTo("") != 0) && (writer != null && writer.getText().compareTo("") != 0) && (category != null && category.getText().compareTo("") != 0)) {
                    String query0 = "";
                    String searchname = bookName.getText().trim().toLowerCase();
                    searchname = "%" + searchname + "%";
                    System.out.println("Search name is " + searchname);


                    query0 = String.format("SELECT book_id,name,ShelfNo,RoomNo,price FROM BOOKS \n" +
                            "where LOWER(name) LIKE '%s'", searchname);

                    String query1 = "", query2 = "";

                    String writername = writer.getText().trim().toLowerCase();
                    writername = "%" + writername + "%";

                    query1 = String.format("SELECT book_id,name,ShelfNo,RoomNo,price FROM BOOKS WHERE book_id IN(" +

                            "SELECT book_id from bookwriter where LOWER(writername) LIKE '%s')", writername);

                    String catname = category.getText().trim().toLowerCase();
                    catname = "%" + catname + "%";
                    System.out.println("catname is " + catname);
                    query2 = String.format("SELECT book_id,name,ShelfNo,RoomNo,price FROM BOOKS WHERE book_id IN(" +

                            "SELECT book_id from CATEGORY where LOWER(category) LIKE '%s')", catname);
                    query = query0 + " INTERSECT " + query1 + " INTERSECT " + query2;
                } else if ((bookName == null || bookName.getText().compareTo("") == 0) && (writer == null || writer.getText().compareTo("") == 0) && (category == null || category.getText().compareTo("") == 0)) {
                    query = String.format("SELECT book_id,name,ShelfNo,RoomNo,price FROM BOOKS ");
                }

                ResultSet rs = oc.searchDB(query);
                int count = 0;

                System.out.println("After query search");
                while (rs.next()) {
                    bk = new Book();
                    bk.bookid = rs.getString("book_id");
                    bk.bookname = rs.getString("name");
                    bk.shelf = rs.getString("ShelfNo");
                    bk.room = rs.getString("RoomNo");
                    bk.price=rs.getInt("price");
                    bk.writer1 = "N/A";
                    bk.writer2 = "N/A";
                    bk.writer3 = "N/A";
                    String query1 = String.format("SELECT WriterName from BookWriter \n" +
                            "WHERE book_id='%s' ", bk.bookid);
                    ResultSet rs1 = oc.searchDB(query1);
                    System.out.println("After wrtier search");
                    int count1 = 1;
                    while (rs1.next()) {
                        if (count1 == 1)
                            bk.writer1 = rs1.getString("WriterName");
                        else if (count1 == 2)
                            bk.writer2 = rs1.getString("WriterName");
                        else if (count1 == 3)
                            bk.writer3 = rs1.getString("WriterName");
                        else
                            break;
                        count1++;

                    }
                    //System.out.println(bk.get);
                    System.out.println("count is " + count1);
                    reslist.add(bk);
                    count++;
                }
                if (count == 0) {
                    System.out.println("No books found");
                } else {
                    System.out.println("Count is " + count);
                    setresbookstable();
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
        }
    }


    public void addcartbtnclicked(ActionEvent actionEvent) throws Exception {
        Book bk=null;
        bk=reserbookstbl.getSelectionModel().getSelectedItem();
        if(bk==null)
            return;
        Stage stage=new Stage();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/sellqtyminiwindow.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root,327,168));
        stage.setResizable(false);
        SellqtyminiwindowController con=loader.getController();
        con.sellcon=this;
        stage.show();

    }
    private int canbuybooks(String bookid,int quan)
    {
        int resbooks=0;
        DbConn oc;
        try {
            oc = new DbConn();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return -1;
        }
        try {
            String query1 = String.format("SELECT count(quantity) from RequestBuy \n" +
                    "where book_id='%s' AND ENDTIME>SYSDATE AND ISBOUGHT='N'", bookid);
            ResultSet rs = oc.searchDB(query1);
            rs.next();
            int count=rs.getInt(1);
            if(count>0)
            {
                String query2 = String.format("SELECT sum(quantity) from RequestBuy \n" +
                        "where book_id='%s' AND ENDTIME>SYSDATE AND ISBOUGHT='N'", bookid);
                ResultSet rs1 = oc.searchDB(query2);
                rs1.next();
                resbooks=rs1.getInt(1);
            }

            String query3 = String.format("select count from selltype where book_id='%s'", bookid);
            ResultSet rs2 = oc.searchDB(query3);
            rs2.next();
            int total=rs2.getInt(1);

            if(quan<=total-resbooks)
                return 1;
            else return 0;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void addcart(int quan)
    {
        int totalprice=0;
        Book bk=reserbookstbl.getSelectionModel().getSelectedItem();
        if(canbuybooks(bk.bookid,quan)==1)
        {
            bk.quantity=quan;
            reslist.remove(bk);
            sellist.add(bk);
            setselbookstable();
            for(int i=0;i<sellist.size();i++)
            {
                totalprice=totalprice+sellist.get(i).price * sellist.get(i).quantity;

            }
            payamountfld.setText(String.valueOf(totalprice));

        }
        else
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Book buy not possible due to unavalilability of the book currently");
            a.showAndWait();
        }

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
                int quant=sellist.get(i).quantity;
                String bkid=sellist.get(i).bookid;

                String query1=String.format("UPDATE SELLTYPE\n" +
                        "SET \n" +
                        "Count=Count-%d\n" +
                        "WHERE BOOK_ID='%s'",quant,bkid);
                oc.updateDB(query1);

                String query2=String.format("INSERT INTO BuyBook VALUES('%s','%s',%d,sysdate)",user,bkid,quant);
                oc.updateDB(query2);
            }

            bookName.clear();
            writer.clear();
            category.clear();
            reslist.clear();
            sellist.clear();
            payamountfld.clear();

            Stage stage= (Stage) bookName.getScene().getWindow();
            stage.hide();

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
    }
}
