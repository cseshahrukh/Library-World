package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import startup.Book;
import startup.DbConn;

import java.sql.ResultSet;

public class BorrowwithoutreservationController {
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
    public TextField bookName;
    public TextField writer;
    public TextField category;
    public String user;
    public String bookid;
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
        resshelfclm.setCellValueFactory(new PropertyValueFactory<Book,String>("shelf"));
        resroomclm.setCellValueFactory(new PropertyValueFactory<Book,String>("room"));
        respublisherclm.setCellValueFactory(new PropertyValueFactory<Book,String>("publisher"));
        reserbookstbl.setItems(reslist);
        reserbookstbl.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    public void searchbtnclicked(javafx.event.ActionEvent event) {
        DbConn oc;
        Book bk=null;
        reslist.clear();

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
                    query = String.format("SELECT book_id,name,ShelfNo,RoomNo,price,publisher FROM BOOKS \n" +
                            "where LOWER(name) LIKE '%s'", searchname);
                }

                //only writer
                else if ((bookName == null || bookName.getText().compareTo("") == 0) && (writer != null && writer.getText().compareTo("") != 0) && (category == null || category.getText().compareTo("") == 0)) {
                    System.out.println("jeta chacchi shetai1");
                    String writername = writer.getText().trim().toLowerCase();
                    writername = "%" + writername + "%";

                    query = String.format("SELECT book_id,name,ShelfNo,RoomNo,price,publisher FROM BOOKS WHERE book_id IN(" +

                            "SELECT book_id from bookwriter where LOWER(writername) LIKE '%s')", writername);
                }


                //only category
                else if ((bookName == null || bookName.getText().compareTo("") == 0) && (writer == null || writer.getText().compareTo("") == 0) && (category != null && category.getText().compareTo("") != 0)) {

                    System.out.println("jeta chacchi shetai2");
                    String catname = category.getText().trim().toLowerCase();
                    catname = "%" + catname + "%";
                    System.out.println("catname is " + catname);
                    query = String.format("SELECT book_id,name,ShelfNo,RoomNo,price,publisher FROM BOOKS WHERE book_id IN(" +

                            "SELECT book_id from bookcategory where LOWER(categoryname) LIKE '%s')", catname);
                }

                //name and writer
                else if ((bookName != null && bookName.getText().compareTo("") != 0) && (writer != null && writer.getText().compareTo("") != 0) && (category == null || category.getText().compareTo("") == 0)) {
                    String searchname = bookName.getText().trim().toLowerCase();
                    searchname = "%" + searchname + "%";
                    String query1 = "", query2 = "";
                    query1 = String.format("SELECT book_id,name,ShelfNo,RoomNo,price,publisher FROM BOOKS \n" +
                            "where LOWER(name) LIKE '%s'", searchname);
                    System.out.println("jeta chacchi shetai1");
                    String writername = writer.getText().trim().toLowerCase();
                    writername = "%" + writername + "%";

                    query2 = String.format(" SELECT book_id,name,ShelfNo,RoomNo,price,publisher FROM BOOKS WHERE book_id IN(" +

                            "SELECT book_id from bookwriter where LOWER(writername) LIKE '%s')", writername);
                    query = query1 + " INTERSECT " + query2;

                }
                //name and category
                else if ((bookName != null && bookName.getText().compareTo("") != 0) && (writer == null || writer.getText().compareTo("") == 0) && (category != null && category.getText().compareTo("") != 0)) {
                    String searchname = bookName.getText().trim().toLowerCase();
                    searchname = "%" + searchname + "%";
                    String query1 = "", query2 = "";
                    query1 = String.format("SELECT book_id,name,ShelfNo,RoomNo,price,publisher FROM BOOKS \n" +
                            "where LOWER(name) LIKE '%s'", searchname);
                    String catname = category.getText().trim().toLowerCase();
                    catname = "%" + catname + "%";
                    System.out.println("catname is " + catname);
                    query2 = String.format("SELECT book_id,name,ShelfNo,RoomNo,price,publisher FROM BOOKS WHERE book_id IN(" +

                            "SELECT book_id from bookcategory where LOWER(categoryname) LIKE '%s')", catname);
                    query = query1 + " INTERSECT " + query2;

                }

                //Category and Writer
                else if ((bookName == null || bookName.getText().compareTo("") == 0) && (writer != null && writer.getText().compareTo("") != 0) && (category != null && category.getText().compareTo("") != 0)) {
                    String query1 = "", query2 = "";

                    String writername = writer.getText().trim().toLowerCase();
                    writername = "%" + writername + "%";

                    query1 = String.format("SELECT book_id,name,ShelfNo,RoomNo,price,publisher FROM BOOKS WHERE book_id IN(" +

                            "SELECT book_id from bookwriter where LOWER(writername) LIKE '%s')", writername);

                    String catname = category.getText().trim().toLowerCase();
                    catname = "%" + catname + "%";
                    System.out.println("catname is " + catname);
                    query2 = String.format("SELECT book_id,name,ShelfNo,RoomNo,price,publisher FROM BOOKS WHERE book_id IN(" +

                            "SELECT book_id from bookcategory where LOWER(categoryname) LIKE '%s')", catname);
                    query = query1 + " INTERSECT " + query2;

                }

                //BookName, Writer, Category
                else if ((bookName != null && bookName.getText().compareTo("") != 0) && (writer != null && writer.getText().compareTo("") != 0) && (category != null && category.getText().compareTo("") != 0)) {
                    String query0 = "";
                    String searchname = bookName.getText().trim().toLowerCase();
                    searchname = "%" + searchname + "%";
                    System.out.println("Search name is " + searchname);


                    query0 = String.format("SELECT book_id,name,ShelfNo,RoomNo,price,publisher FROM BOOKS \n" +
                            "where LOWER(name) LIKE '%s'", searchname);

                    String query1 = "", query2 = "";

                    String writername = writer.getText().trim().toLowerCase();
                    writername = "%" + writername + "%";

                    query1 = String.format("SELECT book_id,name,ShelfNo,RoomNo,price,publisher FROM BOOKS WHERE book_id IN(" +

                            "SELECT book_id from bookwriter where LOWER(writername) LIKE '%s')", writername);

                    String catname = category.getText().trim().toLowerCase();
                    catname = "%" + catname + "%";
                    System.out.println("catname is " + catname);
                    query2 = String.format("SELECT book_id,name,ShelfNo,RoomNo,price,publisher FROM BOOKS WHERE book_id IN(" +

                            "SELECT book_id from bookcategory where LOWER(categoryname) LIKE '%s')", catname);
                    query = query0 + " INTERSECT " + query1 + " INTERSECT " + query2;
                } else if ((bookName == null || bookName.getText().compareTo("") == 0) && (writer == null || writer.getText().compareTo("") == 0) && (category == null || category.getText().compareTo("") == 0)) {
                    query = String.format("SELECT book_id,name,ShelfNo,RoomNo,price,publisher FROM BOOKS ");
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
                    bk.publisher=rs.getString("publisher");
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

    public void selectbtnclicked(ActionEvent actionEvent) {
        DbConn oc;
        Book bk=reserbookstbl.getSelectionModel().getSelectedItem();
        reslist.remove(bk);
        this.bookid=bk.bookid;
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

    private int checkforfines(String user)
    {
        DbConn oc;
        try {
            oc = new DbConn();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return -1;
        }
        try
        {
            String query = String.format("SELECT BorrowID\n" +
                    "from BORROWBOOK\n" +
                    "where username='%s' AND ReturnDate is not null AND ReturnDate>ExpReturnDate\n" +
                    "AND BorrowID NOT IN\n" +
                    "(SELECT BorrowID from FinesPayment\n" +
                    "where UserID='%s' AND PaymentDate is not null)",user,user);
            ResultSet rs = oc.searchDB(query);
            while(rs.next())
            {
                int borrowid=rs.getInt("BorrowID");
                String query4=String.format("select count(*) from FinesPayment where BorrowID=%d",borrowid);
                ResultSet rs4=oc.searchDB(query4);
                rs4.next();
                int c=rs4.getInt(1);
                if(c==0)
                {
                    String query1=String.format("INSERT INTO FinesPayment(UserID,BorrowID) VALUES('%s',%d)",user,borrowid);
                    oc.updateDB(query1);
                }
                System.out.println("finestable updated");
            }


            String query2 = String.format("SELECT BorrowID\n" +
                    "from BORROWBOOK\n" +
                    "where username='%s' AND ReturnDate is null AND SYSDATE>ExpReturnDate\n" +
                    "AND BorrowID NOT IN\n" +
                    "(SELECT BorrowID from FinesPayment\n" +
                    "where UserID='%s' AND PaymentDate is not null)",user,user);
            ResultSet rs2 = oc.searchDB(query2);
            while(rs2.next())
            {
                int borrowid=rs2.getInt("BorrowID");
                String query4=String.format("select count(*) from FinesPayment where BorrowID=%d",borrowid);
                ResultSet rs4=oc.searchDB(query4);
                rs4.next();
                int c=rs4.getInt(1);
                if(c==0)
                {
                    String query1=String.format("INSERT INTO FinesPayment(UserID,BorrowID) VALUES('%s',%d)",user,borrowid);
                    oc.updateDB(query1);
                }
                System.out.println("finestable updated");
            }


            String query3=String.format("SELECT count(*) count from FinesPayment where userid='%s' and paymentdate is null",user);
            ResultSet rs3=oc.searchDB(query3);
            rs3.next();
            int count=rs3.getInt("count");
            if(count>0)
            {
                oc.close();
                return 1;
            }
            else
            {
                oc.close();
                return 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    private int borrowcheck(String user)
    {
        DbConn oc;
        try {
            oc = new DbConn();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return -1;
        }
        try
        {
            String query=String.format("select count(*) count from BORROWBOOK \n" +
                    "where username='%s' AND returndate is null",user);
            ResultSet rs=oc.searchDB(query);
            rs.next();
            int count=rs.getInt("count");
            if(count>0)
            {
                oc.close();
                return 1;
            }
            else
            {
                oc.close();
                return 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    private int bookavail(String user)
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
            String query1 = String.format("SELECT count(*) count from RequestBorrow \n" +
                    "where book_id='%s' AND ENDTIME>SYSDATE AND IsBorrowed='N'", bookid);
            ResultSet rs = oc.searchDB(query1);
            rs.next();
            resbooks=rs.getInt(1);

            String query3 = String.format("select inlibrary from borrowtype where book_id='%s'", bookid);
            ResultSet rs2 = oc.searchDB(query3);
            rs2.next();
            int inlibrary=rs2.getInt(1);

            if(resbooks<inlibrary)
            {
                oc.close();
                return 0;
            }
            else
            {
                oc.close();
                return 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private int activestatus(String user)
    {
        DbConn oc;
        try {
            oc = new DbConn();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return -1;
        }
        try
        {
            String query=String.format("SELECT count(*) count from USERS\n" +
                    "where username='%s' AND isactive<>'y'",user);
            ResultSet rs=oc.searchDB(query);
            rs.next();
            int count=rs.getInt("count");
            if(count>0)
            {
                oc.close();
                return 1;
            }
            else
            {
                oc.close();
                return 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    private int expire(String user)
    {
        DbConn oc;
        try {
            oc = new DbConn();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return -1;
        }
        try
        {
            String query=String.format("SELECT count(*) count from USERS\n" +
                    "where username='%s' AND expiredate<sysdate",user);
            ResultSet rs=oc.searchDB(query);
            rs.next();
            int count=rs.getInt("count");
            if(count>0)
            {
                oc.close();
                return 1;
            }
            else
            {
                oc.close();
                return 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private int check()
    {
        DbConn oc;
        try {
            oc = new DbConn();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return -1;
        }
        try
        {
            //active status
            int acstatus=activestatus(user);
            if(acstatus==1)
            {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("The user is not active.So he cannot borrow book now");
                a.show();
                return 0;
            }


            //expiredate
            int exp=expire(user);
            if(exp==1)
            {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("The expiredate has crossed.So he cannot borrow book now");
                a.show();
                return 0;
            }

            //fines
            int chkfines=checkforfines(user);
            if(chkfines==1)
            {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("The user has fines.So he cannot borrow book now");
                a.show();
                return 0;
            }

            //currently borrowed book
            int borrchk=borrowcheck(user);
            if(borrchk==1)
            {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("The user has not returned his borrowed book.So he cannot borrow book now");
                a.show();
                return 0;
            }

            //book unavailable
            int av=bookavail(user);
            if(av==1)
            {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Book currently unavailable");
                a.show();
                return 0;
            }

            if(acstatus!=-1 && av!=-1 && exp!=-1 && chkfines!=-1 && borrchk!=-1)
                return 1;
            else
                return -1;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void confirmbtnclicked(ActionEvent actionEvent) {
        DbConn oc;
        String username=usernamefld.getText();
        String bookid=bookidfld.getText();
        this.bookid=bookid;
        int chk=check();
        if(chk!=1)
        {
            System.out.println("Book Borrow not possible currently");
            return;
        }

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

            String querycount ="SELECT count(*) count from BorrowBook";
            ResultSet rs = oc.searchDB(querycount);
            rs.next();
            int count1=rs.getInt("count");
            count1++;

            String query2=String.format("INSERT INTO BorrowBook(username,book_id,BorrowID,BorrowDate,ExpReturnDate)\n" +
                    "values('%s','%s',%d,sysdate,sysdate+7)",username,bookid,count1);
            oc.updateDB(query2);

            Stage stage= (Stage)bookidfld.getScene().getWindow();
            stage.hide();
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
}
