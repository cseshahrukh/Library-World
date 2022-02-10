package sample;

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

        OracleConnect oc;
        Book bk;
        list.clear();
        if (bookName!=null)
        {

            try {
                oc = new OracleConnect();
            } catch (Exception e) {
                System.out.println("problem in connection");
                return;
            }
            String searchname=bookName.getText().trim().toLowerCase();
            try {

                /*
                String query = String.format("SELECT book_id,name,ShelfNo,RoomNo FROM BOOKS \n" +
                        "where LOWER(name)='%s' ",searchname);
                */

                searchname="%"+searchname+"%";
                System.out.println("Search name is "+searchname);
                String query = String.format("SELECT book_id,name,ShelfNo,RoomNo FROM BOOKS \n" +
                        "where LOWER(name) LIKE '%s'",  searchname);


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

    public void buybtnclicked(javafx.event.ActionEvent actionEvent)
    {
        OracleConnect oc;
        Book bk=new Book();
        User user=new User();

        try {
            oc = new OracleConnect();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return;
        }

        String bookid=bookidfld.getText();

        try {
            String query = String.format("SELECT book_id,count, countRequest FROM SELLTYPE \n" +
                        "where LOWER(book_id)='%s' ",bookid);
                ResultSet rs = oc.searchDB(query);

                while (rs.next()) {
                    int amount=rs.getInt("count");
                    int amountRequest= rs.getInt("countRequest");
                    if (amount>0)
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
                            String updateQuery=String.format("UPDATE SELLTYPE SET count = %d, countrequest = %d WHERE book_id='%s' ",amount-1, amountRequest+1, bookid);

                            oc.updateDB(updateQuery);
                            updateQuery=String.format("Insert into REQUESTBUY (username, book_id, request_time, end_time, quantity, isbought)" +
                                    "values('%s', '%s', SYSDATE, SYSDATE+8/24, '%s', 'n')", username, bookid, buyquantity.getText());
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


    @FXML
    public void deleteRowFromTable(ActionEvent event)
    {
        tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItem());
    }
}
