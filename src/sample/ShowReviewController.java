package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;

public class ShowReviewController {


    private Main main;

    public void setBook_idd(String book_id) {
        this.book_idd = book_id;
    }

    public String book_idd;
    public TableView<bookReview> bookstbl;



    public TableColumn<bookReview,String> bookidclm;
    public TableColumn<bookReview,String> usernameclm;
    public TableColumn<bookReview, Integer> starclm;
    public TableColumn<bookReview,String> commentclm;

    ObservableList<bookReview> list= FXCollections.observableArrayList();


    public void load(String s)
    {
        book_idd=s;
        System.out.println("inside showreview controller bookid is "+book_idd);
        //bookidfld.setEditable(false);

        OracleConnect oc;
        bookReview bk;
        list.clear();

        try {
            oc = new OracleConnect();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return;
        }

        try {
            String query="";

            query = String.format("SELECT * FROM REVIEW WHERE book_id = '%s'", book_idd);

            ResultSet rs = oc.searchDB(query);


            System.out.println("After query search");
            int count=0;
            while (rs.next()) {
                System.out.println(rs);
                bk=new bookReview();
                bk.Book_id=rs.getString("book_id");
                bk.username=rs.getString("username");
                bk.Star=rs.getInt("star");
                bk.Comment=rs.getString("commont");


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
    private void setTable()
    {
        System.out.println("inside Set Table ");
        bookidclm.setCellValueFactory(new PropertyValueFactory<bookReview,String>("Book_id"));
        usernameclm.setCellValueFactory(new PropertyValueFactory<bookReview,String>("username"));
        starclm.setCellValueFactory(new PropertyValueFactory<bookReview,Integer>("Star"));
        commentclm.setCellValueFactory(new PropertyValueFactory<bookReview,String>("Comment"));

        System.out.println("inside set table but before set items");
        System.out.println("list size is "+list.size());
        bookstbl.setItems(list);
        bookstbl.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
