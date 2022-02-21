package startup;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OtherInfos {
    public static int borrowidcount=0;
    public static int bookidcount=0;
    public static  void main(String[] args)
    {
        DbConn oc;
        try {
            oc = new DbConn();
        } catch (Exception e) {
            System.out.println("problem in connection");
            return;
        }
        try
        {
            int s=12;
            String query="select to_char(paymentdate,'DD-MM-YYYY') payment from finespayment where userid='1'";
            ResultSet rs=oc.searchDB(query);
            rs.next();
            s=rs.getInt("payment");
            if(s==0) System.out.println("hello");
            System.out.println(s);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
