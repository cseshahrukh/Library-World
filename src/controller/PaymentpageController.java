package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import startup.DbConn;

import java.sql.ResultSet;

public class PaymentpageController {

    public TextField userid;
    public Label validexpiredlbl;
    public Label fines1lbl;
    public Label validnotexpiredlbl;
    public Label fine2lbl;
    public Button renewpaybtn;
    public Button finesbtn;
    public String username;

    @FXML
    public void initialize() {
        validexpiredlbl.setVisible(false);
        validnotexpiredlbl.setVisible(false);
        fines1lbl.setVisible(false);
        fine2lbl.setVisible(false);
        renewpaybtn.setVisible(false);
        finesbtn.setVisible(false);

    }
    private int checkforvalidity(String user)
    {
        DbConn oc;
        try {
            oc = new DbConn();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return -1;
        }

        try {
            String query = String.format("SELECT count(*) count from Users \n" +
                    "WHERE username='%s' AND expiredate<sysdate",user);
            ResultSet rs = oc.searchDB(query);
            rs.next();
            int count=rs.getInt("count");
            if(count>0) {
                oc.close();
                return 0;
            }
            else {
                oc.close();
                return 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
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


    public void searchbtnclicked(ActionEvent actionEvent) {
        validexpiredlbl.setVisible(false);
        validnotexpiredlbl.setVisible(false);
        fines1lbl.setVisible(false);
        fine2lbl.setVisible(false);
        renewpaybtn.setVisible(false);
        finesbtn.setVisible(false);
        String user=userid.getText().trim();
        username=user;
        int chkfine=checkforfines(user);
        int chkval=checkforvalidity(user);
        if(chkval==0)
        {
            validexpiredlbl.setVisible(true);
            renewpaybtn.setVisible(true);
        }
        else if(chkval==1)
        {
            validnotexpiredlbl.setVisible(true);
            renewpaybtn.setVisible(true);
        }
        if(chkfine==1)
        {
            fines1lbl.setVisible(true);
            fine2lbl.setVisible(true);
            finesbtn.setVisible(true);
        }

    }

    public void renewalpaybtnclicked(ActionEvent actionEvent) throws Exception{
        Stage stage=new Stage();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/renewalpayment.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root,471,311));
        stage.setResizable(false);
        RenewalpayController rp=loader.getController();
        rp.parent=this;
        rp.user=username;
        stage.show();
    }

    public void finesbtnclicked(ActionEvent actionEvent) throws Exception{
        Stage stage=new Stage();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/finescollection.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root,722,422));
        stage.setResizable(false);
        FinescollectionController fc=loader.getController();
        fc.parent=this;
        fc.user=username;
        fc.setData();
        stage.show();
    }

    public void homebtnclicked(ActionEvent actionEvent) throws Exception {
        Stage stage= (Stage) userid.getScene().getWindow();
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
