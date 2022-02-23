package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import startup.Book;
import startup.DbConn;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookinfosController {
    public TextField writer2fld;
    public TextField writer3fld;
    public TextField publisherfld;
    public TextField booknamefld;
    public TextField writer1fld;
    public TextField pagesfld;
    public TextField pricefld;
    public TextField shelffld;
    public TextField roomfld;
    public TextField bookidfld;
    public TextField totselltypefld;
    public TextField resselltypefld;
    public TextField inlibfld;
    public TextField totborrfld;
    public TextField resborrfld;
    public TextField avselltypefld;

    private String bkid, bookname, writer1,writer2,writer3,publisher,shelf,room;
    private int pages,price;


    public void updatebtnclicked(ActionEvent actionEvent)
    {
        if(booknamefld.getText().equals("") || publisherfld.getText().equals("") || shelffld.getText().equals("") || roomfld.getText().equals("") || pagesfld.getText().equals("") || pricefld.getText().equals(""))
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Please fill up all the fields");
            a.showAndWait();
            return;
        }

        if(writer1fld.getText().equals("") && writer2fld.getText().equals("") && writer3fld.getText().equals(""))
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Please fill up all the fields");
            a.showAndWait();
            return;
        }

        DbConn oc;
        try {
            oc = new DbConn();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return;
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


        try
        {
            String query=String.format("UPDATE BOOKS\n" +
                    "SET name='%s',\n" +
                    "price=%d,\n" +
                    "pages=%d,publisher='%s',ROOMNO='%s',SHELFNO='%s'\n" +
                    "WHERE BOOK_ID='%s'",bookname,price,pages,publisher,room,shelf,bkid);
            oc.updateDB(query);

            String query1=String.format("DELETE FROM BOOKWRITER\n" +
                    "WHERE BOOK_ID='%s'",bkid);
            oc.updateDB(query1);

            if(!writer1.equals(""))
            {
                String query2=String.format("INSERT INTO BOOKWRITER VALUES('%s','%s')",bkid,writer1);
                oc.updateDB(query2);
                //System.out.println("inserted writer1 successfully");
            }
            if(!writer2.equals(""))
            {
                String query2=String.format("INSERT INTO BOOKWRITER VALUES('%s','%s')",bkid,writer2);
                oc.updateDB(query2);
                //System.out.println("inserted writer2 successfully");
            }
            if(!writer3.equals(""))
            {
                String query2=String.format("INSERT INTO BOOKWRITER VALUES('%s','%s')",bkid,writer3);
                oc.updateDB(query2);
                //System.out.println("inserted writer3 successfully");
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Book info updated successfully");
            alert.show();

        }catch (SQLException throwables) {
            throwables.printStackTrace();
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

    public void searchbtnclicked(ActionEvent actionEvent) {
        booknamefld.clear();
        publisherfld.clear();
        writer1fld.clear();
        writer2fld.clear();
        writer3fld.clear();
        pagesfld.clear();
        pricefld.clear();
        shelffld.clear();
        roomfld.clear();
        totselltypefld.clear();
        resselltypefld.clear();
        inlibfld.clear();
        totborrfld.clear();
        resborrfld.clear();
        avselltypefld.clear();
        DbConn oc;
        Book bk=null;
        try {
            oc = new DbConn();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return;
        }

        String search=bookidfld.getText();
        bkid=search;

        try {
            String query = String.format("SELECT name,ShelfNo,RoomNo,price,pages,publisher FROM BOOKS \n" +
                    "where book_id='%s' ",search);
            ResultSet rs = oc.searchDB(query);
            int count=0;

            while (rs.next()) {
                booknamefld.setText(rs.getString("name"));
                shelffld.setText(rs.getString("ShelfNo"));
                roomfld.setText(rs.getString("RoomNo"));
                writer1fld.setText("");
                writer2fld.setText("");
                writer3fld.setText("");
                pagesfld.setText(rs.getString("pages"));
                pricefld.setText(rs.getString("price"));
                publisherfld.setText(rs.getString("publisher"));

                String query1 = String.format("SELECT WriterName from BookWriter \n" +
                        "WHERE book_id='%s' ",search);
                ResultSet rs1 = oc.searchDB(query1);
                int count1=1;
                while (rs1.next()) {
                    if(count1==1)
                        writer1fld.setText(rs1.getString("WriterName"));
                    else if(count1==2)
                        writer2fld.setText(rs1.getString("WriterName"));
                    else if(count1==3)
                        writer3fld.setText(rs1.getString("WriterName"));
                    else
                        break;
                    count1++;

                }
                String query7=String.format("select count(*) from selltype where book_id='%s'",search);
                ResultSet rs7=oc.searchDB(query7);
                rs7.next();
                int x=rs7.getInt(1);
                if(x>0)
                {
                    String query2=String.format("select count from selltype where book_id='%s'",search);
                    ResultSet rs2=oc.searchDB(query2);
                    rs2.next();
                    int totsell=rs2.getInt("count");
                    totselltypefld.setText(String.valueOf(rs2.getInt("count")));

                    int resbooks=0;
                    String query3 = String.format("SELECT count(quantity) from RequestBuy \n" +
                            "where book_id='%s' AND ENDTIME>SYSDATE AND ISBOUGHT='N'",search);
                    ResultSet rs3= oc.searchDB(query3);
                    rs3.next();
                    int count3=rs3.getInt(1);
                    if(count3>0)
                    {
                        String query4 = String.format("SELECT sum(quantity) from RequestBuy \n" +
                                "where book_id='%s' AND ENDTIME>SYSDATE AND ISBOUGHT='N'", search);
                        ResultSet rs4 = oc.searchDB(query4);
                        rs4.next();
                        resbooks=rs4.getInt(1);
                    }
                    resselltypefld.setText(String.valueOf(resbooks));
                    avselltypefld.setText(String.valueOf(totsell-resbooks));

                }


                String query8=String.format("select count(*) from borrowtype where book_id='%s'",search);
                ResultSet rs8=oc.searchDB(query8);
                rs8.next();
                int y=rs8.getInt(1);
                if(y>0)
                {
                    String query5 = String.format("select total,inlibrary from borrowtype where book_id='%s'",search);
                    ResultSet rs5= oc.searchDB(query5);
                    rs5.next();
                    int totborr=rs5.getInt("total");
                    int inlib=rs5.getInt("inlibrary");
                    totborrfld.setText(String.valueOf(totborr));
                    inlibfld.setText(String.valueOf(inlib));

                    int borrres=0;
                    String query6= String.format("SELECT count(*) count from RequestBorrow \n" +
                            "where book_id='%s' AND ENDTIME>SYSDATE AND IsBorrowed='N'",search);
                    ResultSet rs6 = oc.searchDB(query6);
                    rs6.next();
                    borrres=rs6.getInt("count");
                    resborrfld.setText(String.valueOf(borrres));

                }

                count++;
            }
            if(count==0)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("No book found");
                alert.show();

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
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

    public void backbtnclicked(ActionEvent actionEvent) throws Exception{
        Stage stage= (Stage) booknamefld.getScene().getWindow();
        //stage.hide();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/getinfopage.fxml"));
        Parent root = loader.load();
        //stage.setTitle("Add books Page");
        stage.setScene(new Scene(root,665,482));
        stage.show();
    }

}
