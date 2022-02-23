package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import startup.*;
import java.sql.ResultSet;

public class showreviewsController {
    public TableView<Book> bookstbl;
    public TableColumn<Book,String> bookidclm;
    public TableColumn<Book,String> booknameclm;
    public TableColumn<Book,String> usernameclm;
    public TableColumn<Book,Integer> starclm;
    public TableColumn<Book,String> commentclm;
    public TextField usernamefld;
    public TextField bookidfld;
    ObservableList<Book> list= FXCollections.observableArrayList();

    private void setTable()
    {
        bookidclm.setCellValueFactory(new PropertyValueFactory<Book,String>("bookid"));
        booknameclm.setCellValueFactory(new PropertyValueFactory<Book,String>("bookname"));
        usernameclm.setCellValueFactory(new PropertyValueFactory<Book,String>("userid"));
        starclm.setCellValueFactory(new PropertyValueFactory<Book,Integer>("star"));
        commentclm.setCellValueFactory(new PropertyValueFactory<Book,String>("comment"));
        bookstbl.setItems(list);
    }

    public void okbtnclicked(ActionEvent actionEvent) {
        DbConn oc;
        Book bk;
        list.clear();
        String bookid=bookidfld.getText();
        String username=usernamefld.getText();

        try {
            oc = new DbConn();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return;
        }
        try {
            String query="";
            if(!bookid.equals("") && !username.equals(""))
            {
                query = String.format("SELECT * FROM REVIEW WHERE book_id='%s' AND username='%s'", bookid,username);
            }

            else if(!bookid.equals("") && username.equals(""))
            {
                query = String.format("SELECT * FROM REVIEW WHERE book_id='%s'", bookid);

            }
            else if(bookid.equals("") && !username.equals(""))
            {
                query = String.format("SELECT * FROM REVIEW WHERE username='%s'",username);

            }
            else
            {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("At least one field should be filled");
                a.showAndWait();
                return;

            }

            ResultSet rs = oc.searchDB(query);
            System.out.println("After query search");
            int count=0;
            while (rs.next()) {
                //System.out.println(rs);
                bk=new Book();
                bk.bookid=rs.getString("book_id");
                bk.userid=rs.getString("username");
                bk.star=rs.getInt("star");
                bk.comment=rs.getString("commont");
                String query1=String.format("select name from books where book_id='%s'",bk.bookid);
                ResultSet rs1=oc.searchDB(query1);
                rs1.next();
                bk.bookname=rs1.getString("name");
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

    public void backbtnclicked(ActionEvent actionEvent) throws Exception {
        Stage stage= (Stage) usernamefld.getScene().getWindow();
        //stage.hide();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/getinfopage.fxml"));
        Parent root = loader.load();
        //stage.setTitle("Add books Page");
        stage.setScene(new Scene(root,665,482));
        stage.show();
    }
}
