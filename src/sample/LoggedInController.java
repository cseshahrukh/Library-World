package sample;

import com.sun.javafx.binding.StringFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.awt.event.ActionEvent;


import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.sql.ResultSet;

public class LoggedInController {
    private Main main;



    @FXML
    private Button button_logout;

    @FXML
    private Label label_welcome;

    @FXML
    private Label label_fav_channel;

    @FXML
    private TextField writer;

    @FXML
    private TextField bookName;

    @FXML
    private TextField category;


    public TextField searchbookfld;
    public Button searchbtn;
    public Button selectbtn;
    public Button borrowbtn;
    public Button buybtn;
    public TextField bookidfld;
    public TextField qunatityfld;
    public RadioButton selltyperdobtn;
    public RadioButton borrowtyperdobtn;
    public Button confirmbtn;
    public TableView<Book> bookstbl;
    public ToggleGroup tg;
    public String username;
    public TextField buyquantity;

    @FXML
    private TableView<Book> tableView;

    public TableColumn<Book,String> bookidclm;
    public TableColumn<Book,String> booknameclm;
    public TableColumn<Book,String> writer1clm;
    public TableColumn<Book,String> writer2clm;
    public TableColumn<Book,String> writer3clm;
    public TableColumn<Book,String> shelfclm;
    public TableColumn<Book,String> roomclm;
    ObservableList<Book> list= FXCollections.observableArrayList();

    public void initialize()
    {
        bookidfld.setEditable(false);
    }
    private void setTable()
    {
        bookidclm.setCellValueFactory(new PropertyValueFactory<Book,String>("book_id"));
        booknameclm.setCellValueFactory(new PropertyValueFactory<Book,String>("name"));
        writer1clm.setCellValueFactory(new PropertyValueFactory<Book,String>("writer1"));
        writer2clm.setCellValueFactory(new PropertyValueFactory<Book,String>("writer2"));
        writer3clm.setCellValueFactory(new PropertyValueFactory<Book,String>("writer3"));
        shelfclm.setCellValueFactory(new PropertyValueFactory<Book,String>("shelfNo"));
        roomclm.setCellValueFactory(new PropertyValueFactory<Book,String>("roomNo"));
        bookstbl.setItems(list);
        bookstbl.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    public void logoutAction(javafx.event.ActionEvent event) throws Exception {

        main.home("sample.fxml", null);
        //DBUtils.home( "sample.sample.fxml");
    }

    @FXML
    public void search(javafx.event.ActionEvent event) {

        bookidfld.setEditable(false);

        OracleConnect oc;
        Book bk;
        list.clear();
        if (true)
        {

            try {
                oc = new OracleConnect();
            } catch (Exception e) {
                System.out.println("problem in connection");
                return;
            }

            try {
                String query="";


                //only book name
                if (bookName!=null && (writer==null || writer.getText().compareTo("")==0) && (category==null || category.getText().compareTo("")==0))
                {
                    String searchname=bookName.getText().trim().toLowerCase();
                    searchname="%"+searchname+"%";
                    System.out.println("Search name is "+searchname);
                    query = String.format("SELECT book_id,name,ShelfNo,RoomNo FROM BOOKS \n" +
                            "where LOWER(name) LIKE '%s'",  searchname);
                }

                //only writer
                else if ((bookName==null || bookName.getText().compareTo("")==0)&& (writer!=null && writer.getText().compareTo("")!=0) && (category==null || category.getText().compareTo("")==0))
                {
                    System.out.println("jeta chacchi shetai1");
                    String writername=writer.getText().trim().toLowerCase();
                    writername="%"+writername+"%";

                    query=String.format("SELECT book_id,name,ShelfNo,RoomNo FROM BOOKS WHERE book_id IN(" +

                            "SELECT book_id from bookwriter where LOWER(writername) LIKE '%s')", writername);
                }


                //only category
                else if ((bookName==null || bookName.getText().compareTo("")==0)&& (writer==null || writer.getText().compareTo("")==0) && (category!=null && category.getText().compareTo("")!=0))
                {

                    System.out.println("jeta chacchi shetai2");
                    String catname=category.getText().trim().toLowerCase();
                    catname="%"+catname+"%";
                    System.out.println("catname is "+catname);
                    query=String.format("SELECT book_id,name,ShelfNo,RoomNo FROM BOOKS WHERE book_id IN(" +

                            "SELECT book_id from CATEGORY where LOWER(category) LIKE '%s')", catname);
                }

                //name and writer
                else if ((bookName!=null && bookName.getText().compareTo("")!=0) && (writer!=null && writer.getText().compareTo("")!=0) && (category==null || category.getText().compareTo("")==0))
                {
                    String searchname=bookName.getText().trim().toLowerCase();
                    searchname="%"+searchname+"%";
                    String query1="", query2="";
                    query1 = String.format("SELECT book_id,name,ShelfNo,RoomNo FROM BOOKS \n" +
                            "where LOWER(name) LIKE '%s'",  searchname);
                    System.out.println("jeta chacchi shetai1");
                    String writername=writer.getText().trim().toLowerCase();
                    writername="%"+writername+"%";

                    query2=String.format(" SELECT book_id,name,ShelfNo,RoomNo FROM BOOKS WHERE book_id IN(" +

                            "SELECT book_id from bookwriter where LOWER(writername) LIKE '%s')", writername);
                    query= query1+" INTERSECT "+ query2;

                }
                //name and category
                else if ((bookName!=null && bookName.getText().compareTo("")!=0) && (writer==null || writer.getText().compareTo("")==0) && (category!=null && category.getText().compareTo("")!=0))
                {
                    String searchname=bookName.getText().trim().toLowerCase();
                    searchname="%"+searchname+"%";
                    String query1="", query2="";
                    query1 = String.format("SELECT book_id,name,ShelfNo,RoomNo FROM BOOKS \n" +
                            "where LOWER(name) LIKE '%s'",  searchname);
                    String catname=category.getText().trim().toLowerCase();
                    catname="%"+catname+"%";
                    System.out.println("catname is "+catname);
                    query2=String.format("SELECT book_id,name,ShelfNo,RoomNo FROM BOOKS WHERE book_id IN(" +

                            "SELECT book_id from CATEGORY where LOWER(category) LIKE '%s')", catname);
                    query= query1+" INTERSECT "+ query2;

                }

                //Category and Writer
                else if ((bookName==null || bookName.getText().compareTo("")==0) && (writer!=null && writer.getText().compareTo("")!=0) && (category!=null && category.getText().compareTo("")!=0))
                {
                    String query1="", query2="";

                    String writername=writer.getText().trim().toLowerCase();
                    writername="%"+writername+"%";

                    query1=String.format("SELECT book_id,name,ShelfNo,RoomNo FROM BOOKS WHERE book_id IN(" +

                            "SELECT book_id from bookwriter where LOWER(writername) LIKE '%s')", writername);

                    String catname=category.getText().trim().toLowerCase();
                    catname="%"+catname+"%";
                    System.out.println("catname is "+catname);
                    query2=String.format("SELECT book_id,name,ShelfNo,RoomNo FROM BOOKS WHERE book_id IN(" +

                            "SELECT book_id from CATEGORY where LOWER(category) LIKE '%s')", catname);
                    query= query1+" INTERSECT "+ query2;

                }

                //BookName, Writer, Category
                else if ((bookName!=null && bookName.getText().compareTo("")!=0) && (writer!=null && writer.getText().compareTo("")!=0) && (category!=null && category.getText().compareTo("")!=0))
                {
                            String query0="";
                    String searchname=bookName.getText().trim().toLowerCase();
                    searchname="%"+searchname+"%";
                    System.out.println("Search name is "+searchname);


                    query0 = String.format("SELECT book_id,name,ShelfNo,RoomNo FROM BOOKS \n" +
                            "where LOWER(name) LIKE '%s'",  searchname);

                    String query1="", query2="";

                    String writername=writer.getText().trim().toLowerCase();
                    writername="%"+writername+"%";

                    query1=String.format("SELECT book_id,name,ShelfNo,RoomNo FROM BOOKS WHERE book_id IN(" +

                            "SELECT book_id from bookwriter where LOWER(writername) LIKE '%s')", writername);

                    String catname=category.getText().trim().toLowerCase();
                    catname="%"+catname+"%";
                    System.out.println("catname is "+catname);
                    query2=String.format("SELECT book_id,name,ShelfNo,RoomNo FROM BOOKS WHERE book_id IN(" +

                            "SELECT book_id from CATEGORY where LOWER(category) LIKE '%s')", catname);
                    query= query0+ " INTERSECT "+query1+" INTERSECT "+ query2;
                }

                else if ((bookName==null || bookName.getText().compareTo("")==0)&& (writer==null || writer.getText().compareTo("")==0) && (category==null || category.getText().compareTo("")==0))
                {
                    query=String.format("SELECT book_id,name,ShelfNo,RoomNo FROM BOOKS ");
                }

                ResultSet rs = oc.searchDB(query);
                int count=0;

                System.out.println("After query search");
                while (rs.next()) {
                    bk=new Book();
                    bk.book_id=rs.getString("book_id");
                    bk.name=rs.getString("name");
                    bk.shelfNo=rs.getString("ShelfNo");
                    bk.roomNo=rs.getString("RoomNo");
                    bk.writer1="N/A";
                    bk.writer2="N/A";
                    bk.writer3="N/A";
                    String query1 = String.format("SELECT WriterName from BookWriter \n" +
                            "WHERE book_id='%s' ",bk.book_id);
                    ResultSet rs1 = oc.searchDB(query1);
                    System.out.println("After wrtier search");
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
                    //System.out.println(bk.get);
                    System.out.println("count is "+count1);
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
        }
    }


    public void selectbtnclicked(javafx.event.ActionEvent actionEvent) {
        Book bk=bookstbl.getSelectionModel().getSelectedItem();
        bookidfld.setText(bk.book_id);
    }

    public void borrowbtnclicked(javafx.event.ActionEvent actionEvent)
    {
        Book bk=bookstbl.getSelectionModel().getSelectedItem();
        bookidfld.setText(bk.book_id);
        if (bookidfld==null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Select A Row");
            alert.show();
        }
        OracleConnect oc;
        //Book bk=new Book();
        User user=new User();

        try {
            oc = new OracleConnect();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return;
        }

        String bookid=bookidfld.getText();

        try {
            String query = String.format("SELECT book_id,count FROM BORROWTYPE \n" +
                    "where LOWER(book_id)='%s' ",bookid);
            ResultSet rs = oc.searchDB(query);


            while (rs.next()) {
                int amount=rs.getInt("count");
                //int amountRequest= rs.getInt("countRequest");
                String query2= String.format("SELECT count(*) from requestborrow where book_id='%s' AND isborrowed='n' AND SYSDATE<END_TIME", bookid);
                ResultSet rs2=oc.searchDB(query2);
                rs2.next();
                int amountRequest=rs2.getInt(1);


                int quantity=1;

                if (amount-amountRequest-quantity>=0)
                {
                    String query1 = String.format("SELECT * from USERS \n" +
                            "WHERE username='%s' ",username);

                    ResultSet rs1=oc.searchDB(query1);
                    while(rs1.next())
                    {
                        String isActive=rs1.getString("ISACTIVE");
                        String canBorrow=rs1.getString("CANBORROW");
                        String expireDate=rs1.getString("EXPIREDATE");
                        user.setIsActive(isActive);
                        user.setCanBorrow(canBorrow);
                        user.setExpireDate(expireDate);
                    }


                    if (user.getCanBorrow().compareTo("y")==0 && user.getIsActive().compareTo("y")==0)
                    {

                        //already ekta request kore feleche
                            query = String.format("SELECT * FROM REQUESTBORRow \n" +
                                    "where username = '%s' AND ISBORROWED='n' AND (SYSDATE<END_TIME)",  username);
                            rs = oc.searchDB(query);
                            if (rs.next()) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setContentText("You have already requested to borrow a book");
                                alert.show();
                                return;
                            }


                            //already borrow kore bashay rekhe diche ekta book
                        query = String.format("SELECT * FROM REQUESTBORRow \n" +
                                "where username = '%s' AND ISBORROWED='y' AND ISRETURNED='n'",  username);
                        rs = oc.searchDB(query);
                        if (rs.next()) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("You haven't returned the previous book");
                            alert.show();
                            return;
                        }



                        //String updateQuery=String.format("UPDATE SELLTYPE SET count = %d, countrequest = %d WHERE book_id='%s' ",amount-quantity, amountRequest+quantity, bookid);

                        //oc.updateDB(updateQuery);
                        String updateQuery=String.format("Insert into REQUESTBORRow (username, book_id, request_time, end_time, isborrowed, isreturned)" +
                                "values('%s', '%s', SYSDATE, SYSDATE+8/24, 'n', 'n')", username, bookid);
                        oc.updateDB(updateQuery);
                        //System.out.println("here amount and amountRequest are "+ amount+" "+amountRequest);
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("Success! Come in 8 hours to borrow it");
                        alert.show();


                    }

                    else
                    {
                        System.out.println("Not a valid user to buy");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("You can't buy");
                        alert.show();
                    }
                }

                else
                {
                    System.out.println("Password Not Found");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Not Enough Books!! Sorry");
                    alert.show();
                }
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


    public void buybtnclicked1(javafx.event.ActionEvent actionEvent)
    {
        Book bk=bookstbl.getSelectionModel().getSelectedItem();
        bookidfld.setText(bk.book_id);
        if (bookidfld==null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Select A Row");
            alert.show();
        }
        OracleConnect oc;
        //Book bk=new Book();
        User user=new User();

        try {
            oc = new OracleConnect();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return;
        }

        String bookid=bookidfld.getText();

        try {
            String query = String.format("SELECT book_id,count FROM SELLTYPE \n" +
                    "where LOWER(book_id)='%s' ",bookid);
            ResultSet rs = oc.searchDB(query);


            while (rs.next()) {
                int amount=rs.getInt("count");
                //int amountRequest= rs.getInt("countRequest");
                String query2= String.format("SELECT sum(quantity) from requestbuy where book_id='%s' AND isbought='n' AND SYSDATE<END_TIME", bookid);
                ResultSet rs2=oc.searchDB(query2);
                rs2.next();
                int amountRequest=rs2.getInt(1);


                int quantity=1;
                if (buyquantity!=null && buyquantity.getText().compareTo("")!=0)
                {
                    try
                    {
                        quantity=Integer.parseInt(buyquantity.getText());
                    }
                    catch (Exception e)
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Give Valid Number");
                        alert.show();
                        return;
                    }

                }
                if (amount-amountRequest-quantity>=0)
                {
                    String query1 = String.format("SELECT * from USERS \n" +
                            "WHERE username='%s' ",username);

                    ResultSet rs1=oc.searchDB(query1);
                    while(rs1.next())
                    {
                        String isActive=rs1.getString("ISACTIVE");
                        String canBorrow=rs1.getString("CANBORROW");
                        String expireDate=rs1.getString("EXPIREDATE");
                        user.setIsActive(isActive);
                        user.setCanBorrow(canBorrow);
                        user.setExpireDate(expireDate);
                    }


                    if (user.getCanBorrow().compareTo("y")==0 && user.getIsActive().compareTo("y")==0)
                    {
                            /* Eita borrow te applicable hobe just
                            query = String.format("SELECT * FROM REQUESTBUY \n" +
                                    "where username = '%s' AND ISBOUTGHT='n' AND (SYSDATE<REQUESTTIME)",  username);
                            rs = oc.searchDB(query);
                            if (rs.next()) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setContentText("You have already requested");
                                alert.show();
                                return;
                            }
                             */


                        //String updateQuery=String.format("UPDATE SELLTYPE SET count = %d, countrequest = %d WHERE book_id='%s' ",amount-quantity, amountRequest+quantity, bookid);

                        //oc.updateDB(updateQuery);
                        String updateQuery=String.format("Insert into REQUESTBUY (username, book_id, request_time, end_time, quantity, isbought)" +
                                "values('%s', '%s', SYSDATE, SYSDATE+8/24, %d, 'n')", username, bookid, quantity);
                        oc.updateDB(updateQuery);
                        System.out.println("here amount and amountRequest are "+ amount+" "+amountRequest);
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("Success! Come in 8 hours to take it");
                        alert.show();


                    }

                    else
                    {
                        System.out.println("Not a valid user to buy");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("You can't buy");
                        alert.show();
                    }
                }

                else
                {
                    System.out.println("Password Not Found");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Not Enough Books!! Sorry");
                    alert.show();
                }
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

    void setMain(Main main) {
        this.main = main;
    }
}
