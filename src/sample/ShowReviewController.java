package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.ResultSet;

public class ShowReviewController {


    private Main main;

    public void setBook_idd(String book_id) {
        this.book_idd = book_id;
    }

    public String book_idd;
    public String username;
    public TableView<bookReview> bookstbl;
    public TextField avgstar;




    public TableColumn<bookReview,String> bookidclm;
    public TableColumn<bookReview,String> usernameclm;
    public TableColumn<bookReview, Integer> starclm;
    public TableColumn<bookReview,String> commentclm;

    ObservableList<bookReview> list= FXCollections.observableArrayList();


    public void load(String s, String user)
    {
        double totalStar=0;
        int amountCount=0;
        book_idd=s;
        username=user;
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


                amountCount++;
                totalStar+=rs.getDouble("star");
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
                double avg=totalStar/amountCount;
                avgstar.setText(Double.toString(avg));
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

    public void back(javafx.event.ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        System.out.println("before ger resource");
        loader.setLocation(getClass().getResource("logged-in.fxml"));
        Parent root = loader.load();
        System.out.println("after geresource");
        LoggedInController controller=loader.getController();
        //controller = loader.getController();
        System.out.println("before controller set");


        controller.setMain(this.main);
        controller.load(username);

        main.stage.setTitle("Welcome to Library World!");
        main.stage.setScene(new Scene(root, 800, 600));
        main.stage.show();
    }

    public void logout(javafx.event.ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Controller controller=loader.getController();

        controller.setMain(this.main);
        //controller.load(username);



        main.stage.setTitle("Login");
        main.stage.setScene(new Scene(root, 600, 400));
        main.stage.show();
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
