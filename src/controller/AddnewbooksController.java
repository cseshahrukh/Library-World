package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.io.IOException;
import java.util.*;
import java.sql.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import startup.*;

public class AddnewbooksController {
    public TextField writer2fld;
    public TextField writer1fld;
    public TextField writer3fld;
    public TextField publisherfld;
    public TextField booknamefld;
    public TextField pagesfld;
    public TextField pricefld;
    public TextField shelffld;
    public CheckBox novelchkbox;
    public CheckBox biochkbox;
    public CheckBox poetrychkbox;
    public CheckBox dramachkbox;
    public CheckBox storychkbox;
    public TextField roomfld;
    public RadioButton selltyperdobtn;
    public RadioButton borrowtyperdobtn;
    public TextField qunatityfld;
    public Button confirmbtn;
    public ToggleGroup tg;
    public CheckBox childrenchkbox;
    public CheckBox comicschkbox;
    public CheckBox detectivechkbox;
    public CheckBox academicchkbox;
    public CheckBox adventurechkbox;
    private String bookid, bookname, writer1,writer2,writer3,publisher,shelf,room;
    private int pages,price,quantity;

    /*public TableView<Book> bookstable;
    public TableColumn<Book,String> bookidclm;
    public TableColumn<Book,String> booknameclm;
    public TableColumn<Book,String> publisherclm;
    public TableColumn<Book,String> writer1clm;
    public TableColumn<Car,String>  color3clm;
    public TableColumn<Car,String>  makeclm;
    public TableColumn<Car,String>  modelclm;
    public TableColumn<Car,Integer> priceclm;
    ObservableList<Book> list= FXCollections.observableArrayList();*/

    @FXML
    public void initialize() {
        tg = new ToggleGroup();
        selltyperdobtn.setToggleGroup(tg);
        borrowtyperdobtn.setToggleGroup(tg);

    }
    private boolean takeinputs()
    {
        if(booknamefld.getText().equals("") || publisherfld.getText().equals("") || shelffld.getText().equals("") || roomfld.getText().equals("") || pagesfld.getText().equals("") || pricefld.getText().equals("") || qunatityfld.getText().equals(""))
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Please fill up all the fields");
            a.showAndWait();
            return false;
        }

        if(writer1fld.getText().equals("") && writer2fld.getText().equals("") && writer3fld.getText().equals(""))
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Please fill up all the fields");
            a.showAndWait();
            return false;
        }

        bookname=booknamefld.getText().trim();
        writer1=writer1fld.getText().trim();
        writer2=writer2fld.getText().trim();
        writer3=writer3fld.getText().trim();
        publisher=publisherfld.getText().trim();
        shelf=shelffld.getText().trim();
        room=roomfld.getText().trim();
        pages = Integer.parseInt(pagesfld.getText().trim());
        price = Integer.parseInt(pricefld.getText().trim());
        quantity=Integer.parseInt(qunatityfld.getText().trim());
        return true;

    }

   /*private void setTable()
    {
        bookidclm.setCellValueFactory(new PropertyValueFactory<Book,String>("bookid"));
        booknameclm.setCellValueFactory(new PropertyValueFactory<Book,String>("bookname"));
        publisherclm.setCellValueFactory(new PropertyValueFactory<Book,String>("publisher"));
        color2clm.setCellValueFactory(new PropertyValueFactory<Car,String>("colour2"));
        color3clm.setCellValueFactory(new PropertyValueFactory<Car,String>("colour3"));
        makeclm.setCellValueFactory(new PropertyValueFactory<Car,String>("carmake"));
        modelclm.setCellValueFactory(new PropertyValueFactory<Car,String>("carmodel"));
        priceclm.setCellValueFactory(new PropertyValueFactory<Car,Integer>("price"));
        bookstable.setItems(list);
        bookstable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }*/


    /*public void showtables(ObservableList<Book> books) throws IOException {
        Stage stage1=new Stage();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/bookstable.fxml"));
        Parent root = loader.load();
        BooktableController bcon=loader.getController();
        bcon.setTable(books);
        //stage.setTitle("Manufacturer Home Page");
        //stage.setScene(new Scene(root,620,416));
        stage1.setScene(new Scene(root,200,300));
        stage1.show();
    }*/

    public void confirmbtnclicked(ActionEvent actionEvent) {
        boolean v=takeinputs();
        if(v==false)
            return;
        DbConn oc;
        String catname;
        int count1=0;
        try {
            oc = new DbConn();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return;
        }

        try {
            String query ="SELECT count(*) count from books";
            ResultSet rs = oc.searchDB(query);
            rs.next();
            count1=rs.getInt("count");
            count1++;
            bookid=String.valueOf(count1);

            String query1=String.format("INSERT INTO BOOKS (book_id, name, publisher, price, pages, language, shelfno, roomno) VALUES ('%s','%s','%s',%d,%d,'bangla','%s','%s')",bookid,bookname,publisher,price,pages,shelf,room);
            oc.updateDB(query1);
            System.out.println("inserted bookinfo successfully");

            if(!writer1.equals(""))
            {
                String query2=String.format("INSERT INTO BOOKWRITER VALUES('%s','%s')",bookid,writer1);
                oc.updateDB(query2);
                System.out.println("inserted writer1 successfully");
            }
            if(!writer2.equals(""))
            {
                String query2=String.format("INSERT INTO BOOKWRITER VALUES('%s','%s')",bookid,writer2);
                oc.updateDB(query2);
                System.out.println("inserted writer2 successfully");
            }
            if(!writer3.equals(""))
            {
                String query2=String.format("INSERT INTO BOOKWRITER VALUES('%s','%s')",bookid,writer3);
                oc.updateDB(query2);
                System.out.println("inserted writer3 successfully");
            }


            if(novelchkbox.isSelected())
            {
                catname="Novel";
                String query2=String.format("INSERT INTO BookCategory VALUES('%s','%s')",bookid,catname);
                oc.updateDB(query2);

            }
            if(biochkbox.isSelected())
            {
                catname="Biography";
                String query2=String.format("INSERT INTO BookCategory VALUES('%s','%s')",bookid,catname);
                oc.updateDB(query2);

            }
            if(storychkbox.isSelected())
            {
                catname="Story";
                String query2=String.format("INSERT INTO BookCategory VALUES('%s','%s')",bookid,catname);
                oc.updateDB(query2);

            }
            if(dramachkbox.isSelected())
            {
                catname="Drama";
                String query2=String.format("INSERT INTO BookCategory VALUES('%s','%s')",bookid,catname);
                oc.updateDB(query2);

            }
            if(poetrychkbox.isSelected())
            {
                catname="Poetry";
                String query2=String.format("INSERT INTO BookCategory VALUES('%s','%s')",bookid,catname);
                oc.updateDB(query2);

            }
            if(childrenchkbox.isSelected())
            {
                catname="Children";
                String query2=String.format("INSERT INTO BookCategory VALUES('%s','%s')",bookid,catname);
                oc.updateDB(query2);
            }
            if(comicschkbox.isSelected())
            {
                catname="Comics";
                String query2=String.format("INSERT INTO BookCategory VALUES('%s','%s')",bookid,catname);
                oc.updateDB(query2);
            }
            if(detectivechkbox.isSelected())
            {
                catname="Detective";
                String query2=String.format("INSERT INTO BookCategory VALUES('%s','%s')",bookid,catname);
                oc.updateDB(query2);
            }
            if(academicchkbox.isSelected())
            {
                catname="Academic";
                String query2=String.format("INSERT INTO BookCategory VALUES('%s','%s')",bookid,catname);
                oc.updateDB(query2);
            }
            if(adventurechkbox.isSelected())
            {
                catname="Adventure";
                String query2=String.format("INSERT INTO BookCategory VALUES('%s','%s')",bookid,catname);
                oc.updateDB(query2);
            }


            if(selltyperdobtn.isSelected())
            {
                String query2=String.format("INSERT INTO Selltype VALUES('%s',%d)",bookid,quantity);
                oc.updateDB(query2);

            }
            if(borrowtyperdobtn.isSelected())
            {
                String query2=String.format("INSERT INTO Borrowtype VALUES('%s',%d,%d)",bookid,quantity,quantity);
                oc.updateDB(query2);
            }


            Stage stage= (Stage) booknamefld.getScene().getWindow();
            stage.hide();
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Book added successfully");
            a.showAndWait();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /*public void searchbtnclicked(ActionEvent actionEvent) {
        //ArrayList<Book> books=new ArrayList<>();
        String bname=bookname.getText();
        DbConn oc = null;
        int count=0;
        try {
            oc = new DbConn();
            String query = String.format("select b1.bookid,b1.name,b1.price,b1.pages,b1.publisher,(select w.name from Writer w where w.ID=b2.writerID) writer \n" +
                    "from books b1,bookwriter b2\n" +
                    "where b1.bookid=b2.bookid and b1.bookname='%s'; " ,bname);
            ResultSet rs = oc.searchDB(query);


            while (rs.next()) {
                //bookids.add(rs.getString("BookID"));
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setDescription(rs.getString("description"));
                productList.add(p);
                Book b=new Book();
                b.bookid=rs.getString("b1.bookid");
                b.bookname=rs.getString("b1.name");
                b.price=rs.getInt("b1.price");
                b.pages=rs.getInt("b1.pages");
                b.publisher=rs.getString("b1.publisher");
                b.writer=rs.getString("writer");
                list.add(b);
                count++;
            }
            if(count==0)
            {
                System.out.println("no such book with the same name");

            }
            else
            {
                showtables(list);
            }

        } catch (Exception e) {
            System.out.println("Exception in listProducts: " + e);
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //return productList;


    }*/

    /*public void fillbtnclicked(ActionEvent actionEvent) {
        String vbookid=bookid.getText();
        DbConn oc = null;
        try {
            oc = new DbConn();
            String query = String.format("select * from books where bookid=%d",vbookid);
            ResultSet rs = oc.searchDB(query);
            if(rs.next())
            {
                bookname.setText(rs.getString("bookid"));
                Book b=new Book();
                b.bookid=rs.getString("b1.bookid");
                b.bookname=rs.getString("b1.name");
                b.price=rs.getInt("b1.price");
                b.pages=rs.getInt("b1.pages");
                b.publisher=rs.getString("b1.publisher");
                b.writer=rs.getString("writer");
                list.add(b);

            }
        } catch (Exception e) {
            System.out.println("Exception in addProduct: " + e);
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }*/

    /*public void savebtnclicked(ActionEvent actionEvent) {
        String vbookid=bookid.getText();
        String vbookname=bookname.getText();
        String vwriter1=writer1.getText();
        String vpublisher=publisher.getText();
        int vpages=Integer.parseInt(pages.getText());
        int vprice=Integer.parseInt(price.getText());
        DbConn oc = null;
        try {
            oc = new DbConn();
            String query = String.format("select * from products where id = %d", id);
            ResultSet rs = oc.searchDB(query);
            if (rs.next()) {
                System.out.println("Product with this Id already exisits");
            } else {
                String insertQuery = String.format(
                        "insert into products(id, name, price, description) values (%d, '%s', %f, '%s')", id, name,
                        price, desc);
                oc.updateDB(insertQuery);
            }
        } catch (Exception e) {
            System.out.println("Exception in addProduct: " + e);
        } finally {
            try {
                oc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }*/
}

